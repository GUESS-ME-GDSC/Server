package gdsc.mju.guessme.domain.person;

import gdsc.mju.guessme.domain.info.entity.Info;
import gdsc.mju.guessme.domain.info.repository.InfoRepository;
import gdsc.mju.guessme.domain.person.dto.AddInfoReqDto;
import gdsc.mju.guessme.domain.person.dto.CreatePersonReqDto;
import gdsc.mju.guessme.domain.info.dto.InfoObj;
import gdsc.mju.guessme.domain.person.dto.PersonDetailResDto;
import gdsc.mju.guessme.domain.person.dto.PersonResDto;
import gdsc.mju.guessme.domain.person.dto.UpdatePersonDto;
import gdsc.mju.guessme.domain.person.dto.UpdatePersonReqDto;
import gdsc.mju.guessme.domain.person.repository.PersonRepository;
import gdsc.mju.guessme.domain.person.entity.Person;
import gdsc.mju.guessme.domain.user.entity.User;
import gdsc.mju.guessme.domain.user.repository.UserRepository;
import gdsc.mju.guessme.global.infra.gcs.GcsService;
import gdsc.mju.guessme.global.response.BaseException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final InfoRepository infoRepository;
    private final UserRepository userRepository;
    private final GcsService gcsService;

    public List<PersonResDto> getPersonList(
        Boolean favorite
    ) {
        if (favorite) {
            return PersonResDto.of(personRepository.findByFavoriteTrue());
        } else {
            return PersonResDto.of(personRepository.findByFavoriteFalse());
        }
    }

    public void createPerson(CreatePersonReqDto createPersonReqDto)
        throws BaseException, IOException {
        // TODO: use test user now but need to use user from token
        User user = userRepository.findByUserId("test");
        if (user == null) {
            throw new BaseException(404, "User not found");
        }

        // Upload image to gcs
        String imageUrl = createPersonReqDto.getImage() != null ?
            gcsService.uploadFile(createPersonReqDto.getImage()) : null;

        // Upload voice to gcs
        String voiceUrl = createPersonReqDto.getVoice() != null ?
            gcsService.uploadFile(createPersonReqDto.getVoice()) : null;

        // Person 저장
        personRepository.save(Person.builder()
            .image(imageUrl)
            .voice(voiceUrl)
            .name(createPersonReqDto.getName())
            .relation(createPersonReqDto.getRelation())
            .birth(createPersonReqDto.getBirth())
            .residence(createPersonReqDto.getResidence())
            .user(user)
            .build());
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
        throws BaseException, IOException {
        Person person = personRepository.findById(personId)
            .orElseThrow(
                () -> new BaseException(404, "Person not found with that Id")
            );

        /**
         * update person entity with new data
         */
        // Delete old image and voice
        if (updatePersonReqDto.getImage() != null && person.getImage() != null) {
            String fileUUID = person.getImage().split("/")[4];
            gcsService.deleteFile(fileUUID);
        }
        if (updatePersonReqDto.getVoice() != null && person.getVoice() != null) {
            String fileUUID = person.getVoice().split("/")[4];
            gcsService.deleteFile(fileUUID);
        }

        // Upload image to gcs
        MultipartFile image = updatePersonReqDto.getImage();
        String imageUrl = image != null ?
            gcsService.uploadFile(image) : null;

        // Upload voice to gcs
        MultipartFile voice = updatePersonReqDto.getVoice();
        String voiceUrl = voice != null ?
            gcsService.uploadFile(voice) : null;

        UpdatePersonDto updatePersonDto = new UpdatePersonDto(
            imageUrl,
            voiceUrl,
            updatePersonReqDto.getName(),
            updatePersonReqDto.getRelation(),
            updatePersonReqDto.getBirth(),
            updatePersonReqDto.getResidence(),
            updatePersonReqDto.getInfo()
        );
        person.update(updatePersonDto);
        personRepository.save(person);

        /**
         * update info entity with new data
         */
        List<InfoObj> updatedInfoObjList = updatePersonReqDto.getInfo();
        if(updatedInfoObjList != null) {
            List<Info> infoList = infoRepository.findAllByPerson(person);
            // infoList에서 infoKey가 같은 Info를 찾아서 infoValue를 업데이트
            for (Info info : infoList) {
                for (InfoObj infoObj : updatedInfoObjList) {
                    if (info.getInfoKey().equals(infoObj.getInfoKey())) {
                        info.setInfoValue(infoObj.getInfoValue());
                        infoRepository.save(info);

                        // updatedInfoObjList에서 infoKey가 같은 InfoObj를 삭제
                        updatedInfoObjList.remove(infoObj);
                        break;
                    }
                }
            }

            // 만약 updatedInfoObjList에 남은 InfoObj가 있다면
            // 기존에 존재하지 않는 Info를 업데이트하려고 한 것이므로 에러
            if (updatedInfoObjList.size() > 0) {
                throw new BaseException(400, "Invalid info key");
            }
        }
    }

    public void deletePerson(Long personId) {
        infoRepository.deleteAllInBatchByPersonId(personId);
        personRepository.deleteById(personId);
    }

    public void toggleFavorite(Long personId) {
        personRepository.toggleFavorite(personId);
    }

    public void addNewInfo(Long personId, AddInfoReqDto addInfoReqDto) throws BaseException {
        Person person = personRepository.findById(personId)
            .orElseThrow(
                () -> new BaseException(404, "Person not found with that Id")
            );

        List<InfoObj> newInfoObjList = addInfoReqDto.getInfo();
        for (InfoObj infoObj : newInfoObjList) {
            infoRepository.save(Info.builder()
                .infoKey(infoObj.getInfoKey())
                .infoValue(infoObj.getInfoValue())
                .person(person)
                .build());
        }
    }
}
