# Middleware Engineering "High Availability"

## Aufgabenstellung
Die detaillierte [Aufgabenstellung](TASK.md) beschreibt die notwendigen Schritte zur Realisierung.

## Fragestellungen

- Was kann als Gewichtung bei Weighted Round Robin verwendet werden?  
Der Server mit den höheren Specs bekommt mehr Anfragen zugewiesen.  
- Warum stellt die "Hochverfügbarkeit" von IT Systemen in der heutigen Zeit eine sehr wichtige Eigenschaft dar?  
Weil es 24/7 auf der ganzen Welt User gibt die online sind und auf das System zugreifen wollen. Wenn diese Webseite, als Beispiel, dann zu lange lädt oder garnicht lädt, dann wirkt sich das sehr schlecht auf die Userexpierence und Zufriedenheit aus.
- Welche anderen Massnahmen neben einer Lastverteilung müssen getroffen werden, um die "Hochverfügbarkeit" sicher zu stellen?  
Mehrere redundante Nodes (Server) werden zusammen zu Clustern verbunden. Jede Node sollte dabei dafür verantwortlich sein Fehler zu erkennen und zu beheben. Weiters kann man einen DNS round robin Loadbalancer einbauen, der die IP Adressen dynamisch an die Server vergibt, die online sind.[2]  
- Was versteht man unter "Session Persistenz" und welche Schwierigkeiten ergeben sich damit?  
Unter dem Begriff versteht man, dass wenn sich ein User mit einem Server verbindet, dass dieser für alle seine Anfragen, solange die Session besteht, zuständig ist. Um die Performance zu verbessern haben die Server gespeichert wohin sich der User am wahrscheinlichsten als nächsten Schritt hinverbinden will/welche Informationen er abrufen will.  
Um die Geschwindigkeit der Anfragen zu erhöhen, ist es also sinnvoll alle Anfragen vom selben User auf einen Server zu leiten.  
- Nennen Sie jeweils ein Beispiel, wo Session Persistenz notwendig bzw. nicht notwendig ist.  
Sinnvoll ist es, wie schon bereits oben erwähnt, dass man bei einem Webseiten besuch schneller ist.  
Wenn allerdings nur mit einem Server kommuniziert wird und dieser dann plötzlich während einer Transaktion ausfällt, dann besteht die Möglichkeit das Informationen verloren gehen.[3]  
- Welcher Unterschied besteht zwischen einer "server-side" bzw "client-side" Lastverteilungslösung?
- Was versteht man unter dem "Mega-Proxy-Problem"?


## Design und Beschreibung

## Implementierung

## Deployment

## Quellen
[1] https://www.jscape.com/blog/load-balancing-algorithms  
[2] https://www.digitalocean.com/community/tutorials/what-is-high-availability  
[3] https://www.nginx.com/resources/glossary/session-persistence/  
