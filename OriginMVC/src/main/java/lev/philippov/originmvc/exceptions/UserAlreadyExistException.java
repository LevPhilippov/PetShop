package lev.philippov.originmvc.exceptions;

import lombok.Getter;

public class UserAlreadyExistException extends ServerException{

    private static final long serialVersionUID = 7093698835717003886L;

    public UserAlreadyExistException(String message) {
        super(message);
    }

}
