package lev.philippov.originmvc.exceptions;

import lev.philippov.originmvc.models.User;
import lombok.Getter;

public class UserAlreadyExistException extends ServerException{

    private static final long serialVersionUID = 7093698835717003886L;

    @Getter
    private final User user;

    public UserAlreadyExistException(String message, User user) {
        super(message);
        this.user = user;
    }

}
