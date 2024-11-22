package codemetropolis.toolchain.rendering.util;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.Map;


public class JsonUtil {

    /**
     * This method converts a map of spawner block data into a json string which is then returned
     *
     * @param map map object in which the spawner data is stored
     */
    public static String convertMapToJson(Map<String, String> map) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            assertJsonString(objectMapper.writeValueAsString(map));
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method checks if the string that contains spawner data contains ; character, if it does, it throws an
     * exception
     *
     * @param jsonString the string that contains spawner data and need to be checked for ; character
     */
    public static void assertJsonString(String jsonString) {
        if (jsonString.contains(";")) {
            throw new IllegalArgumentException("Json string cannot contain semicolons! The blocks' individual " +
                    "data such as position and block type are separated by semicolons in the csv file. A semicolon in " +
                    "the json would break the structure of the csv file.");
        }
    }

    /**
     * This method converts the json string of a block's info data into a map object which is then returned
     *
     * @param json the json string that contains the block's extra data
     */
    public static Map<String, String> convertJsonStringToMap(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, String>> typeRef = new TypeReference<Map<String, String>>() {};
            return objectMapper.readValue(json, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
