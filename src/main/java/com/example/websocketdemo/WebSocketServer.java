
package com.example.websocketdemo;

import com.example.websocketdemo.util.CipherUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value = "/ws/{arg}")
@Component
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    private static final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap<>();
    private static final CipherUtils cipherUtils = new CipherUtils("-unify~!@#qaz019");

    @OnOpen
    public void onOpen(Session session, @PathParam("arg") String arg) {
        String sessionId = session.getId();
        SESSIONS.put(sessionId, session);

        logger.info("Received raw params: {}", arg);

        try {
            // URL decode the params
            String urlDecodedParams = URLDecoder.decode(arg, StandardCharsets.UTF_8.name());
            logger.info("URL decoded params: {}", urlDecodedParams);

            // Convert URL-safe Base64 to standard Base64
            String standardBase64 = urlSafeToStandardBase64(urlDecodedParams);
            logger.info("Standard Base64: {}", standardBase64);

            // Decrypt the params
            String decryptedParams = cipherUtils.decryptData(standardBase64);

            if (decryptedParams != null) {
                logger.info("Decrypted params: {}", decryptedParams);
                logger.info("New WebSocket connection: {} with decoded params: {}", sessionId, decryptedParams);
                logger.info("Total active sessions: {}", SESSIONS.size());

                session.getBasicRemote().sendText("Connected successfully with params: " + decryptedParams);
                logger.info("Sent connection confirmation to session: {}", sessionId);
            } else {
                logger.error("Failed to decrypt params for session: {}", sessionId);
                session.getBasicRemote().sendText("Connected, but failed to decrypt parameters");
            }
        } catch (Exception e) {
            logger.error("Error processing connection for session: {}", sessionId, e);
            try {
                session.getBasicRemote().sendText("Error occurred while processing connection");
            } catch (IOException ioException) {
                logger.error("Failed to send error message to client", ioException);
            }
        }
    }

    private String urlSafeToStandardBase64(String urlSafeBase64) {
        String base64 = urlSafeBase64.replace('-', '+').replace('_', '/');
        while (base64.length() % 4 != 0) {
            base64 += "=";
        }
        return base64;
    }

    @OnClose
    public void onClose(Session session) {
        String sessionId = session.getId();
        SESSIONS.remove(sessionId);
        logger.info("WebSocket connection closed: {}", sessionId);
        logger.info("Total active sessions: {}", SESSIONS.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        String sessionId = session.getId();
        logger.info("Received message from {}: {}", sessionId, message);
        try {
            session.getBasicRemote().sendText("Server received: " + message);
            logger.info("Sent response to session: {}", sessionId);
        } catch (IOException e) {
            logger.error("Error sending message to session: {}", sessionId, e);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        String sessionId = session.getId();
        logger.error("WebSocket error for session {}", sessionId, error);
        try {
            session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, "Internal server error"));
            logger.info("Closed session {} due to error", sessionId);
        } catch (IOException e) {
            logger.error("Error closing WebSocket session: {}", sessionId, e);
        }
    }
}

// WebSocketConfig.java 和 Application.java 保持不变