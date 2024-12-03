package cn.waitfor.mbga.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

  @Bean
  DirectExchange defaultDirectExchange() {
    return new DirectExchange("exchange.direct.default");
  }

  @Bean
  TopicExchange defaultTopicExchange() {
    return new TopicExchange("exchange.topic.default");
  }

  @Bean
  FanoutExchange defaultPubSubExchange() {
    return new FanoutExchange("exchange.pubsub.default");
  }

  @Bean
  Queue defaultTopicQueue1() {
    return new Queue("queue.topic.default.1");
  }

  @Bean
  Queue defaultTopicQueue2() {
    return new Queue("queue.topic.default.2");
  }

  @Bean
  Queue defaultPubSubQueue1() {
    return new Queue("queue.pubsub.default.1");
  }

  @Bean
  Queue defaultPubSubQueue2() {
    return new Queue("queue.pubsub.default.2");
  }

  @Bean
  Queue defaultSimpleQueue() {
    return new Queue("queue.simple.default");
  }

  @Bean
  Queue defaultWorkQueue() {
    return new Queue("queue.work.default");
  }

  @Bean
  Binding defaultSimpleModeBinding(Queue defaultSimpleQueue, DirectExchange defaultDirectExchange) {
    return BindingBuilder.bind(defaultSimpleQueue).to(defaultDirectExchange).with("baz.simple");
  }

  @Bean
  Binding defaultWorkModeBinding(Queue defaultWorkQueue, DirectExchange defaultDirectExchange) {
    return BindingBuilder.bind(defaultWorkQueue).to(defaultDirectExchange).with("baz.work");
  }

  @Bean
  Binding defaultTopicModeBinding(Queue defaultTopicQueue1, TopicExchange defaultTopicExchange) {
    return BindingBuilder.bind(defaultTopicQueue1).to(defaultTopicExchange).with("*.bar.*");
  }

  @Bean
  Binding defaultTopicModeBinding2(Queue defaultTopicQueue2, TopicExchange defaultTopicExchange) {
    return BindingBuilder.bind(defaultTopicQueue2).to(defaultTopicExchange).with("foo.#");
  }

  @Bean
  Binding defaultPubSubModeBinding(
      Queue defaultPubSubQueue1, FanoutExchange defaultPubSubExchange) {
    return BindingBuilder.bind(defaultPubSubQueue1).to(defaultPubSubExchange);
  }

  @Bean
  Binding defaultPubSubModeBinding2(
      Queue defaultPubSubQueue2, FanoutExchange defaultPubSubExchange) {
    return BindingBuilder.bind(defaultPubSubQueue2).to(defaultPubSubExchange);
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
