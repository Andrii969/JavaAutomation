package api.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    @JsonProperty("id")
    Object id;
    @JsonProperty("username")
    Object username;
    @JsonProperty("firstName")
    Object firstName;
    @JsonProperty("lastName")
    Object lastName;
    @JsonProperty("email")
    Object email;
    @JsonProperty("password")
    Object password;
    @JsonProperty("phone")
    Object phone;
    @JsonProperty("userStatus")
    Object userStatus;
}