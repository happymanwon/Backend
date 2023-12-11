package org.hmanwon.infra.image.application;

import static org.hmanwon.infra.image.exception.ImageExceptionCode.IMAGE_TRANSFER;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.CORSRule;
import com.amazonaws.services.s3.model.CORSRule.AllowedMethods;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hmanwon.infra.image.exception.ImageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageUploader {

    private String accessKey;
    private String secretKey;
    private String region;
    private String endPoint;
    private String bucket;
    AmazonS3 s3;

    public ImageUploader(@Value("${cloud.naver.credentials.access-key}") String accessKey,
        @Value("${cloud.naver.credentials.secret-key}") String secretKey,
        @Value("${cloud.naver.region.static}") String region,
        @Value("${cloud.naver.s3.endpoint}") String endPoint,
        @Value("${cloud.naver.s3.bucket}") String bucket) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
        this.endPoint = endPoint;
        this.bucket = bucket;

        s3 = AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, region))
            .withCredentials(
                new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
            .build();
    }

    public List<String> uploadFile(String prefix, final List<MultipartFile> multipartFileList) {
        List<String> imageUrlList = new ArrayList<>();

        for (MultipartFile file : multipartFileList) {
            String fileName = prefix + "/" + ImageName.createFileName(file.getOriginalFilename());
            InitiateMultipartUploadResult initiateMultipartUploadResult =
                s3.initiateMultipartUpload(new InitiateMultipartUploadRequest(bucket, fileName));
            String uploadId = initiateMultipartUploadResult.getUploadId();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                s3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
                s3.abortMultipartUpload(new AbortMultipartUploadRequest(bucket, fileName,uploadId));
            } catch (IOException e) {
                new ImageException(IMAGE_TRANSFER);
            }
            imageUrlList.add(fileName);
        }
        return imageUrlList;
    }

    public void deleteFile(String prefix, String fileName) {
        s3.deleteObject(new DeleteObjectRequest(bucket, prefix + "/" + fileName));
    }


    /*presigned URL로 프론트에서 사진 업로드 처리*/
    public String uploadPreSignUrl(String prefix, String fileName) {
        /*CORS 설정: 없어도 잘 되는지 확인 필요*/
        List<AllowedMethods> methodRule = new ArrayList<AllowedMethods>();
        methodRule.add(CORSRule.AllowedMethods.PUT);
        methodRule.add(CORSRule.AllowedMethods.GET);
        methodRule.add(CORSRule.AllowedMethods.POST);
        CORSRule rule = new CORSRule().withId("CORSRule")
            .withAllowedMethods(methodRule)
            .withAllowedHeaders(Arrays.asList(new String[]{"*"}))
            .withAllowedOrigins(Arrays.asList(new String[]{"*"}))
            .withMaxAgeSeconds(3000);

        List<CORSRule> rules = new ArrayList<CORSRule>();
        rules.add(rule);

        /*유효 시간 10분*/
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10);

        return s3.generatePresignedUrl(bucket, prefix + "/" + fileName, calendar.getTime(),
            HttpMethod.PUT).toString();
    }

    public String deletePreSignUrl(String prefix, String fileName) {
        /*CORS 설정: 없어도 잘 되는지 확인 필요*/
        List<AllowedMethods> methodRule = new ArrayList<AllowedMethods>();
        methodRule.add(CORSRule.AllowedMethods.PUT);
        methodRule.add(CORSRule.AllowedMethods.GET);
        methodRule.add(CORSRule.AllowedMethods.POST);
        CORSRule rule = new CORSRule().withId("CORSRule")
            .withAllowedMethods(methodRule)
            .withAllowedHeaders(Arrays.asList(new String[]{"*"}))
            .withAllowedOrigins(Arrays.asList(new String[]{"*"}))
            .withMaxAgeSeconds(3000);

        List<CORSRule> rules = new ArrayList<CORSRule>();
        rules.add(rule);

        /*유효 시간 10분*/
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10);

        return s3.generatePresignedUrl(bucket, prefix + "/" + fileName, calendar.getTime(),
            HttpMethod.DELETE).toString();
    }
}
