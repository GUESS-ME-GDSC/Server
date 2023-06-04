package gdsc.mju.guessme.domain.info.dto;

import gdsc.mju.guessme.domain.info.entity.Info;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class InfoObj {
    private Long id;
    private String infoKey;
    private String infoValue;

    public InfoObj(Long id, String infoKey, String infoValue) {
        this.id = id;
        this.infoKey = infoKey;
        this.infoValue = infoValue;
    }

    public static List<InfoObj> of(List<Info> byPerson) {
        return byPerson.stream()
            .map(info -> new InfoObj(info.getId(), info.getInfoKey(), info.getInfoValue()))
            .collect(Collectors.toList());
    }
}