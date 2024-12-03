package cn.waitfor.mbga.integration.mq;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import cn.waitfor.mbga.component.AmqpMessageConsumer;
import cn.waitfor.mbga.config.AmqpConfig;
import cn.waitfor.mbga.dto.amqp.TestMessage;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.mockito.LatchCountDownAndCallRealMethodAnswer;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@SpringJUnitConfig
@ContextConfiguration(classes = {AmqpConfig.class, TestConsumerConfig.class})
@ImportAutoConfiguration(classes = {RabbitAutoConfiguration.class})
@RabbitListenerTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AmqpWithRabbitMqTest {

  @Resource private RabbitTemplate rabbitTemplate;

  @Resource private RabbitListenerTestHarness harness;

  @Container
  public static RabbitMQContainer rabbitMQContainer =
      new RabbitMQContainer(DockerImageName.parse("rabbitmq:4.0.3-management-alpine"));

  @BeforeAll
  static void beforeAll() {
    rabbitMQContainer.start();
  }

  @DynamicPropertySource
  static void rabbitProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
    registry.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
    registry.add("spring.rabbitmq.username", rabbitMQContainer::getAdminUsername);
    registry.add("spring.rabbitmq.password", rabbitMQContainer::getAdminPassword);
  }

  @Test
  public void simpleMode_whenSendMessageWithMatchedRoutingKey_shouldReceivedBySpecificConsumer() {
    TestMessage testMessage = creatTestMessage();
    rabbitTemplate.convertSendAndReceive("exchange.direct.default", "baz.simple", testMessage);
    AmqpMessageConsumer consumer = harness.getSpy("handleSimpleQueueMessage");
    assertNotNull(consumer);
    verify(consumer).handleSimpleQueueMessage(testMessage);
  }

  @Test
  public void workMode_whenSendMessageWithMatchedRoutingKey_shouldReceivedBySpecificConsumer() {
    TestMessage testMessage = creatTestMessage();
    rabbitTemplate.convertSendAndReceive("exchange.direct.default", "baz.work", testMessage);
    AmqpMessageConsumer consumer = harness.getSpy("handleWorkQueueMessage");
    assertNotNull(consumer);
    verify(consumer).handleWorkQueueMessage(testMessage);
  }

  @Test
  public void
      simpleMode_whenSendMessageWithUnmatchedRoutingKey_shouldNotReceivedBySpecificConsumer()
          throws InterruptedException {
    AmqpMessageConsumer consumer = harness.getSpy("handleSimpleQueueMessage");
    assertNotNull(consumer);

    LatchCountDownAndCallRealMethodAnswer answer =
        harness.getLatchAnswerFor("handleSimpleQueueMessage", 1);
    doAnswer(answer).when(consumer).handleSimpleQueueMessage(any());

    TestMessage testMessage = creatTestMessage();
    rabbitTemplate.convertAndSend("exchange.direct.default", "unmatched.routing.key", testMessage);
    answer.await(2);

    verify(consumer, never()).handleSimpleQueueMessage(any());
  }

  @Test
  public void workMode_whenSendMessageWithUnmatchedRoutingKey_shouldNotReceivedBySpecificConsumer()
      throws InterruptedException {
    AmqpMessageConsumer consumer = harness.getSpy("handleWorkQueueMessage");
    assertNotNull(consumer);

    LatchCountDownAndCallRealMethodAnswer answer =
        harness.getLatchAnswerFor("handleWorkQueueMessage", 1);
    doAnswer(answer).when(consumer).handleWorkQueueMessage(any());

    TestMessage testMessage = creatTestMessage();
    rabbitTemplate.convertAndSend("exchange.direct.default", "unmatched.routing.key", testMessage);
    answer.await(2);

    verify(consumer, never()).handleWorkQueueMessage(any());
  }

  @Test
  public void pubSubMode_whenSendMessageToExchange_shouldReceivedByAllRelevantConsumer() {
    TestMessage testMessage = creatTestMessage();
    rabbitTemplate.convertSendAndReceive("exchange.pubsub.default", "", testMessage);
    AmqpMessageConsumer consumer1 = harness.getSpy("handleDefaultPubSubQueue1");
    AmqpMessageConsumer consumer2 = harness.getSpy("handleDefaultPubSubQueue2");
    verify(consumer1).handleDefaultPubSubQueue1(testMessage);
    verify(consumer2).handleDefaultPubSubQueue2(testMessage);
  }

  @Test
  public void topicMode_whenSendMessageToExchange_shouldReceivedByParticularConsumer1()
      throws InterruptedException {
    AmqpMessageConsumer consumer1 = harness.getSpy("handleDefaultTopicQueue1");
    AmqpMessageConsumer consumer2 = harness.getSpy("handleDefaultTopicQueue2");
    LatchCountDownAndCallRealMethodAnswer answer1 =
        harness.getLatchAnswerFor("handleDefaultTopicQueue1", 1);
    LatchCountDownAndCallRealMethodAnswer answer2 =
        harness.getLatchAnswerFor("handleDefaultTopicQueue2", 1);
    doAnswer(answer1).when(consumer1).handleDefaultTopicQueue1(any());
    doAnswer(answer2).when(consumer2).handleDefaultTopicQueue2(any());

    TestMessage testMessage = creatTestMessage();
    rabbitTemplate.convertAndSend("exchange.topic.default", "qox.bar.xyz", testMessage);
    answer1.await(2);
    answer2.await(2);
    verify(consumer1).handleDefaultTopicQueue1(testMessage);
    verify(consumer2, never()).handleDefaultTopicQueue2(testMessage);
  }

  private TestMessage creatTestMessage() {
    TestMessage testMessage = new TestMessage();
    testMessage.setId(23134L);
    testMessage.setName("hgjuis0JgIUB");
    return testMessage;
  }

  @Test
  public void topicMode_whenSendMessageToExchange_shouldReceivedByParticularConsumer2()
      throws InterruptedException {
    TestMessage testMessage = creatTestMessage();
    AmqpMessageConsumer consumer3 = harness.getSpy("handleDefaultTopicQueue1");
    AmqpMessageConsumer consumer4 = harness.getSpy("handleDefaultTopicQueue2");
    LatchCountDownAndCallRealMethodAnswer answer3 =
        harness.getLatchAnswerFor("handleDefaultTopicQueue1", 1);
    LatchCountDownAndCallRealMethodAnswer answer4 =
        harness.getLatchAnswerFor("handleDefaultTopicQueue2", 1);
    doAnswer(answer3).when(consumer3).handleDefaultTopicQueue1(any());
    doAnswer(answer4).when(consumer4).handleDefaultTopicQueue2(any());
    rabbitTemplate.convertAndSend("exchange.topic.default", "foo.baz", testMessage);
    answer3.await(2);
    answer4.await(2);
    verify(consumer3, never()).handleDefaultTopicQueue1(testMessage);
    verify(consumer4).handleDefaultTopicQueue2(testMessage);
  }
}
