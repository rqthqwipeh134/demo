package homework.Service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import homework.DO.AuthenticationHeader;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;


@Service
public class AuthenticationService {

    public AuthenticationHeader parseAndValidateHeader(String base64Header) throws AuthenticationException {
        // Decode, parse, and validate the header
        if (base64Header == null || base64Header.isEmpty()) {
            throw new IllegalArgumentException("Base64 header cannot be null or empty.");
        }

        try {
            // Base64字符串转回JSON字符串
            byte[] decodedBytes = Base64.getDecoder().decode(base64Header);
            String decodedJsonStr = new String(decodedBytes, StandardCharsets.UTF_8);
            System.out.println("Decoded JSON: " + decodedJsonStr);

            // 将JSON字符串反序列化回ObjectNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode decodedObject = objectMapper.readTree(decodedJsonStr);

            return new AuthenticationHeader()
                    .setUserId(decodedObject.get("userId").asLong())
                    .setRole(decodedObject.get("role").asText())
                    .setAccountName(decodedObject.get("accountName").asText());
        } catch (Exception e) {
            throw new AuthenticationException("Failed to parse and validate the authentication header.", e);
        }
    }

    public static class AuthenticationException extends Exception {

        public AuthenticationException(String message) {
            super(message);
        }

        public AuthenticationException(String message, Throwable cause) {
            super(message, cause);
        }
    }


    @SneakyThrows
    public String getBase64Header() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 创建一个简单的JSON对象
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("userId", "123456");
        jsonObject.put("accountName", "alice123");
        jsonObject.put("role", "admin");

        // 序列化JSON对象为字符串
        String jsonString = objectMapper.writeValueAsString(jsonObject);

        // JSON字符串转Base64编码
        byte[] jsonBytes = jsonString.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(jsonBytes);
    }
}
