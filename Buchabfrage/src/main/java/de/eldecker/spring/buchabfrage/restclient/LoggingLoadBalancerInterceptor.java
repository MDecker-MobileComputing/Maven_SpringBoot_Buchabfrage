package de.eldecker.spring.buchabfrage.restclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;
import java.net.URI;

public class LoggingLoadBalancerInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingLoadBalancerInterceptor.class);
    private final LoadBalancerClient loadBalancer;

    public LoggingLoadBalancerInterceptor(LoadBalancerClient loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        URI originalUri = request.getURI();
        String serviceName = originalUri.getHost();

        // Only intercept requests for actual service names
        if (serviceName != null && !serviceName.equalsIgnoreCase("localhost")) {
            ServiceInstance instance = loadBalancer.choose(serviceName);

            if (instance != null) {
                URI resolvedUri = loadBalancer.reconstructURI(instance, originalUri);
                logger.info("Resolved service instance: {} -> {}", originalUri, resolvedUri);
                HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request) {
                    @Override
                    public URI getURI() {
                        return resolvedUri;
                    }
                };
                return execution.execute(requestWrapper, body);
            } else {
                logger.warn("No instances available for {}", serviceName);
            }
        }

        // Proceed with the original request if not a load-balanced service
        return execution.execute(request, body);
    }
}