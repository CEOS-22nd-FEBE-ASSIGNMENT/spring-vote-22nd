package vote.vote_be.domain.vote.dto;

public record CandidateComponent(
        String candidateName,
        long voteCount
) {
}
