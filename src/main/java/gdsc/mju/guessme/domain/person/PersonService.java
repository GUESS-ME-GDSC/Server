package gdsc.mju.guessme.domain.person;

import gdsc.mju.guessme.domain.person.dto.PersonResDto;
import gdsc.mju.guessme.domain.person.repository.PersonRepository;
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

}
