package gdsc.mju.guessme.domain.person;

import gdsc.mju.guessme.domain.person.dto.CreatePersonReqDto;
import gdsc.mju.guessme.domain.person.dto.PersonDetailResDto;
import gdsc.mju.guessme.domain.person.dto.UpdatePersonReqDto;
import gdsc.mju.guessme.domain.person.dto.AddInfoReqDto;
import gdsc.mju.guessme.global.response.BaseException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestParam Boolean favorite
    ) throws BaseException {
        return new BaseResponse<>(
            200,
            "Load Successfully",
            personService.getPersonList(userDetails, favorite)
        );
    }

    @PostMapping
    public BaseResponse<Void> createPerson(
        @AuthenticationPrincipal UserDetails userDetails,
        @ModelAttribute CreatePersonReqDto createPersonReqDto
    ) throws BaseException, IOException {
        System.out.println(createPersonReqDto.toString());
        personService.createPerson(userDetails, createPersonReqDto);
        return new BaseResponse<>(
            201,
            "Create Successfully",
            null
        );
    }

    @GetMapping("/{id}")
    public BaseResponse<PersonDetailResDto> getPersonDetail(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable("id") Long personId
    ) throws BaseException {
        return new BaseResponse<>(
            200,
            "Load Successfully",
            personService.getPersonDetail(userDetails, personId)
        );
    }

    @PatchMapping("/{id}")
    public BaseResponse<Void> updatePerson(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable("id") Long personId,
        @ModelAttribute UpdatePersonReqDto updatePersonReqDto
    ) throws BaseException, IOException {
        personService.updatePerson(userDetails, personId, updatePersonReqDto);
        return new BaseResponse<>(
            201,
            "Update Successfully",
            null
        );
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deletePerson(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable("id") Long personId
    ) throws BaseException {
        personService.deletePerson(userDetails, personId);
        return new BaseResponse<>(
            200,
            "Delete Successfully",
            null
        );
    }

    @PatchMapping("/{id}/favorite")
    public BaseResponse<Void> toggleFavorite(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable("id") Long personId
    ) throws BaseException {
        personService.toggleFavorite(personId);
        return new BaseResponse<>(
            201,
            "Update Successfully",
            null
        );
    }

    @PostMapping("/{id}/newinfo")
    public BaseResponse<Void> addNewInfo(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable("id") Long personId,
        @RequestBody AddInfoReqDto addInfoReqDto
    ) throws BaseException {
        personService.addNewInfo(personId, addInfoReqDto);
        return new BaseResponse<>(
            201,
            "Create Successfully",
            null
        );
    }
}
