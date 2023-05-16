# Middleware Engineering SYT-MoM-Marathon

Autor: Jurij Cejka
Datum: 04.10.2022
Aufgabe: https://elearning.tgm.ac.at/mod/assign/view.php?id=45072
## Fragestellungen
-   Nennen Sie mindestens 4 Eigenschaften der Message Oriented Middleware?
    - Unterstützung für Nachrichtenwarteschlangen (Message Queing): MOM unterstützt oft die Verwendung von Nachrichtenwarteschlangen, in denen Nachrichten temporär gespeichert werden, bevor sie von den Empfängern abgerufen werden.
    - Kommunikation kann Minuten dauern
    - Persistente(aysnchrone Kommunikation)
    - Lose gekoppeltes System
-   Was versteht man unter einer transienten und synchronen Kommunikation?
    - Bei einer transienten und synchronen Kommunikation würde der Sender eine Nachricht an den Empfänger senden und dabei seinen eigenen Prozesse blockieren , bis eine Antwort vom Empfänger empfangen wird. Außerdem wird die Nachricht nur während der Ausführung der beiden Prozesse gespeichert.
-   Beschreiben Sie die Funktionsweise einer JMS Queue?
    - Erstellung der Queue -> Sender sendet Nachrichten -> Queue speichert die Nachricht -> Empfänger ruft die Nachricht ab -> Bestätigung des Nachrichtenempfangs (acknowledge) -> Wiederholung bei nicht bestätigter Nachricht
-   JMS Overview - Beschreiben Sie die wichtigsten JMS Klassen und deren Zusammenhang?
    - ConnectionFactory(Objekt zum erzeugen von Connection Objekten) ->
    - Connection (startet eine connetion und erzeugt Session objekte) ->
    - Session (wir benutzt um ein Destionation, MsgProducer und MsgConsumer-Objekt zu erzeugen in der jeweiligen Session)->
    - Destination (wird benutzt um den "Ort" für den Msg Transfer festzulegen mit einem String z.B "Timingstation: 1". Dieses Objekt wird dann benötigt um einen MsgProducer und Empfänger zu initialisieren) ->
    - MsgProducer (Möglich verschiedene Informationstypen zu senden z.B BytesMessage, TextMessage, ObjectMessage die es an den Consumer mit der gleichen Destination sendet) ->
    - MsgConsumer(speichert Nachrichten in MsQueue die dann nach belieben abgerufen werden können) ->
    - Message (Die Message-Klasse ist die Basisklasse für alle JMS-Nachrichtentypen. Sie enthält den eigentlichen Nachrichteninhalt sowie Metadaten wie Header, Eigenschaften und Zustandsinformationen)
-   Beschreiben Sie die Funktionsweise eines JMS Topic?
    - Erstellung des Topics -> Publisher sendet Nachrichten -> Subscribers abonnieren das Topic -> Nachrichtenverteilung an Subscribers -> Asynchrone Nachrichtenverarbeitung -> Bestätigung der Nachrichtenverarbeitung
-   Was versteht man unter einem lose gekoppelten verteilten System? Nennen Sie ein Beispiel dazu. Warum spricht man hier von lose?
    - Ein lose gekoppeltes verteiltes System bezieht sich auf eine Architektur, bei der die Komponenten oder Teilsysteme des Systems unabhängig voneinander existieren und kommunizieren können, ohne stark voneinander abhängig zu sein.

    - Beispiel:
        - In einer Microservices-Architektur besteht eine Anwendung aus mehreren kleinen, unabhängig bereitgestellten und skalierbaren Services. Jeder Service kümmert sich um einen spezifischen Geschäftsbereich und kann eigenständig entwickelt, bereitgestellt und skaliert werden.
          `
## Schritte
### Download von activemq
Unter diesem Link ladet man das Programm herunter: [http://activemq.apache.org](http://activemq.apache.org/)

Danach wechselt man per cmd in den bin Ordner des Programms und gibt dann diesen Befehl ein wie bei mir zum Beispiel :
```cmd 
cd C:\Users\jurij\Downloads\apache-activemq-5.14.3\bin
activemq start
```
Jetzt sollte unter http://localhost:8161/admin/ activemq laufen.
Benutzer: admin
Passwort: admin

### Demo1
demo1 sollte laufen wenn activemq gestartet ist.
Als nächstet implementieren wir MoMSender in unserer vorherigen Übung als Methode

Ausschnitt der Implementierung
![[Pasted image 20230321090934.png]]
Warnung!!

Wird nicht ohne entsprechende Imports funktionieren

Sender rufen wir dann zum Test in unserer **timingstationdata** methode auf.
```java
@RequestMapping(value="/timingstation/{timingstationID}/data", produces = MediaType.APPLICATION_JSON_VALUE)  
public TimingstationData timingstationData( @PathVariable String timingstationID ) {  
    sender();  
    return service.getTimingstationData( timingstationID );  
}
```
Darauf folgend compilieren wir das programm und wechseln in unserem browser auf localhost:8080 und klicken auf **[Link to timingstation/001/data](http://localhost:8080/timingstation/001/data)**

![[Pasted image 20230321091636.png]]
Perfekt die Methode funktioniert!!
### Implementierung
Um es wirklich zu implementieren habe ich gewählt die Aufabe auf 2 Projekte aufzuteilen. Dies habe ich gemacht, weil der Sender und der Receiver gleichzeitig laufen müssen aber sich Springboot default sich nur einen Port reserviert und der ist 8080. Beim zweiten Projekt habe ich einen Resources Ordner erstellt in main und dem ein File namens **application.properties** hinzugefügt und dort Springboots default Port auf 8083 umgeändert.
![[Pasted image 20230509174505.png]]
Meine Idee war es das ein Projekt den Sender spielt und das andere den Receiver. Die Daten generiere ich aus der Timingstation der vorherigen übung und pushe sie dann in der MOMSender Klasse durch das MessageProducer in den MessageQueue.

Zum Testen habe ich im ***Receiver*** 3 Consumer erstellt die auf die Nachrichten warten:
```java
Destination des1 = session.createTopic("Timingstation: 1");  
Destination des2 = session.createTopic("Timingstation: 2");  
Destination des3 = session.createTopic("Timingstation: 3");  
  
MessageConsumer timing1 = session.createConsumer(des1);  
MessageConsumer timing2 = session.createConsumer(des2);  
MessageConsumer timing3 = session.createConsumer(des3);
```
Und im ***Sender*** erstelle ich 3 Producer die an die jeweilige Destination gesendet werden.

```java
for(int i = 1;i<=3;i++){  
    destination = session.createTopic( "Timingstation: "+i );  
    // Create the producer.  
    producer = session.createProducer(destination);  
    producer.setDeliveryMode( DeliveryMode.NON_PERSISTENT );  
    TimingstationSimulation r = new TimingstationSimulation();  
    TextMessage message = session.createTextMessage(r.getData(Integer.toString(i)).toString());  
    producer.send(message);  
    System.out.println( message.getText());  
}
```

Sobald die letzte Nachricht gesendet wird bricht der Receiver loop ab.
Der Receiver speichert die Nachrichten in einem einfachen String der dann ausgegeben wird.

