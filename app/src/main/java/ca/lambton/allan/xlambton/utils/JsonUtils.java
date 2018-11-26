package ca.lambton.allan.xlambton.utils;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {


    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonUtils() {
    }

    /**
     * Convert to Json
     *
     * @param value Object
     * @return JSON
     */
    public static String toJson(Object value) {
        return toJson(value, false);
    }

    /**
     * Convert to Json
     *
     * @param value    Object
     * @param isPretty pretty print option {true:false}
     * @return JSON
     */
    public static String toJson(Object value, boolean isPretty) {
        String json = null;
        try {
            if (isPretty) {
                json = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(value);
            } else {
                json = mapper.writeValueAsString(value);
            }
        } catch (JsonProcessingException e) {
            Log.e("JSON Utils", e.getMessage());
        }
        return json;
    }

    /**
     * Write JSON File
     *
     * @param value Object
     */
    public static void writeJson(Object value) {
        try {
            mapper.writeValue(new File(value.getClass().getSimpleName() + ".json"), value);
        } catch (IOException e) {
            Log.e("JSON Utils", e.getMessage());
        }
    }

    /**
     * Read JSON File
     *
     * @param fileName FileName or FilePath/FileName
     * @param clazz    Mapping Json Class
     * @return JSON Object
     */
    public static <T> T readJson(String fileName, Class<T> clazz) {
        return readJson(clazz.getClassLoader().getResourceAsStream(fileName), clazz);
    }

    /**
     * Read JSON File
     *
     * @param inputStream File
     * @param clazz       Mapping Json Class
     * @return JSON Object
     */
    public static <T> T readJson(InputStream inputStream, Class<T> clazz) {
        T obj = null;
        try {
            obj = mapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            Log.e("JSON Utils", e.getMessage());
        }
        return obj;
    }
}
