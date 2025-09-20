package com.navgo.handler;

/**
 * @author Akash Bais
 *
 */

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navgo.dto.BusLocationDTO;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WebSocketServer extends TextWebSocketHandler {

    private final Map<String, BusLocationDTO> busLocations = new ConcurrentHashMap<>();
    private final Map<String, Set<WebSocketSession>> topicSubscriptions = new ConcurrentHashMap<>();
    private final Map<WebSocketSession, Set<String>> sessionTopics = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public WebSocketServer() {
        scheduler.scheduleAtFixedRate(this::checkStaleBuses, 30, 30, TimeUnit.SECONDS);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String query = session.getUri().getQuery(); // e.g., "busNumber=17"
        String busNumber = null;

        if (query != null && query.startsWith("busNumber=")) {
            busNumber = query.split("=")[1];
        }

        if (busNumber != null) {
            System.out.println("Driver for Bus #" + busNumber + " connected with session ID: " + session.getId());
        } else {
            System.out.println("Web client connected with session ID: " + session.getId());
        }

        if (!busLocations.isEmpty()) {
            try {
                String allLocationsJson = objectMapper.writeValueAsString(busLocations.values());
                session.sendMessage(new TextMessage(allLocationsJson));
                System.out.println("Sent initial locations to new session: " + session.getId());
            } catch (Exception e) {
                System.err.println("Error sending initial locations: " + e.getMessage());
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        JsonNode jsonNode = objectMapper.readTree(payload);

        String type = jsonNode.has("type") ? jsonNode.get("type").asText() : null;

        if ("subscribe".equals(type)) {
            handleSubscription(session, jsonNode);
        } else if ("unsubscribe".equals(type)) {
            handleUnsubscription(session, jsonNode);
        } else {
            // Raw location data from the Android driver app
            try {
                BusLocationDTO location = objectMapper.readValue(payload, BusLocationDTO.class);

                if (location.getBusNumber() != null && location.getLat() != 0) {
                    System.out.println(" Successfully processed location for Bus #" + location.getBusNumber());
                    busLocations.put(location.getBusNumber(), location);
                    broadcastToTopic(location.getBusNumber(), location);
                }
            } catch (Exception e) {
                System.out.println("   Received a message that could not be parsed. Payload: " + payload);
                System.err.println("   The specific error was: " + e.getMessage());
            }
        }
    }

    private void checkStaleBuses() {
        System.out.println("Running stale bus check...");
        long currentTime = System.currentTimeMillis();
        long staleThreshold = 60000; // 60 seconds

        // Find and remove buses that haven't sent an update in over a minute
        boolean removed = busLocations.values().removeIf(
            bus -> (currentTime - bus.getTimestamp()) > staleThreshold
        );

        // If a bus was removed, broadcast the new, clean list to all web clients
        if (removed) {
            System.out.println("Stale buses found and removed. Broadcasting updated list.");
            try {
                broadcastFullBusList();
            } catch (IOException e) {
                System.err.println("Error broadcasting after removing stale buses: " + e.getMessage());
            }
        }
    }

    // --- ADD THIS NEW HELPER METHOD ---
    private void broadcastFullBusList() throws IOException {
        String allLocationsJson = objectMapper.writeValueAsString(busLocations.values());
        
        // Create a set of unique sessions that are subscribed to any topic
        Set<WebSocketSession> allSubscribers = ConcurrentHashMap.newKeySet();
        topicSubscriptions.values().forEach(allSubscribers::addAll);

        for (WebSocketSession session : allSubscribers) {
             if (session.isOpen()) {
                session.sendMessage(new TextMessage(allLocationsJson));
             }
        }
    }

    private void handleSubscription(WebSocketSession session, JsonNode jsonNode) {
        String busNumber = jsonNode.has("busNumber") ? jsonNode.get("busNumber").asText() : null;
        if (busNumber != null) {
            topicSubscriptions.computeIfAbsent(busNumber, k -> ConcurrentHashMap.newKeySet()).add(session);
            sessionTopics.computeIfAbsent(session, k -> ConcurrentHashMap.newKeySet()).add(busNumber);
            System.out.println("Session " + session.getId() + " subscribed to bus " + busNumber);

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
            if (topicSubscriptions.containsKey(busNumber)) {
                topicSubscriptions.get(busNumber).remove(session);
            }
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