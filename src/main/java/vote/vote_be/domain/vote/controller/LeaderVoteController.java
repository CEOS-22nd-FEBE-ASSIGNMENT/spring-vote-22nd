package vote.vote_be.domain.vote.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vote.vote_be.domain.vote.dto.request.VoteRequestDto;
import vote.vote_be.domain.vote.dto.response.CandidateListResponseDto;
import vote.vote_be.domain.vote.dto.response.VoteResultListResponseDto;
import vote.vote_be.domain.vote.service.LeaderVoteService;
import vote.vote_be.global.apiPayload.ApiResponse;
import vote.vote_be.global.security.AuthDetails;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "파트장 투표관련 API")
public class LeaderVoteController {

    private final LeaderVoteService leaderVoteService;

    @GetMapping("/votes")
    @Operation(summary = "파트장 후보자 명단 조회 API", description = "파트장 후보자 명단 조회 시에 사용하는 API입니다.")
    public ApiResponse<List<CandidateListResponseDto>> getCandidateList(@AuthenticationPrincipal AuthDetails authDetails) {
        List<CandidateListResponseDto> responseDtos = leaderVoteService.getCandidateList(authDetails.user());

        return ApiResponse.onSuccess(responseDtos);
    }

    @PostMapping("/votes/leader")
    @Operation(summary = "파트장 투표 API", description = "파트장 투표 시에 사용하는 API입니다.")
    public ApiResponse<String> votePartLeader(@AuthenticationPrincipal AuthDetails authDetails, @RequestBody VoteRequestDto requestDto) {

        leaderVoteService.votePartLeader(authDetails.user(), requestDto);

        return ApiResponse.onSuccess("파트장 투표가 완료되었습니다.");
    }

    @GetMapping("/votes/leader-result")
    @Operation(summary = "파트장 투표 결과 조회 API", description = "파트장 투표 결과 조회 시에 사용하는 API입니다.")
    public ApiResponse<VoteResultListResponseDto> getPartLeaderVoteResult(@AuthenticationPrincipal AuthDetails authDetails) {
        VoteResultListResponseDto result = leaderVoteService.getPartLeaderVoteResult(authDetails.user());

        return ApiResponse.onSuccess(result);
    }

}
