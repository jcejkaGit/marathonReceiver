package sectionpark.timingstation;



import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MOMSender {

    private static String user = ActiveMQConnection.DEFAULT_USER;
    private static String password = ActiveMQConnection.DEFAULT_PASSWORD;
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String subject = "demo_001";

    public MOMSender() {

        System.out.println( "Sender started." );

        // Create the connection.
        Session session = null;
        Connection connection = null;
        MessageProducer producer = null;
        MessageProducer producer2 = null;
        MessageProducer producer3 = null;
        Destination destination = null;
        Destination destination2 = null;
        Destination destination3 = null;

        try {

            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory( user, password, url );
            connection = connectionFactory.createConnection();
            connection.start();

            // Create the session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);


            ;

            // Create the message
            for(int i = 1;i<=3;i++){
                destination = session.createTopic( "Timingstation: "+i );


                // Create the producer.
                producer = session.createProducer(destination);
                producer.setDeliveryMode( DeliveryMode.NON_PERSISTENT );
                 TimingstationSimulation r = new TimingstationSimulation();

               TextMessage message = session.createTextMessage(r.getData(Integer.toString(i)).toString());

               // TextMessage message = session.createTextMessage( "MaxMustermann [ xxx.xxx.xxx.xxx ]: This message was sent at (ms): " + System.currentTimeMillis() );

                producer.send(message);
                System.out.println( message.getText());
            }


            connection.stop();

        } catch (Exception e) {

            System.out.println("[MessageProducer] Caught: " + e);
            e.printStackTrace();

        } finally {

            try { producer.close(); } catch ( Exception e ) {}
            try { session.close(); } catch ( Exception e ) {}
            try { connection.close(); } catch ( Exception e ) {}

        }
        System.out.println( "Sender finished." );

    } // end main

}