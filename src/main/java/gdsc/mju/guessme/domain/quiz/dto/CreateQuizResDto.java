package gdsc.mju.guessme.domain.quiz.dto;

import gdsc.mju.guessme.domain.info.dto.InfoObj;
import gdsc.mju.guessme.domain.info.entity.Info;
import gdsc.mju.guessme.domain.person.entity.Person;
import lombok.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
//@ToString
@NoArgsConstructor
public class CreateQuizResDto {
    private Long personId;
    private Long score;
    private String image;
    private String voice;
    private String name;
    private String relation;
    private LocalDate birth;
    private String residence;
    private List<InfoObj> info;

    @Builder
    public CreateQuizResDto(Person person, List<InfoObj> info) {
        this.personId = person.getId();
        this.score = person.getScore();
        this.image = person.getImage();
        this.voice = person.getVoice();
        this.name = person.getName();
        this.relation = person.getRelation();
        this.birth = person.getBirth();
        this.residence = person.getResidence();
        this.info = info;
    }
}
