package tech.xavi.wschat.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/ws");
        registry.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/v1/websocket").setAllowedOriginPatterns("*").withSockJS();
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
//    }
//
//    @Override
//    public void configureClientOutboundChannel(ChannelRegistration registration) {
//        WebSocketMessageBrokerConfigurer.super.configureClientOutboundChannel(registration);
//    }

}