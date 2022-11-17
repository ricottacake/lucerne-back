package com.aceliq.lucerne.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import com.aceliq.lucerne.components.AuthChannelInterceptor;
import com.aceliq.lucerne.components.HttpHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Autowired
  private AuthChannelInterceptor channelInterceptor;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic/", "/queue/");
    config.setApplicationDestinationPrefixes("/app");
    // config.setUserDestinationPrefix("/user");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws").setAllowedOriginPatterns("*")
        .addInterceptors(new HttpHandshakeInterceptor()).withSockJS();
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {

    registration.interceptors(channelInterceptor);
  }
}
