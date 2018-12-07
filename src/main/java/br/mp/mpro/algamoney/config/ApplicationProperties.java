package br.mp.mpro.algamoney.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Algamoney.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

//    private final S3 s3 = new S3();
//
//    public S3 getS3() {
//        return s3;
//    }
//
//    public static class S3 {
//        private String accessKeyId;
//        private String secretAccessKey;
//        //private String bucket = "jnb-algamoney-arquivos";
//        private String bucket = "jonasnink";
//
//        public String getAccessKeyId() {
//            return accessKeyId;
//        }
//
//        public void setAccessKeyId(String accessKeyId) {
//            this.accessKeyId = accessKeyId;
//        }
//
//        public String getSecretAccessKey() {
//            return secretAccessKey;
//        }
//
//        public void setSecretAccessKey(String secretAccessKey) {
//            this.secretAccessKey = secretAccessKey;
//        }
//
//        public String getBucket() {
//            return bucket;
//        }
//
//        public void setBucket(String bucket) {
//            this.bucket = bucket;
//        }
//    }

}
