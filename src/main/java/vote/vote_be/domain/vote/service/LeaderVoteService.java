package vote.vote_be.domain.vote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vote.vote_be.domain.user.entity.Part;
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
public class LeaderVoteService {

    private final CandidateService candidateService;
    private final VoteService voteService;

    @Transactional
    public void votePartLeader(User user, VoteRequestDto requestDto) {
        Candidate candidate = candidateService.getById(requestDto.candidateId());

        //파트 확인
        if ( !((user.getPart() == Part.FRONTEND && candidate.getVoteCategory() == VoteCategory.FRONTEND_PART_LEADER) ||
                (user.getPart() == Part.BACKEND && candidate.getVoteCategory() == VoteCategory.BACKEND_PART_LEADER))){
            throw new GeneralException(ErrorStatus.MISMATCH_PART);
        }

        //중복투표인지 확인
        voteService.existAlreadyVote(user, candidate.getVoteCategory());

        Vote vote = Vote.builder()
                .voteCategory(candidate.getVoteCategory())
                .user(user)
                .candidate(candidate)
                .build();

        voteService.save(vote);
    }

    @Transactional(readOnly = true)
    public VoteResultListResponseDto getPartLeaderVoteResult(User user) {
        List<CandidateComponent> candidateComponentList;
        int totalVotes;

        if (user.getPart() == Part.FRONTEND) {
            candidateComponentList  = candidateService.getVoteResultByVoteCategory(VoteCategory.FRONTEND_PART_LEADER);
            totalVotes =  voteService.getTotalVotes(VoteCategory.FRONTEND_PART_LEADER);
        } else {
            candidateComponentList = candidateService.getVoteResultByVoteCategory(VoteCategory.BACKEND_PART_LEADER);
            totalVotes =  voteService.getTotalVotes(VoteCategory.BACKEND_PART_LEADER);
        }

        return VoteResultListResponseDto.from(candidateComponentList, totalVotes);
    }

    @Transactional(readOnly = true)
    public List<CandidateListResponseDto> getCandidateList(User user) {
        List<CandidateListResponseDto> candidateListResponseDtos;
        if (user.getPart() == Part.FRONTEND) {
            candidateListResponseDtos = candidateService.getAllByVoteCategory(VoteCategory.FRONTEND_PART_LEADER);
        } else {
            candidateListResponseDtos = candidateService.getAllByVoteCategory(VoteCategory.BACKEND_PART_LEADER);
        }

        return candidateListResponseDtos;
    }
}
