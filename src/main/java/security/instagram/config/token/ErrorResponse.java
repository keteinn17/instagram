package security.instagram.config.token;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import security.instagram.config.jackson.LocalDateTimeToStringSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ket_ein17
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse implements Serializable {
    @JsonSerialize(using = LocalDateTimeToStringSerializer.class)
    private LocalDateTime timestamp;
    private String error;
    private String error_description;
    private HttpStatus httpStatus;
}
