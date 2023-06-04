package gdsc.mju.guessme.global.infra.openai;

import gdsc.mju.guessme.global.infra.openai.dto.ChatResponse;
import gdsc.mju.guessme.global.response.BaseException;

public interface OpenAIService {
    String createCompletion(String prompt) throws BaseException;
}
