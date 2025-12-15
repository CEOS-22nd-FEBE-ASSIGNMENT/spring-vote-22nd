package vote.vote_be.domain.vote.entity;

import jakarta.persistence.*;
import lombok.*;
import vote.vote_be.domain.user.entity.Part;
import vote.vote_be.domain.user.entity.Team;
import vote.vote_be.global.entity.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Candidate extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoteCategory voteCategory;

    private String name;

    @Enumerated(EnumType.STRING)
    private Team team;

}
