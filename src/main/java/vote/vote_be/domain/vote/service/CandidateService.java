package vote.vote_be.domain.vote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vote.vote_be.domain.vote.dto.CandidateComponent;
import vote.vote_be.domain.vote.dto.response.CandidateListResponseDto;
import vote.vote_be.domain.vote.entity.Candidate;
import vote.vote_be.domain.vote.entity.VoteCategory;
import vote.vote_be.domain.vote.repository.CandidateRepository;
import vote.vote_be.global.apiPayload.code.status.ErrorStatus;
import vote.vote_be.global.apiPayload.exception.GeneralException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public Candidate getById(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(()-> new GeneralException(ErrorStatus.NOT_FOUND_CANDIDATE));
    }

    public List<CandidateListResponseDto> getAllByVoteCategory(VoteCategory voteCategory) {
        return candidateRepository.findAllByVoteCategory(voteCategory);
    }

    public List<CandidateComponent> getVoteResultByVoteCategory(VoteCategory voteCategory) {
        return candidateRepository.findVoteResultsByVoteCategory(voteCategory);
    }
}
