package gdsc.mju.guessme.domain.person.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Getter
@ToString
public class CreatePersonReqDto {

    private MultipartFile image;
    private MultipartFile voice;
    private String name;
    private String relation;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private String residence;

    @Builder
    public CreatePersonReqDto(MultipartFile image, MultipartFile voice, String name, String relation, LocalDate birth, String residence) {
        this.image = image;
        this.voice = voice;
        this.name = name;
        this.relation = relation;
        this.birth = birth;
        this.residence = residence;
    }
}