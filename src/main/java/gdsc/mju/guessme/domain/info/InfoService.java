package gdsc.mju.guessme.domain.info;

import gdsc.mju.guessme.domain.person.entity.Person;
import gdsc.mju.guessme.domain.user.entity.User;
import gdsc.mju.guessme.domain.user.repository.UserRepository;
import gdsc.mju.guessme.global.response.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import gdsc.mju.guessme.domain.info.repository.InfoRepository;
import gdsc.mju.guessme.domain.info.dto.DeleteInfoByIdListReqDto;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InfoService {
    private final InfoRepository infoRepository;
    private final UserRepository userRepository;

    public void deleteInfoByIdList(
            UserDetails userDetails,
            DeleteInfoByIdListReqDto deleteInfoByIdListReqDto
    ) throws BaseException {
        // load user
        User user = userRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(
                        () -> new BaseException(404, "User not found")
                );

        // dto에서 infoIdList 추출
        List<Long> infoIds = deleteInfoByIdListReqDto.getIdList();

        // user의 personList에서 infoIdList에 해당하는 info가 있는 첫번째 person 추출
        Optional<Person> targetPerson = user.getPersonList().stream()
                .filter(person -> person.getInfoList().stream()
                        .anyMatch(info -> infoIds.contains(info.getId())))
                .findFirst();

        // personId 추출
        Long personId = targetPerson.orElseThrow(
                () -> new BaseException(404, "Person not found")
        ).getId();

        // personId와 infoIdList로 info 삭제
        infoRepository.deleteInfoByPersonIdAndInfoIds(personId, infoIds);
    }
}
