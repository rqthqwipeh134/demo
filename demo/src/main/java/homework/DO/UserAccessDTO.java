package homework.DO;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserAccessDTO {

    @JsonProperty("userId")
    @NotNull
    private Long userId;
    @JsonProperty("endpoints")
    @NotNull
    private List<String> endpoints;

    public UserAccessDTO(long i, List<String> asList) {
        this.userId = i;
        this.endpoints = asList;

    }
}
