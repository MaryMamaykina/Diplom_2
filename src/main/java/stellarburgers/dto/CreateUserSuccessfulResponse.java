package stellarburgers.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserSuccessfulResponse {
    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;

}
