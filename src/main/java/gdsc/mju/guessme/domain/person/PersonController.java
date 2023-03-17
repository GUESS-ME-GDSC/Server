package gdsc.mju.guessme.domain.person;

import gdsc.mju.guessme.domain.person.dto.CreatePersonReqDto;
import gdsc.mju.guessme.domain.person.dto.PersonDetailResDto;
import gdsc.mju.guessme.global.response.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    ) throws BaseException {
        return new BaseResponse<>(
            200,
            "Load Successfully",
            personService.getPersonList(favorite)
        );
    }

    @PostMapping
    public BaseResponse<Void> createPerson(
        @RequestBody CreatePersonReqDto createPersonReqDto
    ) throws BaseException {
        personService.createPerson(createPersonReqDto);
        return new BaseResponse<>(
            201,
            "Create Successfully",
            null
        );
    }

    @GetMapping("/{id}")
    public BaseResponse<PersonDetailResDto> getPerson(
        @PathVariable("id") Long personId
    ) throws BaseException {
        return new BaseResponse<>(
            200,
            "Load Successfully",
            personService.getPerson(personId)
        );
    }
}
