/*
 * Copyright 2016 Phaneesh Nagaraja <phaneesh.n@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.dropwizard.oor;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.oor.healtcheck.OorHealthCheck;
import io.dropwizard.oor.resources.HealthCheckResource;
import io.dropwizard.oor.tasks.BirTask;
import io.dropwizard.oor.tasks.OorTask;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

/**
 * @author phaneesh
 */
@Slf4j
public abstract class OorBundle<T extends Configuration> implements ConfiguredBundle<T> {

    public abstract boolean withOor();

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

    @Override
    public void run(T configuration, Environment environment) throws Exception {
        environment.healthChecks().register("oor", new OorHealthCheck(withOor()));
        environment.admin().addTask(new OorTask());
        environment.admin().addTask(new BirTask());
        if (exposeApplicationPortHealthCheck()) {
            environment.jersey().register(new HealthCheckResource(environment.healthChecks()));
        }
    }

    protected boolean exposeApplicationPortHealthCheck() {
        //Client to over-ride if healthCheck resource required at application port
        return false;
    }
}
