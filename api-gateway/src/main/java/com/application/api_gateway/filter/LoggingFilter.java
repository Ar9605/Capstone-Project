package com.application.api_gateway.filter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();

    // The Splunk details we just set up!
    private final String SPLUNK_URL = "http://localhost:8088/services/collector/event";
    private final String SPLUNK_TOKEN = "6e8d75d7-15b0-4f67-9cc5-7f3a94fe5f4c";

    @Override
    @SuppressWarnings("UseSpecificCatch")
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long duration = System.currentTimeMillis() - startTime;
            
            try {
                // 1. Organize the log data
                Map<String, Object> eventData = new HashMap<>();
                eventData.put("method", exchange.getRequest().getMethod().name());
                eventData.put("path", exchange.getRequest().getURI().getPath());
                eventData.put("status", exchange.getResponse().getStatusCode().value());
                eventData.put("duration_ms", duration);

                // 2. Wrap it in the "event" key exactly how Splunk demands
                Map<String, Object> splunkPayload = new HashMap<>();
                splunkPayload.put("event", eventData); 
                splunkPayload.put("sourcetype", "_json"); // Tells Splunk it's JSON formatted

                String jsonBody = objectMapper.writeValueAsString(splunkPayload);

                // 3. Fire the HTTP request to the Splunk Docker container
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(SPLUNK_URL))
                        .header("Authorization", "Splunk " + SPLUNK_TOKEN)
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();

                httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            } catch (Exception e) {
                logger.error("Failed to send log to Splunk", e);
            }
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
