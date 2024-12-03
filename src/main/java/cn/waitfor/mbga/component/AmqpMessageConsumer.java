package cn.waitfor.mbga.component;

import cn.waitfor.mbga.dto.amqp.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AmqpMessageConsumer {

  @RabbitListener(id = "handleSimpleQueueMessage", queues = "queue.simple.default")
  public void handleSimpleQueueMessage(TestMessage testMessage) {
    log.info("Received amqp message {} on queue.simple.default", testMessage);
  }

  @RabbitListener(id = "handleWorkQueueMessage", queues = "queue.work.default")
  public void handleWorkQueueMessage(TestMessage testMessage) {
    log.info("Received amqp message {} on queue.work.default", testMessage);
  }

  @RabbitListener(id = "handleDefaultPubSubQueue1", queues = "queue.pubsub.default.1")
  public void handleDefaultPubSubQueue1(TestMessage testMessage) {
    log.info("Received amqp message {} on queue.pubsub.default.1", testMessage);
  }

  @RabbitListener(id = "handleDefaultPubSubQueue2", queues = "queue.pubsub.default.2")
  public void handleDefaultPubSubQueue2(TestMessage testMessage) {
    log.info("Received amqp message {} on queue.pubsub.default.2", testMessage);
  }

  @RabbitListener(id = "handleDefaultTopicQueue1", queues = "queue.topic.default.1")
  public void handleDefaultTopicQueue1(TestMessage testMessage) {
    log.info("Received amqp message {} on queue.topic.default.1", testMessage);
  }

  @RabbitListener(id = "handleDefaultTopicQueue2", queues = "queue.topic.default.2")
  public void handleDefaultTopicQueue2(TestMessage testMessage) {
    log.info("Received amqp message {} on queue.topic.default.2", testMessage);
  }
}
