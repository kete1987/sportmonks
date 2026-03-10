package es.com.kete1987.sportmonks.library.v3.model.subscription;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class SubscriptionMetaDeserializer implements JsonDeserializer<SubscriptionMeta> {

    @Override
    public SubscriptionMeta deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        if (json == null || json.isJsonNull()) {
            return null;
        }

        JsonObject obj;

        // Caso antiguo: "meta": { ... }
        if (json.isJsonObject()) {
            obj = json.getAsJsonObject();
        }
        // Caso nuevo: "meta": [ { ... } ]
        else if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();
            if (array.isEmpty() || array.get(0) == null || !array.get(0).isJsonObject()) {
                return null;
            }
            obj = array.get(0).getAsJsonObject();
        } else {
            throw new JsonParseException("Formato no soportado para meta: " + json);
        }

        SubscriptionMeta meta = new SubscriptionMeta();

        JsonElement trialEndsAt = obj.get("trial_ends_at");
        if (trialEndsAt != null && !trialEndsAt.isJsonNull()) {
            meta.setTrialEndsAt(trialEndsAt.getAsString());
        }

        JsonElement endsAt = obj.get("ends_at");
        if (endsAt != null && !endsAt.isJsonNull()) {
            meta.setEndsAt(endsAt.getAsString());
        }

        return meta;
    }
}