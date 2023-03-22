package gdsc.mju.guessme.global.infra.gcs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

@Configuration
public class GCSConfig {
    @Bean
    public Storage storage() throws IOException {
        InputStream keyFile = ResourceUtils.getURL("classpath:involuted-span-377818-a3d393cddc53.json").openStream();
//        Storage storage = StorageOptions.newBuilder().setProjectId("involuted-span-377818")
//            // Key 파일 수동 등록
//            .setCredentials(GoogleCredentials.fromStream(keyFile))
//            .build().getService();
//        ClassPathResource resource = new ClassPathResource("위에서 만든 key 파일 이름.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(keyFile);
        String projectId = "involuted-span-377818";
        return StorageOptions.newBuilder()
            .setProjectId(projectId)
            .setCredentials(credentials)
            .build()
            .getService();
    }

}
