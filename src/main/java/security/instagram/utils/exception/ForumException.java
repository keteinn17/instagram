package security.instagram.utils.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Getter
public class ForumException extends RuntimeException {

    private ErrorCode errorCode = ErrorCode.OK;

    private HttpStatus status = HttpStatus.OK;

    private String message;

    public ForumException() {
    }

    public ForumException(String message) {
        this.message=message;
    }

    public ForumException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = this.errorCode.message;
    }

    public enum ErrorCode {
        OK(1, "OK"),
        INVALID_GENDER(2, "Invalid user gender"),
        INVALID_ACCOUNT_TYPE(3, "Invalid user gender"),
        INVALID_USER_FORM(4, "Invalid user form"),
        CLIENT_ERROR(5, "Client side error"),
        INTERNAL_ERROR(6, "Internal server error"),
        SENDER_SERVICE_ERROR(7, "Email Sender Service error"),
        INVALID_ARGUMENT(8, "Invalid argument!");


        ErrorCode(int index, String message) {
            this.index = index;
            this.message = message;
        }

        public String message;
        public int index;
    }
}

