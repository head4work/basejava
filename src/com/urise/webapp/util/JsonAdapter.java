package com.urise.webapp.util;

import com.google.gson.*;
import com.urise.webapp.model.Section;

import java.lang.reflect.Type;

public class JsonAdapter implements JsonSerializer<Section>, JsonDeserializer<Section> {

    @Override
    public Section deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            // Specify the package name + class name
            String thePackage = "com.urise.webapp.model.";
            return context.deserialize(element, Class.forName(thePackage + type));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }

    @Override
    public JsonElement serialize(Section src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));

        return result;
    }
}