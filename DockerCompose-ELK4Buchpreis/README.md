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

Startseite von Kibana: http://localhost:5601/

<br>

----

## Kibana: Abfrage mit KQL ## 

<br>

Alle Log-Nachrichten ...

* von Buchabfrage-Frontend anzeigen: `logger_name:de.eldecker.spring.buchabfrage*`

* von ISBN2Preis-Anwendung: `logger_name:de.eldecker.spring.isbn2preis*`

* mit Log-Level `ERROR` oder `WARN`: `level:WARN OR level:ERROR`

* mit Teilstring "8030" in MDC-Feld `Instanzname`: `Instanzname : "*8030*"`

* mit exaktem Wert für Instanz auf Port 8030: `Instanzname : "ISBN13Preis-8030"`

<br>

Weitere Infos von KQL siehe [hier](https://www.elastic.co/docs/explore-analyze/query-filter/languages/kql).

<br>