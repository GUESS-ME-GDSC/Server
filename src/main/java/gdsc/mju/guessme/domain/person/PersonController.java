package gdsc.mju.guessme.domain.person;

import gdsc.mju.guessme.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import gdsc.mju.guessme.domain.person.dto.PersonResDto;

@RequiredArgsConstructor
@RequestMapping("/person")
@Controller
public class PersonController {

    private final PersonService personService;

    @GetMapping("/all")
    public Response<List<PersonResDto>> getPersonList(
        @RequestParam Boolean favorite
    ) {
        return new Response<>(
            200,
            "Load Success",
            personService.getPersonList(favorite)
        );
    }
}
