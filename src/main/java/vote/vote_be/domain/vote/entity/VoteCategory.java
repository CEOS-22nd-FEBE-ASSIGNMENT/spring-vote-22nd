package vote.vote_be.domain.vote.entity;

public enum VoteCategory {
    FRONTEND_PART_LEADER("프론트엔드 파트장 투표"),
    BACKEND_PART_LEADER("백엔드 파트장 투표"),
    DEMODAY("데모데이 투표");

    public final String description;

    VoteCategory(String description) {this.description = description;}
}
