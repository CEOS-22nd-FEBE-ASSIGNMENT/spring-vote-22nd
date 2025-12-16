package vote.vote_be.domain.vote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vote.vote_be.domain.user.entity.User;
import vote.vote_be.domain.vote.dto.CandidateComponent;
import vote.vote_be.domain.vote.dto.request.VoteRequestDto;
import vote.vote_be.domain.vote.dto.response.CandidateListResponseDto;
import vote.vote_be.domain.vote.dto.response.VoteResultListResponseDto;
import vote.vote_be.domain.vote.entity.Candidate;
import vote.vote_be.domain.vote.entity.Vote;
import vote.vote_be.domain.vote.entity.VoteCategory;
import vote.vote_be.global.apiPayload.code.status.ErrorStatus;
import vote.vote_be.global.apiPayload.exception.GeneralException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DemodayVoteService {

    private final CandidateService candidateService;
    private final VoteService voteService;

    @Transactional(readOnly = true)
    public List<CandidateListResponseDto> getDemodayCandidateList() {
        return candidateService.getAllByVoteCategory(VoteCategory.DEMODAY);
    }

    @Transactional
    public void voteDemoday(User user, VoteRequestDto requestDto) {
        Candidate candidate = candidateService.getById(requestDto.candidateId());

        if (candidate.getVoteCategory() != VoteCategory.DEMODAY) {
            throw new GeneralException(ErrorStatus.NOT_FOUND_DEMODAY);
        }

        if (user.getTeam() == candidate.getTeam()) {
            throw new GeneralException(ErrorStatus.CANNOT_VOTE_OWN_TEAM);
        }

        voteService.existAlreadyVote(user, VoteCategory.DEMODAY);

        Vote vote = Vote.builder()
                .voteCategory(VoteCategory.DEMODAY)
                .user(user)
                .candidate(candidate)
                .build();

        voteService.save(vote);
    }

    @Transactional(readOnly = true)
    public VoteResultListResponseDto getDemodayVoteResult() {
        List<CandidateComponent> results = candidateService.getVoteResultByVoteCategory(VoteCategory.DEMODAY);
        int totalVotes = voteService.getTotalVotes(VoteCategory.DEMODAY);

        return VoteResultListResponseDto.from(results, totalVotes);
    }
}
