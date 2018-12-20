# Middleware Engineering "High Availability"

## Aufgabenstellung
Die detaillierte [Aufgabenstellung](TASK.md) beschreibt die notwendigen Schritte zur Realisierung.

## Fragestellungen

- __Was kann als Gewichtung bei Weighted Round Robin verwendet werden?__
---  
Der Server mit den höheren Specs bekommt mehr Anfragen zugewiesen.  

Bei dieser Loadbalancing-Methode wird __nach den Resourcen der Server entschieden__, welche Anfrage an welche Instanz weitergeleitet wird.  

- __Warum stellt die "Hochverfügbarkeit" von IT Systemen in der heutigen Zeit eine sehr wichtige Eigenschaft dar?__  
---  
Weil es 24/7 auf der ganzen Welt User gibt die online sind und auf das System zugreifen wollen. Wenn diese Webseite, als Beispiel, dann zu lange lädt oder garnicht lädt, dann wirkt sich das sehr schlecht auf die Userexpierence und Zufriedenheit aus.
- __Welche anderen Massnahmen neben einer Lastverteilung müssen getroffen werden, um die "Hochverfügbarkeit" sicher zu stellen?__  
---  
Mehrere redundante Nodes (Server) werden zusammen zu Clustern verbunden. Jede Node sollte dabei dafür verantwortlich sein Fehler zu erkennen und zu beheben. Weiters kann man einen DNS round robin Loadbalancer einbauen, der die IP Adressen dynamisch an die Server vergibt, die online sind.[2]  
- __Was versteht man unter "Session Persistenz" und welche Schwierigkeiten ergeben sich damit?__  
---
Verbindet sich ein User zu einem Server, bleibt dieser für alle seine weiteren Anfragen zuständig. Um die Performance zu verbessern, analysieren die Server mögliche weitere Schritte der Benutzer und stellen diese Informationen und Berechnungen im Voraus bereit. Daher ist es zur Geschwindigkeitsoptimierung sinnvoll, alle Anfragen vom selben User auf einen Server zu leiten, obwohl dies einer effektiven Lastverteilung entgegenstrebt.  

- __Nennen Sie jeweils ein Beispiel, wo Session Persistenz notwendig bzw. nicht notwendig ist.__  
---  
Sinnvoll ist es, wie schon bereits oben erwähnt, dass man bei einem Webseitenbesuch schneller ist.  
Wenn allerdings nur mit einem Server kommuniziert wird und dieser dann plötzlich während einer Transaktion ausfällt, dann besteht die Möglichkeit das Informationen verloren gehen - hier wären mehrere Server sinnvoller.[3]  

- __Welcher Unterschied besteht zwischen einer "server-side" bzw "client-side" Lastverteilungslösung?__  
---
Beim __serverseitigen Loadbalancer__ gehen die Anfragen des Clients an eine Schnittstelle zwischen dem Client und der Serverfarm. Dabei werden von dieser Schnittstelle die Anfragen auf die entsprechenden Server, je nach loadbalancing-Methode aufgeteilt.(Früher die meist genütze Art)  

Beim __clientseitigen Loadbalancer__ (wird aktuell immer beliebter in manchen Anwendungsbereichen) fällt die eigentliche Idee vom Loadbalancing weg, da es die Middleware Schnittstelle nicht mehr gibt. Der Client hat eine Liste von Servern (IPs) und wählt random oder nach einem Algorithmus den Server aus.[4]  
- __Was versteht man unter dem "Mega-Proxy-Problem"?__  
---
Wenn über die IP-Adresse des Clients das Loadbalancing betrieben wird, dann kann man nicht sagen ob das der selbe Client ist oder nicht.  
Als Beispiel nehmen wir das TGM. Hier hat jeder nach außen die selbe IP-Adresse.  
Wenn nun von dem einen Client eine Anfrage an den Loadbalancer kommt, leitet ihn dieser zum Server1 weiter. Kommt nun von Client2 auch eine Anfrage an diesen Loadbalancer, dann denkt er aufgrund der selben IP-Adresse, dass das der Client1 ist und leitet ihn wieder zu Server1 weiter.  

_Es ist nicht immer möglich über die IP-Adresse des Clients eine eindeutige Zuordnung der Session bzw. der Anfrage zu gewährlieisten um eine entsprechende Load-Balancing Methode für die eigene Serverlandschaft auszuwählen._  

__-> Mega Proxy Problem__

## Design und Beschreibung
### Weighted Round Robin - Theorie
Bei dieser Loadbalancing Methode bekommen die Server einen "Weight"-Wert zugewiesen. Dieser gibt an, wie viele andere Tasks der Server noch ausrechnen kann. Ein Server mit einer höheren Rechenleistung und mehr RAM wird einen höheren Wert haben als ein schwächerer.  
Sobald der Server einen Task zum ausrechnen bekommt, wird der Wert um einen speziellen Faktor, je nach Schwierigkeit der Rechnung, verkleinert. Sobald die Rechnung fertig ist, wird der Faktor wieder dazu addiert.  
Solange der Wert des Servers noch größer als eine bestimmte Zahl ist, können ihm noch Tasks zugewiesen werden.

## Implementierung
### Java Socket Exception
Sollte eine Java Socket Exception auftreten, dann hängt das sicher mit der Java Policy zusammen. Diese muss den Zugriff über die Ports auf localhost erlauben.  
Dazu geht man in folgende Files:  
- "C:\Program Files\Java\jdk1.8.0_181\jre\lib\security\javaws.policy"  
- "C:\Program Files\Java\jdk1.8.0_181\jre\lib\security\java.policy"  

__ACHTUNG!__ Das was jetzt kommt ist ein Quick n dirty Pfusch. Dieser sollte nach der Übung wieder rausgenommen werden.  
Es wird folgender Code-Snippet oben ins File hinzugefügt:  

    grant {
        permission java.security.AllPermission;
    };   
__Besser:__  
In ein File den Code von oben einfügen und dann bei den VM Parametern in der Start-Konfig folgende Syntax hinzufügen. 
Das hat den Vorteil, dass man keine Sicherheitslücken im System erstellt und die Policy nur solange gilt, solange das Programm läuft.  

	-Djava.security.policy=/some/path/my.policy

### Weighted Round Robin Implementierung
Das Grundgerüst (Round Robin) war von Marc Rousavy. Darauf wurde in dieser Übung aufgebaut und ein Weighted Round Robin implementiert.  
Dazu wurden Getter Methoden in die Interfaces "process" und "task" eingebaut. Mit denen kann man im Loadbalancer auf die "weight"-Werte zugreifen.  
Für die Weight der Tasks wurde die Nachkommazahl/500 gerechnet. Das ist ein frei gewählter Wert, den man nach belieben anpassen kann.  
Wenn der Weight-Wert des Servers nicht groß genug ist wird der nächste genommen.  


#### Methode zur Berechnung der Weight der Berechnung
Um die Schwierigkeit der Berechnung zu bewerten wird in beide Klassen (Fibonnaci und Pi) folgende Methode eingefügt.  

	@Override
    public int getWeight() {
        return (int) _digits / 500;
    }
Diese muss natürlich auch ins Task Interface eingefügt werden.

#### Weight vom Server
Da die Server verschieden stark sein können gibt es auch hier verschiedene Weights. Diese müssen in der run Methode des Servers reduziert werden bis der Server mit der Berechnung fertig ist.  

	@Override
    public <T> T run(Task<T> task) throws RemoteException {
        try{
            setWeight(getWeight() - task.getWeight());
            T result = task.run();
            JLogger.Instance.Log(Logger.Severity.Info,
                    _name + ": Executed Task \"" + task.toString() +
                            "\", at " + new Date().toString());
            return result;
        } finally {
            setWeight(getWeight() + task.getWeight());
        }
    }

#### Überarbeitung LoadBalancer
Anschließend muss noch beim LoadBalancer die Methode zur Lastverteilung überarbeitet werden. Diese soll nun bezogen auf die Weights die Tasks vergeben.  

	@Override
    public <T> T run(Task<T> t) throws RemoteException {
        //List<Processor> available = _processors.stream()
        //      .filter(p -> !p.busy()).collect(Collectors.toList());

        do {

            if (!_iter.hasNext())
                _iter = _processors.iterator();

            System.out.println("New task request...");
            System.out.println("Task weight: " + t.getWeight());
            while (_iter.hasNext()) {
                Processor p = _iter.next();
                if (!(p.getWeight() - t.getWeight() < 0)) {
                    return p.run(t);

                    //return p.run(t);
                } else {
                    System.out.println("Nächster Server wird ausgewählt.");
                }
            }

            //return p.run(t);
        } while (true);
    }
	
## Deployment
Um das ganze auszuführen muss man (ich verwende Intellij Idea) die Configurations ändern. Als erstes muss man den LoadBalancer starten.  
Die Startkonfiguration vom LoadBalancer:  

	Main-Class: Proxy.Main
	VM options: wie oben beschrieben
Bei den Clients muss man in der Konfiguration etwas mehr ändern:  

	Main-Class: Client.Main
	VM options: wie oben beschrieben
	Programm arguments: <host> <Calculation-Method> <Length> // localhost Pi/Fibonnaci Nachkommastellen/Größe von n

In dem man dann anschließend den Proxy startet, startet dieser automatisch 4 Server. Sobald der LoadBalancer (Proxy) gestartet ist kann man die Clients starten, deren Berechnungen dann per Weigted Round Robin Prinzip aufgeteilt und berechnet werden.
	

## Quellen
[1] https://www.jscape.com/blog/load-balancing-algorithms  
[2] https://www.digitalocean.com/community/tutorials/what-is-high-availability  
[3] https://www.nginx.com/resources/glossary/session-persistence/  
[4] https://soaessentials.com/client-side-load-balancing-vs-server-side-load-balancing-how-client-side-load-balancing-works/  
