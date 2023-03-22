package gdsc.mju.guessme.domain.person.dto;

import gdsc.mju.guessme.domain.info.dto.InfoObj;
import gdsc.mju.guessme.domain.person.entity.Person;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreatePersonReqDto {


    private Optional<String> image;
    private Optional<String> voice;
    private String name;
    private String relation;
    private LocalDate birth;
    private String residence;

    @Builder
    public CreatePersonReqDto(Optional<String> image, Optional<String> voice, String name, String relation, LocalDate birth, String residence) {
        this.image = image;
        this.voice = voice;
        this.name = name;
        this.relation = relation;
        this.birth = birth;
        this.residence = residence;
    }
}