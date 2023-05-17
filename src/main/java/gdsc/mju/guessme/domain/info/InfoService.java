package gdsc.mju.guessme.domain.info;

import gdsc.mju.guessme.domain.info.entity.Info;
import gdsc.mju.guessme.domain.person.entity.Person;
import gdsc.mju.guessme.domain.person.repository.PersonRepository;
import gdsc.mju.guessme.domain.user.entity.User;
import gdsc.mju.guessme.domain.user.repository.UserRepository;
import gdsc.mju.guessme.global.response.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import gdsc.mju.guessme.domain.info.repository.InfoRepository;
import gdsc.mju.guessme.domain.info.dto.DeleteInfoByIdListReqDto;

import java.util.List;

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

        Person targetPerson = infoRepository.findAllByIdIn(infoIds)
                .filter(infos -> infos.size() == infoIds.size()) // infoIds에 해당하는 모든 Info가 존재하는지 확인
                .flatMap(infos -> infos.stream().findFirst()) // 첫 번째 Info
                .map(Info::getPerson) // Person 추출
                .orElseThrow(() -> new BaseException(404, "Info not found"));

        // check if targetPerson is allowed to be controlled by user
        if (!user.getPersonList().contains(targetPerson)) {
            throw new BaseException(403, "You are not allowed to control this person");
        }

        // personId와 infoIdList로 info 삭제
        infoRepository.deleteInfoByPersonIdAndInfoIds(targetPerson.getId(), infoIds);
    }
}
