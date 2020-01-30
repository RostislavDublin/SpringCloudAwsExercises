package rdublin.aws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import rdublin.aws.s3.S3Download;
import rdublin.aws.s3.S3ResourceResolver;
import rdublin.aws.s3.S3Upload;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class AwsTestApplication {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = SpringApplication.run(AwsTestApplication.class, args);

        String s3bucket = "myteststack-mytestbucket-onrs6o86njhn";
        String s3Name = "myTestLog.log";

        S3Upload s3Upload = (S3Upload) context.getBean("s3Upload");

        System.out.println("--- Upload content to the bucket");
        String strContent = "test - " + new Date();
        try (InputStream content = new ByteArrayInputStream(strContent.getBytes())) {
            s3Upload.writeResource(s3bucket, s3Name, content);
        }

        System.out.println("--- Download resource content from the bucket");
        S3Download s3Download = (S3Download) context.getBean("s3Download");
        s3Download.readResource(s3bucket, s3Name);

        System.out.println("--- Find s3 resources by the location pattern");
        S3ResourceResolver resolver = context.getBean(S3ResourceResolver.class);
        for (Resource r : resolver.resolveAndLoad("s3://**/*.*")) {
            System.out.println(r.toString());
        }
    }
}
