package vote.vote_be.domain.vote.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vote.vote_be.domain.vote.dto.request.VoteRequestDto;
import vote.vote_be.domain.vote.dto.response.CandidateListResponseDto;
import vote.vote_be.domain.vote.dto.response.VoteResultListResponseDto;
import vote.vote_be.domain.vote.service.DemodayVoteService;
import vote.vote_be.global.apiPayload.ApiResponse;
import vote.vote_be.global.security.AuthDetails;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "데모데이 투표관련 API")
public class DemodayVoteController {

    private final DemodayVoteService demodayVoteService;

    @GetMapping("/votes/demoday")
    @Operation(summary = "데모데이 후보(팀) 목록 조회", description = "데모데이 팀 조회 시에 사용하는 API입니다.")
    public ApiResponse<List<CandidateListResponseDto>> getDemodayCandidates(
            @AuthenticationPrincipal AuthDetails authDetails
    ) {
        authDetails.user(); //jwt 인증 용도
        return ApiResponse.onSuccess(demodayVoteService.getDemodayCandidateList());
    }

    @PostMapping("/votes/demoday")
    @Operation(summary = "데모데이 투표", description = "데모데이 투표 시에 사용하는 API입니다.")
    public ApiResponse<String> voteDemoday(
            @AuthenticationPrincipal AuthDetails authDetails,
            @RequestBody VoteRequestDto requestDto
    ) {
        demodayVoteService.voteDemoday(authDetails.user(), requestDto);
        return ApiResponse.onSuccess("데모데이 투표가 완료되었습니다.");
    }

    @GetMapping("/votes/demoday-result")
    @Operation(summary = "데모데이 투표 결과 조회", description = "데모데이 투표 결과 조회 시에 사용하는 API입니다.")
    public ApiResponse<VoteResultListResponseDto> getDemodayResult(
            @AuthenticationPrincipal AuthDetails authDetails
    ) {
        authDetails.user(); //jwt 인증 용도
        return ApiResponse.onSuccess(demodayVoteService.getDemodayVoteResult());
    }
}
