package gdsc.mju.guessme.domain.info.dto;

import gdsc.mju.guessme.domain.info.entity.Info;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class InfoObj {

    private String infoKey;
    private String infoValue;

    public InfoObj(String infoKey, String infoValue) {
        this.infoKey = infoKey;
        this.infoValue = infoValue;
    }

    public static List<InfoObj> of(List<Info> byPerson) {
        return byPerson.stream()
            .map(info -> new InfoObj(info.getInfoKey(), info.getInfoValue()))
            .collect(Collectors.toList());
    }
}