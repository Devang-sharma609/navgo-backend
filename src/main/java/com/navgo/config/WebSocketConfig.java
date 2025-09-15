package com.navgo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.navgo.handler.WebSocketServer;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketServer(), "/ws") // WebSocket endpoint
                .setAllowedOrigins("*"); // Allows connections from any frontend or mobile app
    }

    public WebSocketHandler webSocketServer() {
        return new WebSocketServer(); // Custom WebSocket handler
    }
}
