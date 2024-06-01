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

import io.dropwizard.core.Configuration;
import io.dropwizard.core.ConfiguredBundle;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.oor.healtcheck.OorHealthCheck;
import io.dropwizard.oor.tasks.BirTask;
import io.dropwizard.oor.tasks.OorTask;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author phaneesh
 */
@Slf4j
public abstract class OorBundle<T extends Configuration> implements ConfiguredBundle<T> {

    public static final List<OorHook> oorHooks = new ArrayList<>();

    public static final List<BirHook> birHooks = new ArrayList<>();

    public abstract boolean withOor();

    @Override
    public void initialize(Bootstrap<?> bootstrap) {

    }

    public void registerOorHook(OorHook oorHook) {
        oorHooks.add(oorHook);
    }

    public void registerBirHook(BirHook birHook) {
        birHooks.add(birHook);
    }

    @Override
    public void run(T configuration, Environment environment) {
        environment.healthChecks().register("oor", new OorHealthCheck(withOor()));
        environment.admin().addTask(new OorTask());
        environment.admin().addTask(new BirTask());
    }
}
