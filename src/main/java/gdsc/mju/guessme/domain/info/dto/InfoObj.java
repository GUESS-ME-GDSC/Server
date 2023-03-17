package gdsc.mju.guessme.domain.info.dto;

import lombok.Getter;

@Getter
public class InfoObj {

    private String infoKey;
    private String infoValue;

    public InfoObj(String infoKey, String infoValue) {
        this.infoKey = infoKey;
        this.infoValue = infoValue;
    }
}