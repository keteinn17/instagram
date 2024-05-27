package security.instagram.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

/**
 * @author ket_ein17
 * @Date 5/27/2024
 */
@Getter
@AllArgsConstructor
public class ForumMessage {
    private Date timestamp;
    private Object message;
    private String description;
}

