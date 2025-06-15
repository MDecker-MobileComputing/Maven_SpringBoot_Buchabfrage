<br>

Die `docker-compose.yml`-Datei für Container mit Komponenten des ELK-Stacks in diesem Verzeichnis
wurde anhand 
[dieser Anleitung](https://community.hetzner.com/tutorials/deploy-elk-stack-with-docker)
angelegt.

<br>

Siehe auch 
[dieser Anleitung](https://betterstack.com/community/questions/send-spring-boot-logs-directly-to-logstash-with-no-file/)
für Einrichten von Senden von Log-Nachrichten von Spring-Boot-Anwendung an Logstash.

<br>

----

## Kibana: Abfrage mit KQL ## 

<br>

Nur Log-Nachrichten von Buchabfrage-Frontend anzeigen: `logger_name:de.eldecker.spring.buchabfrage*`

Nur Log-Nachrichten von ISBN2Preis-Anwendung: `logger_name:de.eldecker.spring.isbn2preis*`

<br>