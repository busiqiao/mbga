package cn.waitfor.mbga.controller;

import cn.waitfor.mbga.dto.amqp.TestMessage;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/amqp/publisher/")
public class AmqpMessageProducer {

  @Resource private RabbitTemplate rabbitTemplate;

  @PostMapping(value = "/test/simple-mode")
  public String testSimpleMode(
      @RequestParam(name = "routing-key") String routingKey, @RequestBody TestMessage testMessage) {
    rabbitTemplate.convertAndSend("exchange.direct.default", routingKey, testMessage);
    return "succeed";
  }

  @PostMapping(value = "/test/work-mode")
  public String testWorkMode(
      @RequestParam(name = "routing-key") String routingKey, @RequestBody TestMessage testMessage) {
    rabbitTemplate.convertAndSend("exchange.direct.default", routingKey, testMessage);
    return "succeed";
  }

  @PostMapping(value = "/test/topic-mode")
  public String testTopicMode(
      @RequestParam(name = "routing-key") String routingKey, @RequestBody TestMessage testMessage) {
    rabbitTemplate.convertAndSend("exchange.topic.default", routingKey, testMessage);
    return "succeed";
  }

  @PostMapping(value = "/test/pubsub-mode")
  public String testPubSubMode(@RequestBody TestMessage testMessage) {
    rabbitTemplate.convertAndSend("exchange.pubsub.default", "", testMessage);
    return "succeed";
  }
}
