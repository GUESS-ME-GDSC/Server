package gdsc.mju.guessme.global.infra.openai;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class ChatgptServiceImpl implements ChatgptService {
    @Qualifier("openaiRestTemplate")
    @Autowired
    private final RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Override
    public String sendMessage(String message) {
        return null;
    }

    @Override
    public ChatResponse sendChatRequest(ChatRequest request) {
        return null;
    }

    @Override
    public String multiChat(List<MultiChatMessage> messages) {
        return null;
    }

    @Override
    public MultiChatResponse multiChatRequest(MultiChatRequest multiChatRequest) {
        return null;
    }

    @Override
    public String imageGenerate(String prompt) {
        return null;
    }

    @Override
    public List<String> imageGenerate(String prompt, Integer n, ImageSize size, ImageFormat format) {
        return null;
    }

    @Override
    public ImageResponse imageGenerateRequest(ImageRequest imageRequest) {
        return null;
    }
}
