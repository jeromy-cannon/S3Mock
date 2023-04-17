/*
 *  Copyright 2017-2023 Adobe.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
