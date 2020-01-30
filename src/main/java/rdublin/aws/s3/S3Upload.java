package rdublin.aws.s3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
public class S3Upload {
    // buffer size used for reading and writing
    private static final int BUFFER_SIZE = 8192;
    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * Reads all bytes from an input stream and writes them to an output stream.
     */
    private static long copy(InputStream source, OutputStream sink) throws IOException {
        long nread = 0L;
        byte[] buf = new byte[BUFFER_SIZE];
        int n;
        while ((n = source.read(buf)) > 0) {
            sink.write(buf, 0, n);
            nread += n;
        }
        return nread;
    }

    public void writeResource(String s3bucket, String s3file, InputStream content) throws IOException {

        StringBuilder s3resourcePath = new StringBuilder("s3://").append(s3bucket).append("/").append(s3file);
        Resource s3resource = this.resourceLoader.getResource(s3resourcePath.toString());

        WritableResource writableResource = (WritableResource) s3resource;
        try (OutputStream outputStream = writableResource.getOutputStream()) {
            copy(content, outputStream);
        }
    }
}
