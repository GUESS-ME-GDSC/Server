package gdsc.mju.guessme.domain.person;

import gdsc.mju.guessme.domain.person.dto.CreatePersonReqDto;
import gdsc.mju.guessme.domain.person.dto.PersonResDto;
import gdsc.mju.guessme.domain.person.repository.PersonRepository;
import gdsc.mju.guessme.domain.person.entity.Person;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;

    public List<PersonResDto> getPersonList(
        Boolean favorite
    ) {
        if (favorite) {
            return PersonResDto.of(personRepository.findByFavoriteTrue());
        } else {
            return PersonResDto.of(personRepository.findByFavoriteFalse());
        }
    }

    public void createPerson(CreatePersonReqDto createPersonReqDto) {
        // TODO: image upload to gcs

        // TODO: voice upload to gcs

        // TODO: info save to db


        personRepository.save(Person.builder()
            .image(createPersonReqDto.getImage())
            .voice(createPersonReqDto.getVoice())
            .name(createPersonReqDto.getName())
            .relation(createPersonReqDto.getRelation())
            .birth(createPersonReqDto.getBirth())
            .residence(createPersonReqDto.getResidence())
            .build());
    }
}
