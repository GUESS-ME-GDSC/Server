package gdsc.mju.guessme.domain.info;

import gdsc.mju.guessme.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gdsc.mju.guessme.domain.info.dto.DeleteInfoByIdListReqDto;

@RequiredArgsConstructor
@RequestMapping("/info")
@RestController
public class InfoController {
    private final InfoService infoService;

    @DeleteMapping
    public BaseResponse<Void> deleteInfoByIdList(
        @RequestBody DeleteInfoByIdListReqDto deleteInfoByIdListReqDto
    ) {
        infoService.deleteInfoByIdList(deleteInfoByIdListReqDto);
        return new BaseResponse<>(
            200,
            "Delete Info Successfully",
            null
        );
    }
}
