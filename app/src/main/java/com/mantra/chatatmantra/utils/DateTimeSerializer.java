package com.mantra.chatatmantra.utils;

/**
 * Created by rajat on 28/05/16.
 */

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.lang.reflect.Type;

public class DateTimeSerializer implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

    public static final DateTimeFormatter FORMAT = ISODateTimeFormat.dateTime().withOffsetParsed().withZone(DateTimeZone.getDefault());
    public static final long MILLIS = 1000L;

    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null || json.isJsonNull()) {
            return null;
        } else if (json.getAsJsonPrimitive().isNumber()) {
            return new DateTime(json.getAsLong() * MILLIS, DateTimeZone.UTC);
        } else {
            return FORMAT.parseDateTime(json.getAsString());
        }
    }

    @Override
    public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return JsonNull.INSTANCE;
        } else {
            return new JsonPrimitive(FORMAT.print(src));
        }
    }

}
