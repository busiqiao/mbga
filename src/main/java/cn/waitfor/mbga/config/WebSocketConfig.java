package cn.waitfor.mbga.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Value("${cors.allowedOrigins}")
  private String allowedOrigins;

  public static final String ROOT_PATH = "/chat-room";
  public static final String TOPIC = "/notice";
  public static final String QUEUE = "/message";
  public static final String RECEIVE_ENDPOINT_PREFIXES = "/app";
  public static final String USER_PREFIXES = "/user";

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker(TOPIC, QUEUE);
    config.setApplicationDestinationPrefixes(RECEIVE_ENDPOINT_PREFIXES);
    config.setUserDestinationPrefix(USER_PREFIXES);
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint(ROOT_PATH).setAllowedOrigins(allowedOrigins);
  }
}
