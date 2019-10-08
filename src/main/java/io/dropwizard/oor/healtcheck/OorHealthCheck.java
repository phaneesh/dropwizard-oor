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
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author phaneesh
 */
@Slf4j
@Singleton
public class OorHealthCheck extends HealthCheck {

    public static AtomicBoolean oor;

    public OorHealthCheck(boolean status) {
        oor = new AtomicBoolean(status);
    }

    @Override
    protected Result check() throws Exception {
        Result result = null;
        if(oor.get()) {
            result = Result.unhealthy("Service is out of rotation");
        } else {
            result = Result.healthy();
        }
        log.debug("OORBundle HealthCheck status: {}, {}", result.isHealthy(), result.getMessage());
        return result;
    }

    public static void setOor(boolean state) {
        oor.getAndSet(state);
    }
}
