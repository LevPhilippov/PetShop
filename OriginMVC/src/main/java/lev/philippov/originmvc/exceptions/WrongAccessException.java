package lev.philippov.originmvc.exceptions;

public class WrongAccessException extends ServerException{

    public WrongAccessException(String message) {
        super(message);
    }

    public WrongAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
