package gdsc.mju.guessme.domain.person.dto;

import gdsc.mju.guessme.domain.info.dto.InfoObj;
import gdsc.mju.guessme.domain.person.entity.Person;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PersonDetailResDto {
    private Long id;
    private String name;
    private String relation;
    private LocalDate birth;
    private String residence;
    private String image;
    private String voice;
    private List<InfoObj> info;
    private Boolean favorite;

    @Builder
    public PersonDetailResDto(Long id, String name, String relation, LocalDate birth, String residence,
        String image, String voice, List<InfoObj> info, Boolean favorite) {
        this.id = id;
        this.name = name;
        this.relation = relation;
        this.birth = birth;
        this.residence = residence;
        this.image = image;
        this.voice = voice;
        this.info = info;
        this.favorite = favorite;
    }

    public static PersonDetailResDto of(Person person, List<InfoObj> info) {
        return PersonDetailResDto.builder()
            .id(person.getId())
            .name(person.getName())
            .relation(person.getRelation())
            .birth(person.getBirth())
            .residence(person.getResidence())
            .image(person.getImage())
            .voice(person.getVoice())
            .info(info)
            .favorite(person.getFavorite())
            .build();
    }
}