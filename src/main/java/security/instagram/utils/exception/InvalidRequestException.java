package security.instagram.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Getter
@Setter
@AllArgsConstructor
public class InvalidRequestException extends RuntimeException{
    private LocalDateTime timestamp;
    private String error;
    private String error_description;
    private HttpStatus httpStatus;
}
