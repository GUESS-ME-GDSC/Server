package gdsc.mju.guessme.domain.info;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import gdsc.mju.guessme.domain.info.repository.InfoRepository;
import gdsc.mju.guessme.domain.info.dto.DeleteInfoByIdListReqDto;

@RequiredArgsConstructor
@Service
public class InfoService {
    private final InfoRepository infoRepository;

    public void deleteInfoByIdList(DeleteInfoByIdListReqDto deleteInfoByIdListReqDto) {
        infoRepository.deleteByIdIn(
            deleteInfoByIdListReqDto.getIdList()
        );
    }
}
