package gdsc.mju.guessme.domain.person;

import gdsc.mju.guessme.domain.info.entity.Info;
import gdsc.mju.guessme.domain.info.repository.InfoRepository;
import gdsc.mju.guessme.domain.person.dto.CreatePersonReqDto;
import gdsc.mju.guessme.domain.info.dto.InfoObj;
import gdsc.mju.guessme.domain.person.dto.PersonDetailResDto;
import gdsc.mju.guessme.domain.person.dto.PersonResDto;
import gdsc.mju.guessme.domain.person.dto.UpdatePersonReqDto;
import gdsc.mju.guessme.domain.person.repository.PersonRepository;
import gdsc.mju.guessme.domain.person.entity.Person;
import gdsc.mju.guessme.domain.user.entity.User;
import gdsc.mju.guessme.domain.user.repository.UserRepository;
import gdsc.mju.guessme.global.response.BaseException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final InfoRepository infoRepository;
    private final UserRepository userRepository;

    public List<PersonResDto> getPersonList(
        Boolean favorite
    ) {
        if (favorite) {
            return PersonResDto.of(personRepository.findByFavoriteTrue());
        } else {
            return PersonResDto.of(personRepository.findByFavoriteFalse());
        }
    }

    public void createPerson(CreatePersonReqDto createPersonReqDto) throws BaseException {
        // TODO: use test user now but need to use user from token
         User user = userRepository.findByUserId("test");
         if(user == null) {
             throw new BaseException(404, "User not found");
         }

        // TODO: image upload to gcs

        // TODO: voice upload to gcs

        // Person 저장
        Person person = personRepository.save(Person.builder()
            .image(createPersonReqDto.getImage())
            .voice(createPersonReqDto.getVoice())
            .name(createPersonReqDto.getName())
            .relation(createPersonReqDto.getRelation())
            .birth(createPersonReqDto.getBirth())
            .residence(createPersonReqDto.getResidence())
            .user(user)
            .build());

        // Info 저장
        List<InfoObj> infoObjList = createPersonReqDto.getInfo();
        for (InfoObj infoObj : infoObjList) {
            infoRepository.save(Info.builder()
                .infoKey(infoObj.getInfoKey())
                .infoValue(infoObj.getInfoValue())
                .person(person)
                .build());
        }
    }

    public PersonDetailResDto getPerson(Long personId) throws BaseException {
        Person person = personRepository.findById(personId)
            .orElseThrow(
                () -> new BaseException(404, "Person not found with that Id")
            );

        List<InfoObj> infoObjList = InfoObj.of(infoRepository.findAllByPerson(person));
        return PersonDetailResDto.of(person, infoObjList);
    }

    public void updatePerson(Long personId, UpdatePersonReqDto updatePersonReqDto)
        throws BaseException {
        Person person = personRepository.findById(personId)
            .orElseThrow(
                () -> new BaseException(404, "Person not found with that Id")
            );

        List<Info> infoList = infoRepository.findAllByPerson(person);
        List<InfoObj> updatedInfoObjList = updatePersonReqDto.getInfo();

        // update person entity with new data
        person.update(updatePersonReqDto);
        personRepository.save(person);

        /**
         * update info entity with new data
         */

        // infoList에서 infoKey가 같은 Info를 찾아서 infoValue를 업데이트
        for (Info info : infoList) {
            for (InfoObj infoObj : updatedInfoObjList) {
                System.out.println("infoObj = " + infoObj.getInfoKey());
                if (info.getInfoKey().equals(infoObj.getInfoKey())) {
                    info.setInfoValue(infoObj.getInfoValue());
                    infoRepository.save(info);

                    // updatedInfoObjList에서 infoKey가 같은 InfoObj를 삭제
                    updatedInfoObjList.remove(infoObj);
                    break;
                }
            }
        }

        // updatedInfoObjList에 남은 InfoObj는 새로운 Info를 생성
        for (InfoObj infoObj : updatedInfoObjList) {
            infoRepository.save(Info.builder()
                .infoKey(infoObj.getInfoKey())
                .infoValue(infoObj.getInfoValue())
                .person(person)
                .build());
        }
    }
}
