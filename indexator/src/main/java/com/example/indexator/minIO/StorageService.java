package com.example.indexator.minIO;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageService {
	
	private final MinioClient minioClient;
	
	@Value("${minio.bucket-name}")
    private String bucketName;
	
	public String downloadHtml(String key) throws Exception {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(key)
                        .build()
        )) {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
	
}
