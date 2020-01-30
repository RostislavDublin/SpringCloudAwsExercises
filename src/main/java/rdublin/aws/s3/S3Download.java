package rdublin.aws.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class S3Download {
    @Autowired
    private ResourceLoader resourceLoader;

    public void readResource(String s3bucket, String s3file) throws IOException {

        StringBuilder s3resourcePath = new StringBuilder("s3://").append(s3bucket).append("/").append(s3file);
        Resource resource = this.resourceLoader.getResource(s3resourcePath.toString());

        try (InputStream inputStream = resource.getInputStream()) {

            byte[] buffer = new byte[1024];
            int len = inputStream.read(buffer);
            while (len != -1) {
                System.out.write(buffer, 0, len);
                len = inputStream.read(buffer);
            }
        }
    }
}
