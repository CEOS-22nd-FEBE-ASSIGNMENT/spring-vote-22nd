package vote.vote_be.domain.vote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vote.vote_be.domain.user.entity.User;
import vote.vote_be.domain.vote.dto.CandidateComponent;
import vote.vote_be.domain.vote.entity.Vote;
import vote.vote_be.domain.vote.entity.VoteCategory;
import vote.vote_be.domain.vote.repository.VoteRepository;
import vote.vote_be.global.apiPayload.code.status.ErrorStatus;
import vote.vote_be.global.apiPayload.exception.GeneralException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    public void existAlreadyVote(User user, VoteCategory voteCategory) {
        if (voteRepository.existsByUserAndVoteCategory(user, voteCategory)){
            throw new GeneralException(ErrorStatus.DUPLICATE_VOTE);
        }
    }

    public void save(Vote vote) {
        voteRepository.save(vote);
    }

    public int getTotalVotes(VoteCategory voteCategory) {
        return voteRepository.countsByVoteCategory(voteCategory);
    }
}
