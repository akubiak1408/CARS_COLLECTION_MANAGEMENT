package ak.app.exceptions;

import java.time.LocalDateTime;

public class MyException extends RuntimeException {
    private String exceptionMessage;
    private LocalDateTime exceptionDateTime;

    public MyException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        this.exceptionDateTime = LocalDateTime.now();
    }

    public LocalDateTime getExceptionDateTime() {
        return exceptionDateTime;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
