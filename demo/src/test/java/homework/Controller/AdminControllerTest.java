package homework.Controller;

import homework.Service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AdminController.class, AuthenticationService.class})
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenAddUserWithException_thenInternalServerError() throws Exception {

        // Perform the request and expect an response
        mockMvc.perform(post("/admin/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"userId\": 123456," +
                        "\"endpoints\": [\"a\", \"b\"]" +
                        "}")
                .header("Authorization", "ewoidXNlcklkIjoxMjM0NTYsCiJhY2NvdW50TmFtZSI6ICJYWFhYWFhYIiwKInJvbGUiOiAiYWRtaW4iCn0="))
                .andExpect(status().isOk());
    }

}
