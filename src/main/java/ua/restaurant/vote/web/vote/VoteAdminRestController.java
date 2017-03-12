package ua.restaurant.vote.web.vote;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Galushkin Pavel on 12.03.2017.
 */
@RestController
@RequestMapping(VoteAdminRestController.REST_URL)
public class VoteAdminRestController extends AbstractVoteController{
    static final String REST_URL = "/rest/admin/votes";
//TODO VoteAdminRestController + VoteRestTests
   /* @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }                               // delete*/
}
