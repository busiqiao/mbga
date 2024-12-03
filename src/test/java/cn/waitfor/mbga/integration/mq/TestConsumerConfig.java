package cn.waitfor.mbga.integration.mq;

import cn.waitfor.mbga.component.AmqpMessageConsumer;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.context.annotation.Bean;

@RabbitListenerTest
public class TestConsumerConfig {

  @Bean
  public AmqpMessageConsumer amqpMessageTestConsumer() {
    return new AmqpMessageConsumer();
  }
}
