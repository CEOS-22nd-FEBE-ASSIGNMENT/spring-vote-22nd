package vote.vote_be.domain.vote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vote.vote_be.domain.vote.dto.CandidateComponent;
import vote.vote_be.domain.vote.dto.response.CandidateListResponseDto;
import vote.vote_be.domain.vote.entity.Candidate;
import vote.vote_be.domain.vote.entity.VoteCategory;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Query("select c.name, count(v.id) " +
            "from Candidate c " +
            "left join Vote v on v.candidate = c " +
            "where c.voteCategory = :voteCategory " +
            "group by c.id, c.name " +
            "order by count (v.id) desc, c.id desc " +
            "limit 3 ")
    List<CandidateComponent> findVoteResultsByVoteCategory(VoteCategory voteCategory);

    @Query("select c.name, c.id " +
            "from Candidate c " +
            "where c.voteCategory = :voteCategory ")
    List<CandidateListResponseDto> findAllByVoteCategory(VoteCategory voteCategory);
}
