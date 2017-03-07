package ua.restaurant.vote.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.restaurant.vote.TestUtil;
import ua.restaurant.vote.model.Vote;

import java.util.Arrays;

import static ua.restaurant.vote.VoteTestData.*;

/**
 * Created by Galushkin Pavel on 07.03.2017.
 */
public abstract class AbstractVoteServiceTest extends AbstractServiceTest {
    @Autowired
    VoteService service;

    @Test
    public void testSave() throws Exception {
        Vote newVote = getCreated();
        Vote created = service.save(newVote, 100_000, 100_006);
        newVote.setId(created.getId());
        TestUtil.prntCollect(service.getAll(100_000));
        //MATCHER.assertCollectionEquals(Arrays.asList(RESTAURANT1, RESTAURANT2, RESTAURANT3, created), service.getAll());
    }
}
