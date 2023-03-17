package gdsc.mju.guessme.domain.person.dto;

import gdsc.mju.guessme.domain.info.dto.InfoObj;
import gdsc.mju.guessme.domain.person.entity.Person;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreatePersonReqDto {

    private String image;
    private String voice;
    private String name;
    private String relation;
    private LocalDate birth;
    private String residence;
    private List<InfoObj> info;

    @Builder
    public CreatePersonReqDto(String image, String voice, String name, String relation, LocalDate birth, String residence, List<InfoObj> info) {
        this.image = image;
        this.voice = voice;
        this.name = name;
        this.relation = relation;
        this.birth = birth;
        this.residence = residence;
        this.info = info;
    }
}