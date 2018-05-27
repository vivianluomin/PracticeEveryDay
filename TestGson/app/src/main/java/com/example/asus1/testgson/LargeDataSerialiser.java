package com.example.asus1.testgson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by asus1 on 2017/9/13.
 */

public class LargeDataSerialiser implements JsonSerializer<LargeData> {

    @Override
    public JsonElement serialize(LargeData largeData, Type type, JsonSerializationContext jsonSerializationContext) {

        final JsonArray jsonNumbers =  new JsonArray();
        for(final long number :largeData.getNumbers()){
            jsonNumbers.add(new JsonPrimitive(number));

        }
        final JsonObject jsonObject = new JsonObject();

            jsonObject.add("numbers",jsonNumbers);

        return jsonObject;

    }


}
