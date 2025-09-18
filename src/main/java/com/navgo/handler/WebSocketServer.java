package com.navgo.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navgo.dto.BusLocationDTO;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketServer extends TextWebSocketHandler {

    // Stores the latest known location for each bus
    private final Map<String, BusLocationDTO> busLocations = new ConcurrentHashMap<>();
    
    // Maps a topic (busNumber) to a set of subscribed sessions
    private final Map<String, Set<WebSocketSession>> topicSubscriptions = new ConcurrentHashMap<>();
    
    // Maps a session to all topics it is subscribed to (for easy cleanup on disconnect)
    private final Map<WebSocketSession, Set<String>> sessionTopics = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("New connection: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        JsonNode jsonNode = objectMapper.readTree(payload);

        // Checking the message "type" to decide what to do
        String type = jsonNode.has("type") ? jsonNode.get("type").asText() : null;

        if ("location_update".equals(type)) {
            handleLocationUpdate(jsonNode);
        } else if ("subscribe".equals(type)) {
            handleSubscription(session, jsonNode);
        } else if ("unsubscribe".equals(type)) {
            handleUnsubscription(session, jsonNode);
        } else {
           
            try {
                BusLocationDTO location = objectMapper.readValue(payload, BusLocationDTO.class);
                if (location.getBusId() != null && location.getLat() != 0) {
                     // Store the latest location
                    busLocations.put(location.getBusId(), location);
                    // Broadcast this update ONLY to subscribers of this bus
                    broadcastToTopic(location.getBusId(), location);
                }
            } catch (Exception e) {
                System.out.println("Received unknown message format: " + payload);
            }
        }
    }

    private void handleLocationUpdate(JsonNode jsonNode) throws Exception {
        JsonNode dataNode = jsonNode.get("data");
        BusLocationDTO location = objectMapper.treeToValue(dataNode, BusLocationDTO.class);
        if (location != null && location.getBusId() != null) {
            // Store the latest location
            busLocations.put(location.getBusId(), location);
            // Broadcast this update ONLY to subscribers of this bus
            broadcastToTopic(location.getBusId(), location);
        }
    }

    private void handleSubscription(WebSocketSession session, JsonNode jsonNode) {
        String busNumber = jsonNode.has("busNumber") ? jsonNode.get("busNumber").asText() : null;
        if (busNumber != null) {
            // Add session to the topic's subscriber list
            topicSubscriptions.computeIfAbsent(busNumber, k -> ConcurrentHashMap.newKeySet()).add(session);
            // Track that this session is subscribed to this topic
            sessionTopics.computeIfAbsent(session, k -> ConcurrentHashMap.newKeySet()).add(busNumber);
            System.out.println("Session " + session.getId() + " subscribed to bus " + busNumber);

            // Immediately send the latest known location if we have it
            if (busLocations.containsKey(busNumber)) {
                try {
                    String json = objectMapper.writeValueAsString(busLocations.get(busNumber));
                    session.sendMessage(new TextMessage(json));
                } catch (Exception e) {
                    System.err.println("Error sending initial location data: " + e.getMessage());
                }
            }
        }
    }

    private void handleUnsubscription(WebSocketSession session, JsonNode jsonNode) {
        String busNumber = jsonNode.has("busNumber") ? jsonNode.get("busNumber").asText() : null;
        if (busNumber != null) {
            // Remove session from the topic's subscriber list
            if (topicSubscriptions.containsKey(busNumber)) {
                topicSubscriptions.get(busNumber).remove(session);
            }
            // Remove the topic from this session's tracking list
            if (sessionTopics.containsKey(session)) {
                sessionTopics.get(session).remove(busNumber);
            }
            System.out.println("Session " + session.getId() + " unsubscribed from bus " + busNumber);
        }
    }

    private void broadcastToTopic(String busNumber, BusLocationDTO location) throws Exception {
        Set<WebSocketSession> subscribers = topicSubscriptions.get(busNumber);
        if (subscribers == null || subscribers.isEmpty()) {
            return; // No one is listening for this bus
        }

        String json = objectMapper.writeValueAsString(location);
        for (WebSocketSession subscriber : subscribers) {
            if (subscriber.isOpen()) {
                subscriber.sendMessage(new TextMessage(json));

            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // This is important for cleanup!
        // Remove the disconnected session from all topics it was subscribed to.
        Set<String> topics = sessionTopics.remove(session);
        if (topics != null) {
            for (String busNumber : topics) {
                if (topicSubscriptions.containsKey(busNumber)) {
                    topicSubscriptions.get(busNumber).remove(session);
                }
            }
        }
        System.out.println("Connection closed: " + session.getId() + ". Cleaned up subscriptions.");
    }
}