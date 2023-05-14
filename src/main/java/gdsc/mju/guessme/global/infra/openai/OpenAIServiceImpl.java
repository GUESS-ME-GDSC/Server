package gdsc.mju.guessme.global.infra.openai;

import gdsc.mju.guessme.global.infra.openai.dto.ChatRequest;
import gdsc.mju.guessme.global.infra.openai.dto.ChatResponse;
import gdsc.mju.guessme.global.infra.openai.dto.Message;
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
    public ChatResponse createCompletion(String prompt) {
        // create a request
        List<Message> messages = List.of(
                new Message(prompt)
        );
        ChatRequest request = new ChatRequest(model, messages, 0.9);

        // call the API
        return restTemplate.postForObject(apiUrl, request, ChatResponse.class);
    }
}
