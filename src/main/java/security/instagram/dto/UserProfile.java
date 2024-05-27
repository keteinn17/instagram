package security.instagram.dto;

import lombok.*;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfile {
    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private String dateOfBirth;

//    private Gender gender;
    private Long avatarId;

}
