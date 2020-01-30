package rdublin.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.core.io.s3.PathMatchingSimpleStorageResourcePatternResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * https://cloud.spring.io/spring-cloud-static/spring-cloud-aws/2.2.1.RELEASE/reference/html/#searching-resources
 */
@Component
public class S3ResourceResolver {

    private ResourcePatternResolver resourcePatternResolver;

    @Autowired
    public void setupResolver(ApplicationContext applicationContext, AmazonS3 amazonS3) {
        this.resourcePatternResolver =
                new PathMatchingSimpleStorageResourcePatternResolver(amazonS3, applicationContext);
    }

    public Resource[] resolveAndLoad(String locationPattern) throws IOException {
        return this.resourcePatternResolver.getResources(locationPattern);
        //"s3://bucket/name/*.txt"
        //"s3://bucket/**/*.txt"
        //"s3://**/*.txt"
    }

}
