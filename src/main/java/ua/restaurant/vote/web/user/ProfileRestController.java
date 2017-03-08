package ua.restaurant.vote.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.restaurant.vote.AuthorizedUser;
import ua.restaurant.vote.model.User;
import ua.restaurant.vote.to.UserTo;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Galushkin Pavel on 05.03.2017.
 */
@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {
    static final String REST_URL = "/rest/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(AuthorizedUser.id());
    }

    @DeleteMapping
    public void delete() {
        super.delete(AuthorizedUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody UserTo userTo) {
        super.update(userTo, AuthorizedUser.id());
    }

    @GetMapping(value = "/text")
    public String testUTF() {
        return "Русский текст";
    }

    @GetMapping(value = "/between", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                    @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return super.getBetween(AuthorizedUser.id(), startDate, endDate);
    }
}
