package gdsc.mju.guessme.global.infra.openai;

import gdsc.mju.guessme.global.infra.openai.dto.ChatResponse;

public interface OpenAIService {
    ChatResponse createCompletion(String prompt);
}
