package ua.restaurant.vote.web.vote;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.restaurant.vote.AuthorizedUser;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.to.ResultTo;
import ua.restaurant.vote.to.VoteTo;
import ua.restaurant.vote.to.VoteToJSONView;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Galushkin Pavel on 12.03.2017.
 */
@RestController
@RequestMapping(value = VoteProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteProfileRestController extends AbstractVoteController {
    static final String REST_URL = "/rest/profile/votes";

    @Override
    @GetMapping
    public List<Vote> getAll() {
        return super.getAll();
    }

    // get specific vote
    @Override
    @GetMapping(value = "/{id}")
    public Vote get(@PathVariable("id") int id) {
        return super.get(id);
    }

    // get own list with votes for period
    @GetMapping(value = "/between")
    public List<VoteToJSONView> getWithUserForPeriod(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                                                     @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return super.getWithUserForPeriod(AuthorizedUser.id(), startDate, endDate);
    }

    // create new vote
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@Valid @RequestBody VoteTo voteTo) {
        Vote created = super.create(voteTo);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    // update vote
    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody VoteTo voteTo, @PathVariable("id") int id) {
        super.update(voteTo, id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    // poll result for the specified date. If date doesn't present, then date = today
    @Override
    @GetMapping(value = "/result")
    public List<ResultTo> getResultSet(@RequestParam(value = "date", required = false) LocalDate date) {
        return super.getResultSet(date);
    }
}
