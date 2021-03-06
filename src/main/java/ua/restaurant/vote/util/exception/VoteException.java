package ua.restaurant.vote.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Galushkin Pavel on 09.03.2017.
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "You've already voted today") // 422
public class VoteException  extends RuntimeException {
    public VoteException(String message) {
        super(message);
    }
}