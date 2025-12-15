package vote.vote_be.domain.vote.entity;

import jakarta.persistence.*;
import lombok.*;
import vote.vote_be.domain.user.entity.User;
import vote.vote_be.global.entity.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "vote",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"user_id", "vote_category"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_category", nullable = false)
    private VoteCategory voteCategory;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
