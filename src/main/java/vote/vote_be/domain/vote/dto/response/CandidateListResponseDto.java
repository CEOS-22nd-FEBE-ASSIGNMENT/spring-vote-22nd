package vote.vote_be.domain.vote.dto.response;

public record CandidateListResponseDto(
        String candidateName,
        Long candidateId
) {
}
