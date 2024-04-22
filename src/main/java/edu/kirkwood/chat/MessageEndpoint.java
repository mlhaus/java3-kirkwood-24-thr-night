package edu.kirkwood.chat;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(
        value = "/chat/message-endpoint",
        encoders = {MessageEncoder.class},
        decoders = {MessageDecoder.class}
)
public class MessageEndpoint {
    private static final Set<Session> subscribers = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("MessageEndpoint.onOpen(), session: " + session);
        subscribers.add(session);
    }

    @OnMessage
    public void onMessage(Message message, Session session) throws EncodeException, IOException {
        System.out.println("MessageEndpoint.onMessage(), message: " + message);
        for (Session subscriber : subscribers) {
            if (!subscriber.equals(session)) {
                subscriber.getBasicRemote().sendObject(message);
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("MessageEndpoint.onClose(), session: " + session);
        subscribers.remove(session);
    }

    @OnError
    public void onError(Throwable throwable) {
        System.err.println("ERROR: " + throwable.getMessage());
    }

}
