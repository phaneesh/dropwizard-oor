/*
 * Copyright (c) 2016 Phaneesh Nagaraja <phaneesh.n@gmail.com>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package io.dropwizard.oor.healtcheck;

import com.codahale.metrics.health.HealthCheck;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author phaneesh
 */
@Slf4j
@Singleton
public class OorHealthCheck extends HealthCheck {

    @Getter
    private static final AtomicBoolean oor = new AtomicBoolean();

    public OorHealthCheck(boolean status) {
        oor.set(status);
    }

    @Override
    protected Result check() {
        Result result;
        if(oor.get()) {
            result = Result.unhealthy("Service is out of rotation");
        } else {
            result = Result.healthy();
        }
        if(log.isDebugEnabled()) {
            log.debug("OORBundle HealthCheck status: {}, {}", result.isHealthy(), result.getMessage());
        }
        return result;
    }
}
