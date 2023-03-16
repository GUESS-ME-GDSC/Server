package gdsc.mju.guessme.domain.person;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import gdsc.mju.guessme.domain.person.dto.PersonResDto;
import gdsc.mju.guessme.global.response.BaseResponse;

@RequiredArgsConstructor
@RequestMapping("/person")
@RestController
public class PersonController {

    private final PersonService personService;

    @GetMapping("/all")
    public BaseResponse<List<PersonResDto>> getPersonList(
        @RequestParam Boolean favorite
    ) {
        return new BaseResponse<>(
            200,
            "Load Success",
            personService.getPersonList(favorite)
        );
    }
}
