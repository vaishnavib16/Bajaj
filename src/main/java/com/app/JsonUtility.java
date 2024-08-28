package com.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class JsonUtility {
	public static String findDestinationValue(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            
            JsonNode rootNode = objectMapper.readTree(new File(filePath));
            return findKey(rootNode, "destination");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; 
    }
	
	private static String findKey(JsonNode node, String key) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if (field.getKey().equals(key)) {
                    return field.getValue().asText();
                }
                
                String result = findKey(field.getValue(), key);
                if (result != null) {
                    return result;
                }
            }
        } else if (node.isArray()) {
            for (JsonNode item : node) {
                String result = findKey(item, key);
                if (result != null) {
                    return result;
                }
            }
        }
        return null; 
    }
}
