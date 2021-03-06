/*
 * Licensed to ElasticSearch and Shay Banon under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. ElasticSearch licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.elasticsearch.test.rest.section;

import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Represents a gt assert section:
 *
 *  - gt:    { fields._ttl: 0}
 *
 */
public class GreaterThanAssertion extends Assertion {

    private static final ESLogger logger = Loggers.getLogger(GreaterThanAssertion.class);

    public GreaterThanAssertion(String field, Object expectedValue) {
        super(field, expectedValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doAssert(Object actualValue, Object expectedValue) {
        logger.trace("assert that [{}] is greater than [{}]", actualValue, expectedValue);
        assertThat(actualValue, instanceOf(Comparable.class));
        assertThat(expectedValue, instanceOf(Comparable.class));
        assertThat(errorMessage(), (Comparable)actualValue, greaterThan((Comparable) expectedValue));
    }

    private String errorMessage() {
        return "field [" + getField() + "] is not greater than [" + getExpectedValue() + "]";
    }
}
