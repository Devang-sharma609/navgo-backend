package com.navgo.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import com.navgo.dto.BusLocationDTO;

public class WebSocketServer extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    private final Map<String, BusLocationDTO> busLocations = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON parser

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("New WebSocket connection: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received JSON: " + message.getPayload());

        // Parse incoming JSON
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());

        // Ensure "type" is "location_update"
        if (!jsonNode.has("type") || !"location_update".equals(jsonNode.get("type").asText())) {
            System.out.println("Invalid message type, ignoring.");
            return;
        }

        // Extract "data" object from JSON
        JsonNode dataNode = jsonNode.get("data");

        // Convert JSON "data" to BusLocationDTO
        BusLocationDTO location = objectMapper.treeToValue(dataNode, BusLocationDTO.class);

        if (location == null) {
            System.out.println("Invalid JSON format.");
            return;
        }

        // Store the latest location of the bus
        busLocations.put(location.getBusId(), location);

        // ✅ Send updated locations to all clients
        broadcastAllBusLocations();
    }

    private void broadcastAllBusLocations() throws Exception {
        if (sessions.isEmpty()) {
            System.out.println("No active WebSocket sessions. Skipping broadcast.");
            return;
        }

        String json = objectMapper.writeValueAsString(busLocations.values());
        System.out.println("Broadcasting: " + json);

        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(json));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
        System.out.println("WebSocket disconnected: " + session.getId());
    }
}
