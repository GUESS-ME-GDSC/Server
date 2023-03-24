package gdsc.mju.guessme.global.infra.gcs;

import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import com.google.cloud.storage.*;

@RequiredArgsConstructor
@Service
public class GcsService {
    private final Storage storage;
    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public String uploadFile(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
        String contentType = file.getContentType(); // 파일의 형식 ex) JPG

        // Cloud에 이미지 업로드
        storage.create(
            BlobInfo.newBuilder(bucketName, uuid)
                .setContentType(contentType)
                .build(),
            file.getInputStream()
        );

        return "https://storage.cloud.google.com/" + bucketName + "/" + uuid;
    }

    // delete file
    public void deleteFile(String fileName) {
        storage.delete(bucketName, fileName);
    }

}
