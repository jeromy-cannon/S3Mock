package com.adobe.testing.s3mock.service;

import static com.adobe.testing.s3mock.util.AwsHttpHeaders.X_AMZ_SERVER_SIDE_ENCRYPTION_AWS_KMS_KEY_ID;

import com.adobe.testing.s3mock.dto.ObjectKey;
import com.adobe.testing.s3mock.store.AlteredBehaviorStore;
import com.adobe.testing.s3mock.store.S3ObjectMetadata;
import org.springframework.http.ResponseEntity;

public class BehaviorService {
  private final AlteredBehaviorStore alteredBehaviorStore;

  public BehaviorService(AlteredBehaviorStore alteredBehaviorStore) {
    this.alteredBehaviorStore = alteredBehaviorStore;
  }

  public ResponseEntity<String> getPutObjectResponse(S3ObjectMetadata s3ObjectMetadata,
      String kmsKeyId, ObjectKey key) {
    if (alteredBehaviorStore.isForceSlowDownResponse()) {
      return ResponseEntity
          .status(503)
          .eTag(s3ObjectMetadata.getEtag())
          .lastModified(s3ObjectMetadata.getLastModified())
          .header(X_AMZ_SERVER_SIDE_ENCRYPTION_AWS_KMS_KEY_ID, kmsKeyId)
          .body("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
              + "<Error>\n"
              + "  <Code>SlowDown</Code>\n"
              + "  <Message>Please reduce your request rate.</Message>\n"
              + "  <Resource>" + key.getKey() + "</Resource> \n"
              + "  <RequestId>1234567890Example</RequestId>\n"
              + "</Error>");
    } else {
      return ResponseEntity
          .ok()
          .eTag(s3ObjectMetadata.getEtag())
          .lastModified(s3ObjectMetadata.getLastModified())
          .header(X_AMZ_SERVER_SIDE_ENCRYPTION_AWS_KMS_KEY_ID, kmsKeyId)
          .body("");
    }
  }
}
