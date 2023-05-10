package gdsc.mju.guessme.domain.person.dto;

import gdsc.mju.guessme.domain.info.dto.InfoObj;
import java.util.List;
import lombok.Getter;

@Getter
public class AddInfoReqDto {
    private List<InfoObj> info;
}