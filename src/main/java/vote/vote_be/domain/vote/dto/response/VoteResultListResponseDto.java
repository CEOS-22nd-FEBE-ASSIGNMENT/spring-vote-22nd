package vote.vote_be.domain.vote.dto.response;

import vote.vote_be.domain.vote.dto.CandidateComponent;

import java.util.List;

public record VoteResultListResponseDto(
        List<CandidateComponent> candidateList,
        int totalVoteCount
) {

    public static VoteResultListResponseDto from(List<CandidateComponent> candidateList, int totalVoteCount) {
        return new VoteResultListResponseDto(candidateList, totalVoteCount);
    }
}
