package consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class reveiver_001 {
    @RabbitListener
    public void processMessage(String content) {
        // ...
    }
}
