package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class HelloMessageListener {

    //payload anno to deserialize the object from the queue
    //headers anno to consume the message headers i.e. JMS properties
    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers, Message message) {

        System.out.println("I got a message!!!!");

        System.out.println(helloWorldMessage);

        //to demo that JMS is transactional and if the client face issues in receiving message from
        //queue, JMS is going to re-deliver that to the client saying that the transaction isn't complete
        //header property jms_redelivered can be seen to be set as 'true' showing that the failed message
        //is being re-delivered

        // uncomment and view to see retry count in debugger
        //throw new RuntimeException("foo");
    }

}
