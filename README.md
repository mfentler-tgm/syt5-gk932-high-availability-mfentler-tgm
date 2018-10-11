# Middleware Engineering "High Availability"

## Aufgabenstellung
Die detaillierte [Aufgabenstellung](TASK.md) beschreibt die notwendigen Schritte zur Realisierung.

## Fragestellungen

- Was kann als Gewichtung bei Weighted Round Robin verwendet werden?  
Der Server mit den höheren Specs bekommt mehr Anfragen zugewiesen.  
- Warum stellt die "Hochverfügbarkeit" von IT Systemen in der heutigen Zeit eine sehr wichtige Eigenschaft dar?  
Weil es 24/7 auf der ganzen Welt User gibt die online sind und auf das System zugreifen wollen. Wenn diese Webseite, als Beispiel, dann zu lange lädt oder garnicht lädt, dann wirkt sich das sehr schlecht auf die Userexpierence und Zufriedenheit aus.
- Welche anderen Massnahmen neben einer Lastverteilung müssen getroffen werden, um die "Hochverfügbarkeit" sicher zu stellen?  
Mehrere redundante Nodes (Server) werden zusammen zu Clustern verbunden. Jede Node sollte dabei dafür verantwortlich sein Fehler zu erkennen und zu beheben. [2]
- Was versteht man unter "Session Persistenz" und welche Schwierigkeiten ergeben sich damit?
- Nennen Sie jeweils ein Beispiel, wo Session Persistenz notwendig bzw. nicht notwendig ist.
- Welcher Unterschied besteht zwischen einer "server-side" bzw "client-side" Lastverteilungslösung?
- Was versteht man unter dem "Mega-Proxy-Problem"?


## Design und Beschreibung

## Implementierung

## Deployment

## Quellen
[1] https://www.jscape.com/blog/load-balancing-algorithms  
[2] Multiple redundant nodes must be connected together as a cluster where each node should be equally capable of failure detection and recovery.  
