package gdsc.mju.guessme.domain.person.dto;

import gdsc.mju.guessme.domain.info.dto.InfoObj;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdatePersonDto {
    private String imageUrl;
    private String voiceUrl;
    private String name;
    private String relation;
    private LocalDate birth;
    private String residence;
    private List<InfoObj> info;

    public UpdatePersonDto(String imageUrl, String voiceUrl, String name, String relation, LocalDate birth, String residence, List<InfoObj> info) {
        this.imageUrl = imageUrl;
        this.voiceUrl = voiceUrl;
        this.name = name;
        this.relation = relation;
        this.birth = birth;
        this.residence = residence;
        this.info = info;
    }
}
