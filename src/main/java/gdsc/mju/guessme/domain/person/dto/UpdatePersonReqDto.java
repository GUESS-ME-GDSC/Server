package gdsc.mju.guessme.domain.person.dto;

import gdsc.mju.guessme.domain.info.dto.InfoObj;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@ToString
public class UpdatePersonReqDto {

    private MultipartFile image;
    private MultipartFile voice;
    private String name;
    private String relation;
    private LocalDate birth;
    private String residence;
    private List<InfoObj> info;

    @Builder
    public UpdatePersonReqDto(MultipartFile image, MultipartFile voice, String name, String relation, LocalDate birth, String residence, List<InfoObj> info) {
        this.image = image;
        this.voice = voice;
        this.name = name;
        this.relation = relation;
        this.birth = birth;
        this.residence = residence;
        this.info = info;
    }
}