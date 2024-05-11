package homework.DO;


import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class AuthenticationHeader {
    @NotNull
    private long userId;
    @NotNull
    private String accountName;
    @NotNull
    private String role;

    public AuthenticationHeader() {

    }
}
