package com.example.asus1.testgson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by asus1 on 2017/9/13.
 */

public class LagerDataTypeAdaper extends TypeAdapter<LargeData> {

    @Override
    public LargeData read(JsonReader jsonReader) throws IOException {
        return null;
    }

    @Override
    public void write(JsonWriter jsonWriter, LargeData largeData) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("numbers");
        jsonWriter.beginArray();
        for (long number : largeData.getNumbers()) {
            jsonWriter.value(number);
        }
        jsonWriter.endArray();
        jsonWriter.endObject();
    }
}
