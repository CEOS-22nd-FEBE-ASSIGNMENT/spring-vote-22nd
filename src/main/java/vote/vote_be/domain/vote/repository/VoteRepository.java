package vote.vote_be.domain.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vote.vote_be.domain.user.entity.User;
import vote.vote_be.domain.vote.dto.CandidateComponent;
import vote.vote_be.domain.vote.entity.Vote;
import vote.vote_be.domain.vote.entity.VoteCategory;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByUserAndVoteCategory(User user, VoteCategory voteCategory);


    @Query("select count(v.id) " +
            "from Vote v " +
            "where v.voteCategory = :voteCategory")
    int countsByVoteCategory(VoteCategory voteCategory);
}
