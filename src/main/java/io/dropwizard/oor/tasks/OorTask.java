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

package io.dropwizard.oor.tasks;

import io.dropwizard.oor.healtcheck.OorHealthCheck;
import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * @author phaneesh
 */
public class OorTask extends Task {

    public OorTask() {
        super("OorTask");
    }

    @Override
    public void execute(Map<String, List<String>> params, PrintWriter printWriter) throws Exception {
        OorHealthCheck.oor.getAndSet(true);
        printWriter.println("Service out of rotation");
    }
}
