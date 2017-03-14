package ua.restaurant.vote.web.vote;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.restaurant.vote.model.Vote;
import ua.restaurant.vote.to.VoteTo;
import ua.restaurant.vote.to.VoteToJSONView;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Galushkin Pavel on 12.03.2017.
 */
@RestController
@RequestMapping(VoteAdminRestController.REST_URL)
public class VoteAdminRestController extends AbstractVoteController {
    static final String REST_URL = "/rest/admin/votes";
    /*
     * admin can remove the vote at any time
     */
    @Override
    @DeleteMapping(value = "/{id}/users/{userId}")
    public void delete(@PathVariable("id") int id, @PathVariable("userId") int userId) {
        super.delete(id, userId);
    }

    // get all votes from concrete user
    @Override
    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAll(@PathVariable("userId") int userId) {
        return super.getAll(userId);
    }

    // get user's list with votes for period
    @GetMapping(value = "/users/{userId}/between", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteToJSONView> getWithUserForPeriod(@PathVariable("userId") int userId,
                                                     @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                                     @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return super.getWithUserForPeriod(userId, startDate, endDate);
    }

    // get votes list for period for specific restaurant
    @GetMapping(value = "/restaurants/{restId}/between", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteToJSONView> getWithRestaurantForPeriod(@PathVariable("restId") int restId,
                                                   @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                                   @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return super.getWithRestaurantForPeriod(restId, startDate, endDate);
    }
}
