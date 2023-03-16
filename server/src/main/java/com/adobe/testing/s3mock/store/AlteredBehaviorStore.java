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

package com.adobe.testing.s3mock.store;

import com.adobe.testing.s3mock.S3MockApplication;

/**
 * Stores settings for altered non-default behaviors for the {@link S3MockApplication}.
 */
public class AlteredBehaviorStore {

  /**
   * True if PutObject should generate a SlowDown response error code.
   * False (default) for normal PutObject behavior.
   */
  private boolean forceSlowDownResponse;

  public AlteredBehaviorStore(boolean forceSlowDownResponse) {
    this.forceSlowDownResponse = forceSlowDownResponse;
  }

  public boolean isForceSlowDownResponse() {
    return forceSlowDownResponse;
  }

  public void setForceSlowDownResponse(boolean forceSlowDownResponse) {
    this.forceSlowDownResponse = forceSlowDownResponse;
  }
}
