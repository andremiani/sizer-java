package br.com.sizer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND)
public class DuplicateUserNameException extends RuntimeException {
    private static final long serialVersionUID = 1L;
	private String message;

    public DuplicateUserNameException(String message) {
        super(String.format("%s ", message));
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
