/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class GsonUtils {

    public static Gson createGson() {
        GsonBuilder builder = new GsonBuilder();

        // Adaptador para LocalDate
        builder.registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
            @Override
            public void write(JsonWriter out, LocalDate value) throws IOException {
                out.value(value != null ? value.toString() : null);
            }

            @Override
            public LocalDate read(JsonReader in) throws IOException {
                String str = in.nextString();
                return str != null ? LocalDate.parse(str) : null;
            }
        });

        // Adaptador para LocalTime
        builder.registerTypeAdapter(LocalTime.class, new TypeAdapter<LocalTime>() {
            @Override
            public void write(JsonWriter out, LocalTime value) throws IOException {
                out.value(value != null ? value.toString() : null);
            }

            @Override
            public LocalTime read(JsonReader in) throws IOException {
                String str = in.nextString();
                return str != null ? LocalTime.parse(str) : null;
            }
        });

        return builder.create();
    }
}
