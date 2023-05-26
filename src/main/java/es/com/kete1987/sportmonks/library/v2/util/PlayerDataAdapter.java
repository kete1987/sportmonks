package es.com.kete1987.sportmonks.library.v2.util;

import com.google.gson.*;
import es.com.kete1987.sportmonks.library.v2.model.player.PlayerData;

import java.lang.reflect.Type;

public class PlayerDataAdapter implements JsonDeserializer<PlayerData> {
    public PlayerData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) {
        try {
            JsonObject jsonObject = json.getAsJsonObject();
            jsonObject.getAsJsonObject("data");
            Gson gson = new Gson();
            return gson.fromJson(json.toString(), PlayerData.class);
        } catch (Exception e) {
            return null;
        }
    }
}