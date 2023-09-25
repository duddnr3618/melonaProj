package com.fundguide.melona.WebSocket.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
        /*
         * withSockJS()
         * 는 웹소켓을 지원하지 않는 브라우저에
         * 폴백 옵션을 활성화하는데 사용됩니다.
         * */



    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // "/ app"로 시작하는 메시지가 메시지 처리 방법으로 라우팅되어야 함을 정의합니다
        registry.setApplicationDestinationPrefixes("/app");
        //"/ topic"으로 시작하는 메시지가 메시지 브로커로 라우팅되도록 정의합니다.
        // 메시지 브로커는 특정 주제를 구독 한 연결된 모든 클라이언트에게 메시지를 브로드 캐스트합니다.
        registry.enableSimpleBroker("/topic");
    }


}