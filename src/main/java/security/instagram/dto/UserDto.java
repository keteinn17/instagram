package security.instagram.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import security.instagram.entity.Role;
import security.instagram.utils.annotation.FieldMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldMatch(first = "password", second = "confirmPassword", message = "Passwords must match")
public class UserDto {
    @JsonIgnore
    private Long userId;
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty
    private String username;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Date of birth must be in the format MM/dd/yyyy")
    private String dateOfBirth;

    @NotEmpty
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "^                                   \n# start of line\n" +
                    "  (?=.*[0-9])                       # positive lookahead, digit [0-9]\n" +
                    "  (?=.*[a-z])                       # positive lookahead, one lowercase character [a-z]\n" +
                    "  (?=.*[A-Z])                       # positive lookahead, one uppercase character [A-Z]\n" +
                    "  (?=.*[!@#&()–[{}]:;',?/*~$^+=<>]) # positive lookahead, one of the special character in this [..]\n" +
                    "  .                                 # matches anything\n" +
                    "  {8,20}                            # length at least 8 characters and maximum of 20 characters\n" +
                    "$                                   # end of line")
    private String password;

    @NotEmpty
    private String confirmPassword;
    @JsonIgnore
    private Role role;
//    private Gender gender;
}
