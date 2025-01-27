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

import static com.adobe.testing.s3mock.dto.DtoTestUtil.deserialize;
import static com.adobe.testing.s3mock.dto.DtoTestUtil.serializeAndAssert;
import static com.adobe.testing.s3mock.dto.LifecycleRule.Status.ENABLED;
import static com.adobe.testing.s3mock.dto.StorageClass.GLACIER;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class BucketLifecycleConfigurationTest {

  @Test
  void testDeserialization(TestInfo testInfo) throws IOException {
    BucketLifecycleConfiguration iut = deserialize(BucketLifecycleConfiguration.class, testInfo);

    List<LifecycleRule> rules = iut.getRules();
    assertThat(rules).hasSize(2);
    LifecycleRule rule1 = rules.get(0);
    assertThat(rule1.getId()).isEqualTo("id1");
    assertThat(rule1.getExpiration()).isNull();
    LifecycleRuleFilter filter1 = rule1.getFilter();
    assertThat(filter1).isNotNull();
    assertThat(filter1.getPrefix()).isEqualTo("documents/");
    assertThat(rule1.getStatus()).isEqualTo(ENABLED);
    List<Transition> transitions1 = rule1.getTransitions();
    assertThat(transitions1).hasSize(1);
    assertThat(transitions1.get(0).getDate()).isNull();
    assertThat(transitions1.get(0).getDays()).isEqualTo(30);
    assertThat(transitions1.get(0).getStorageClass()).isEqualTo(GLACIER);
    assertThat(rule1.getAbortIncompleteMultipartUpload()).isNull();
    assertThat(rule1.getNoncurrentVersionExpiration()).isNull();
    assertThat(rule1.getNoncurrentVersionTransitions()).isNull();

    LifecycleRule rule2 = rules.get(1);
    assertThat(rule2.getId()).isEqualTo("id2");
    LifecycleRuleFilter filter2 = rule2.getFilter();
    assertThat(filter2).isNotNull();
    assertThat(filter2.getPrefix()).isEqualTo("logs/");
    assertThat(rule2.getStatus()).isEqualTo(ENABLED);
    assertThat(rule2.getExpiration()).isNotNull();
    assertThat(rule2.getExpiration().getDays()).isEqualTo(365);
    assertThat(rule2.getAbortIncompleteMultipartUpload()).isNull();
    assertThat(rule2.getNoncurrentVersionExpiration()).isNull();
    assertThat(rule2.getNoncurrentVersionTransitions()).isNull();

  }

  @Test
  void testSerialization(TestInfo testInfo) throws IOException {
    LifecycleRuleFilter filter1 = new LifecycleRuleFilter(null, null, "documents/", null, null);
    Transition transition1 = new Transition(null, 30, GLACIER);
    LifecycleRule rule1 = new LifecycleRule(null, null, filter1, "id1", null, null,
        ENABLED, Collections.singletonList(transition1));
    LifecycleRuleFilter filter2 = new LifecycleRuleFilter(null, null, "logs/", null, null);
    LifecycleExpiration expiration2 = new LifecycleExpiration(null, 365, null);
    LifecycleRule rule2 = new LifecycleRule(null, expiration2, filter2, "id2", null, null,
        ENABLED, null);
    BucketLifecycleConfiguration iut =
        new BucketLifecycleConfiguration(Arrays.asList(rule1, rule2));
    serializeAndAssert(iut, testInfo);
  }

}
