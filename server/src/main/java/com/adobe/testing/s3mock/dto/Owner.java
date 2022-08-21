/*
 *  Copyright 2017-2022 Adobe.
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

package com.adobe.testing.s3mock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Owner of a Bucket.
 * <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_Owner.html">API Reference</a>
 */
public class Owner {

  /**
   * Default owner in S3Mock until support for ownership is implemented.
   */
  public static final Owner DEFAULT_OWNER = new Owner(123, "s3-mock-file-store");

  @JsonProperty("ID")
  private long id;

  @JsonProperty("DisplayName")
  private String displayName;

  public Owner() {
    // Jackson needs the default constructor for deserialization.
  }

  public Owner(final long id, final String displayName) {
    this.id = id;
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public long getId() {
    return id;
  }

  public void setDisplayName(final String displayName) {
    this.displayName = displayName;
  }

  public void setId(final long id) {
    this.id = id;
  }
}
