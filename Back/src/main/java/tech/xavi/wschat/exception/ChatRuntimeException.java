package tech.xavi.wschat.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ChatRuntimeException extends RuntimeException{

    private final ChatError error;
    private final HttpStatus httpStatus;

    public ChatRuntimeException(ChatError error, HttpStatus httpStatus) {
        this.error = error;
        this.httpStatus = httpStatus;
    }
}
