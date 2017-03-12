package ua.restaurant.vote.web.vote;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.restaurant.vote.AuthorizedUser;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.to.ResultTo;
import ua.restaurant.vote.to.VoteTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Galushkin Pavel on 12.03.2017.
 */
@RestController
@RequestMapping(VoteProfileRestController.REST_URL)
public class VoteProfileRestController extends AbstractVoteController {
    static final String REST_URL = "/rest/profile/votes";

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAll() {
        return super.getAll();
    }

    // get specific vote
    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote get(@PathVariable("id") int id) {
        return super.get(id);
    }

    // get own list with votes for period
    @GetMapping(value = "/between", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteTo> get(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                            @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return super.getWithUserForPeriod(AuthorizedUser.id(), startDate, endDate);
    }

    // create new vote
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@Valid @RequestBody Vote vote, @PathVariable("restaurantId") int restaurantId) {
        Vote created = super.create(vote, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    // update vote
    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Vote vote, @PathVariable("id") int id, @PathVariable("restaurantId") int restaurantId) {
        super.update(vote, id, restaurantId);
    }

    // poll result
    @Override
    @GetMapping(value = "/result", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResultTo> getResultSet(@RequestParam(value = "date", required = false) LocalDate date) {
        return super.getResultSet(date);
    }
}
