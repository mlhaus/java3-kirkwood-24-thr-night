package edu.kirkwood.learnx.controllers;

import edu.kirkwood.shared.MyDecoder;
import edu.kirkwood.shared.MyEncoder;
import edu.kirkwood.shared.MyJson;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(
        value = "/group-chat/endpoint",
        encoders = {MyEncoder.class},
        decoders = {MyDecoder.class}
)
public class GroupChatEndpoint {
    private static final Set<Session> subscribers = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        subscribers.add(session);
        System.out.println("Session count: " + subscribers.size());
    }

    @OnClose
    public void onClose(Session session) {
        subscribers.remove(session);
        System.out.println("Session count: " + subscribers.size());
    }

    @OnError
    public void onError(Throwable throwable) {

    }

    @OnMessage
    public void onMessage(MyJson myJson, Session session) throws EncodeException, IOException {
//        System.out.println("Forwarding: " + myJson);
        for(Session subscriber : subscribers) {
            if(!subscriber.equals(session)) {
                subscriber.getBasicRemote().sendObject(myJson);
            }
        }
    }


}
