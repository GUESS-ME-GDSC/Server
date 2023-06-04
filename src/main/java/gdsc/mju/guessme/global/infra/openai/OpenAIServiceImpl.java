package gdsc.mju.guessme.global.infra.openai;

import gdsc.mju.guessme.global.infra.openai.dto.ChatRequest;
import gdsc.mju.guessme.global.infra.openai.dto.ChatResponse;
import gdsc.mju.guessme.global.infra.openai.dto.Message;
import gdsc.mju.guessme.global.response.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OpenAIServiceImpl implements OpenAIService {
    @Qualifier("openaiRestTemplate")
    @Autowired
    private final RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Override
    public String createCompletion(String prompt) throws BaseException {
        // create a request
        List<Message> messages = List.of(
                new Message(prompt)
        );
        ChatRequest request = new ChatRequest(model, messages, 0.9);

        // call the API
        ChatResponse chatResponse = restTemplate.postForObject(apiUrl, request, ChatResponse.class);
        if(chatResponse == null) {
            throw new BaseException(500, "OpenAI API call failed");
        }

        // return the response
        return chatResponse.getChoices().get(0).getMessage().getContent();
    }
}
