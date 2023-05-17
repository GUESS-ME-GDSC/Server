package gdsc.mju.guessme.domain.info.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteInfoByIdListReqDto {
    private List<Long> idList;

    public DeleteInfoByIdListReqDto(List<Long> idList) {
        this.idList = idList;
    }

}
