package com.kete.sportmonks.library.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kete.sportmonks.library.model.player.PlayerData;

public class PlayerDataAdapter implements JsonDeserializer<PlayerData> {
    public PlayerData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) {
    	try
    	{
    		JsonObject jsonObject = json.getAsJsonObject();
    		jsonObject.getAsJsonObject("data");
    		Gson gson = new Gson();
    		return gson.fromJson(json.toString(), PlayerData.class);
    	}
    	catch (Exception e)
    	{
    		return null;
    	}
    }
}