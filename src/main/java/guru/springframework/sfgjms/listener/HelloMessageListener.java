package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

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

    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void listenForHello(@Payload HelloWorldMessage helloWorldMessage,
                               @Headers MessageHeaders headers, Message jmsMessage,
                               org.springframework.messaging.Message springMessage) throws JMSException {

        HelloWorldMessage payloadMsg = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("World!!")
                .build();

        jmsTemplate.convertAndSend(jmsMessage.getJMSReplyTo(), payloadMsg);

        //example to use Spring Message type
        //jmsTemplate.convertAndSend((Destination) springMessage.getHeaders().get("jms_replyTo"), "got it!");

    }

}
