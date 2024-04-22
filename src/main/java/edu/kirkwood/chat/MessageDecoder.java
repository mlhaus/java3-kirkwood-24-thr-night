package edu.kirkwood.chat;

import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

import java.io.StringReader;

public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String s) throws DecodeException {
        JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
        return new Message(jsonObject);
    }

    @Override
    public boolean willDecode(String s) {
        boolean result;
        try {
            JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
            result = true;
        } catch (JsonException jex) {
            result = false;
        }
        return result;
    }

}