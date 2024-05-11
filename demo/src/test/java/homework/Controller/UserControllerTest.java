package homework.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import homework.DO.AuthenticationHeader;
import homework.Service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {UserController.class, AuthenticationService.class})
public class UserControllerTest {

    @Mock
    private AuthenticationService authService;

    @InjectMocks
    private UserController userController;

    String resource = "a";
    String authHeader = "";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        authHeader = authService.getBase64Header();
        ObjectMapper objectMapper = new ObjectMapper();

        // 创建一个简单的JSON对象
        ObjectNode jsonObject = objectMapper.createObjectNode();
        jsonObject.put("userId", "123456");
        jsonObject.put("accountName", "alice123");
        jsonObject.put("role", "admin1");

        // 序列化JSON对象为字符串
        String jsonString = objectMapper.writeValueAsString(jsonObject);

        // JSON字符串转Base64编码
        byte[] jsonBytes = jsonString.getBytes(StandardCharsets.UTF_8);
        String s= Base64.getEncoder().encodeToString(jsonBytes);
        System.out.println(s);
    }

    @Test
    public void whenAccessResourceWithValidAuthHeaderAndResource_thenReturnSuccess() throws AuthenticationService.AuthenticationException {
        // Arrange
        AuthenticationHeader header = new AuthenticationHeader().setUserId(123456).setRole("user").setAccountName("testUser");
        when(authService.parseAndValidateHeader(authHeader)).thenReturn(header);

        // Act
        ResponseEntity<?> response = userController.accessResource(resource, authHeader);

        // Assert
        assertEquals(ResponseEntity.ok().body("You have access to the resource: " + resource), response);
    }
}
