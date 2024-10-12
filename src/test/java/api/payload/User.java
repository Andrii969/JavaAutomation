package api.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
//@Builder  ---> CHECK WHAT IT IS, AND WHAT USAGE
public class User {
    int id;
    String username;
//    @JsonProperty("first_name") // use if the field name in the class differs from the field name in Json
    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
    int userStatus;
}
