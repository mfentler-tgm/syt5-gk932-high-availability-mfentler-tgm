# Middleware Engineering "High Availability"

## Aufgabenstellung
Die detaillierte [Aufgabenstellung](TASK.md) beschreibt die notwendigen Schritte zur Realisierung.

## Fragestellungen

- __Was kann als Gewichtung bei Weighted Round Robin verwendet werden?__  
Der Server mit den höheren Specs bekommt mehr Anfragen zugewiesen.  
- __Warum stellt die "Hochverfügbarkeit" von IT Systemen in der heutigen Zeit eine sehr wichtige Eigenschaft dar?__  
Weil es 24/7 auf der ganzen Welt User gibt die online sind und auf das System zugreifen wollen. Wenn diese Webseite, als Beispiel, dann zu lange lädt oder garnicht lädt, dann wirkt sich das sehr schlecht auf die Userexpierence und Zufriedenheit aus.
- __Welche anderen Massnahmen neben einer Lastverteilung müssen getroffen werden, um die "Hochverfügbarkeit" sicher zu stellen?__  
Mehrere redundante Nodes (Server) werden zusammen zu Clustern verbunden. Jede Node sollte dabei dafür verantwortlich sein Fehler zu erkennen und zu beheben. Weiters kann man einen DNS round robin Loadbalancer einbauen, der die IP Adressen dynamisch an die Server vergibt, die online sind.[2]  
- __Was versteht man unter "Session Persistenz" und welche Schwierigkeiten ergeben sich damit?__  
Unter dem Begriff versteht man, dass wenn sich ein User mit einem Server verbindet, dass dieser für alle seine Anfragen, solange die Session besteht, zuständig ist. Um die Performance zu verbessern haben die Server gespeichert wohin sich der User am wahrscheinlichsten als nächsten Schritt hinverbinden will/welche Informationen er abrufen will.  
Um die Geschwindigkeit der Anfragen zu erhöhen, ist es also sinnvoll alle Anfragen vom selben User auf einen Server zu leiten.  
- __Nennen Sie jeweils ein Beispiel, wo Session Persistenz notwendig bzw. nicht notwendig ist.__  
Sinnvoll ist es, wie schon bereits oben erwähnt, dass man bei einem Webseitenbesuch schneller ist.  
Wenn allerdings nur mit einem Server kommuniziert wird und dieser dann plötzlich während einer Transaktion ausfällt, dann besteht die Möglichkeit das Informationen verloren gehen - hier wären mehrere Server sinnvoller.[3]  
- __Welcher Unterschied besteht zwischen einer "server-side" bzw "client-side" Lastverteilungslösung?__  
Beim serverseitigen Loadbalancer gehen die Anfragen des Clients an eine Schnittstelle zwischen dem Client und der Serverfarm. Dabei werden von dieser Schnittstelle die Anfragen auf die entsprechenden Server, je nach loadbalancing-Methode aufgeteilt.(Früher die meist genütze Art)  
Beim clientseitigen Loadbalancer (wird aktuell immer beliebter in manchen Anwendungsbereichen) fällt die eigentliche Idee vom Loadbalancing weg, da es die Middleware Schnittstelle nicht mehr gibt. Der Client hat eine Liste von Servern (IPs) und wählt random oder nach einem Algorithmus den Server aus.[4]  
- __Was versteht man unter dem "Mega-Proxy-Problem"?__  
Wenn über die IP-Adresse des Clients das Loadbalancing betrieben wird, dann kann man nicht sagen ob das der selbe Client ist oder nicht.  
Als Beispiel nehmen wir das TGM. Hier hat jeder nach außen die selbe IP-Adresse.  
Wenn nun von dem einen Client eine Anfrage an den Loadbalancer kommt, leitet ihn dieser zum Server1 weiter. Kommt nun von Client2 auch eine Anfrage an diesen Loadbalancer, dann denkt er aufgrund der selben IP-Adresse, dass das der Client1 ist und leitet ihn wieder zu Server1 weiter.  [5]

## Design und Beschreibung

## Implementierung

## Deployment

## Quellen
[1] https://www.jscape.com/blog/load-balancing-algorithms  
[2] https://www.digitalocean.com/community/tutorials/what-is-high-availability  
[3] https://www.nginx.com/resources/glossary/session-persistence/  
[4] https://soaessentials.com/client-side-load-balancing-vs-server-side-load-balancing-how-client-side-load-balancing-works/  
[5] Prof. Borko Erklärung :)  
