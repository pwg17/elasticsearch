/*
 * Licensed to ElasticSearch and Shay Banon under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. ElasticSearch licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.index.analysis;

import org.elasticsearch.Version;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.indices.analysis.PreBuiltTokenizers;
import org.elasticsearch.test.ElasticsearchTestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

/**
 *
 */
public class PreBuiltTokenizerFactoryFactoryTests extends ElasticsearchTestCase {

    @Test
    public void testThatDifferentVersionsCanBeLoaded() {
        PreBuiltTokenizerFactoryFactory factory = new PreBuiltTokenizerFactoryFactory(PreBuiltTokenizers.STANDARD.getTokenizerFactory(Version.CURRENT));

        TokenizerFactory emptySettingsTokenizerFactory = factory.create("standard", ImmutableSettings.EMPTY);
        // different es versions, same lucene version, thus cached
        TokenizerFactory former090TokenizerFactory = factory.create("standard", ImmutableSettings.settingsBuilder().put(IndexMetaData.SETTING_VERSION_CREATED, Version.V_0_90_1).build());
        TokenizerFactory former090TokenizerFactoryCopy = factory.create("standard", ImmutableSettings.settingsBuilder().put(IndexMetaData.SETTING_VERSION_CREATED, Version.V_0_90_2).build());
        TokenizerFactory currentTokenizerFactory = factory.create("standard", ImmutableSettings.settingsBuilder().put(IndexMetaData.SETTING_VERSION_CREATED, Version.CURRENT).build());

        assertThat(emptySettingsTokenizerFactory, is(currentTokenizerFactory));
        assertThat(emptySettingsTokenizerFactory, is(not(former090TokenizerFactory)));
        assertThat(emptySettingsTokenizerFactory, is(not(former090TokenizerFactoryCopy)));
        assertThat(former090TokenizerFactory, is(former090TokenizerFactoryCopy));
    }

}
