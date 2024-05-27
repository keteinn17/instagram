package security.instagram.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Getter
@AllArgsConstructor
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
