package gdsc.mju.guessme.domain.person.dto;

import gdsc.mju.guessme.domain.person.entity.Person;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PersonResDto {

    private String id;
    private String name;
    private String relation;
    private String image;
    private Long score;
    private Boolean favorite;

    @Builder
    public PersonResDto(String id, String name, String relation, String image, Long score, Boolean favorite) {
        this.id = id;
        this.name = name;
        this.relation = relation;
        this.image = image;
        this.score = score;
        this.favorite = favorite;
    }

    public static PersonResDto of(String id, String name, String relation, String image, Long score, Boolean favorite) {
        return PersonResDto.builder()
            .id(id)
            .name(name)
            .relation(relation)
            .image(image)
            .score(score)
            .favorite(favorite)
            .build();
    }

    public static List<PersonResDto> of(List<Person> personList) {
        Stream<PersonResDto> personListStream = personList.stream()
            .map(person -> PersonResDto.of(
                    person.getId().toString(),
                    person.getName(),
                    person.getRelation(),
                    person.getImage(),
                    person.getScore(),
                    person.getFavorite()
                )
            );
        return Arrays.asList(personListStream.toArray(PersonResDto[]::new));
    }
}