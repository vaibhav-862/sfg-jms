package guru.springframework.sfgjms;

import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SfgJmsApplication {

    public static void main(String[] args) throws Exception {

        //setting embedded server
        //set security enabled property to 'false' to avoid below exception on latest SpringBoot version
        //Caused by: javax.jms.JMSSecurityException: AMQ229031: Unable to validate user
        ActiveMQServer server = ActiveMQServers.newActiveMQServer(new ConfigurationImpl()
        .setPersistenceEnabled(false)
        .setJournalDirectory("target/data/journal")
        .addAcceptorConfiguration("invm", "vm://0")
        .setSecurityEnabled(false));

        server.start();

        SpringApplication.run(SfgJmsApplication.class, args);
    }

}
