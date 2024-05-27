package security.instagram.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_refresh_token")
public class UserRefreshToken {
    @Id
    private String email;
    private String refresh_token_id;
    @Basic(fetch = FetchType.LAZY)
    private byte[] refresh_token;
    @Column(name = "access_token_id")
    private String ati;
    private Integer refresh_token_expiration;
}