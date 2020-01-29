/*
 * Copyright © 2020 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.cdap.runtime.dataproc.launcher;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dataproc.v1.HadoopJob;
import com.google.cloud.dataproc.v1.Job;
import com.google.cloud.dataproc.v1.JobControllerClient;
import com.google.cloud.dataproc.v1.JobControllerSettings;
import com.google.cloud.dataproc.v1.JobPlacement;
import com.google.cloud.dataproc.v1.SubmitJobRequest;
import io.cdap.cdap.runtime.spi.launcher.Launcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
public class DataprocLauncher implements Launcher {
  private static final Logger LOG = LoggerFactory.getLogger(DataprocLauncher.class);

  @Override
  public String getName() {
    return "dataproc-launcher";
  }

  @Override
  public void launch(Map<String, URI> localFiles) {
    LOG.info("Inside dataproc launcher");
    LOG.info("launcher jar classpath {}", new File(localFiles.get("launcher.jar")).getAbsolutePath());
    List<String> collect = localFiles.values().stream().map(
      x -> new File(x).getAbsolutePath()).collect(Collectors.toList());
    for (String url : collect) {
      LOG.info("classpath {}", url);
    }
    // TODO figure out how to pass in cluster information
    try {
      SubmitJobRequest request = SubmitJobRequest.newBuilder()
        .setRegion("us-west1")
        .setProjectId("vini-project-238000")
        .setJob(Job.newBuilder().setPlacement(JobPlacement.newBuilder().setClusterName("test-zk").build())
                  .setHadoopJob(HadoopJob.newBuilder()
                                  .setMainClass("io.cdap.cdap.internal.app.runtime.distributed.launcher.TestLauncher")
                                  .addAllJarFileUris(collect)
                                  .build())
                  .build())
        .build();
      CredentialsProvider credentialsProvider = FixedCredentialsProvider
        .create(GoogleCredentials.getApplicationDefault());
      JobControllerClient client = JobControllerClient.create(
        JobControllerSettings.newBuilder().setCredentialsProvider(credentialsProvider)
          .setEndpoint("us-west1-dataproc.googleapis.com:443").build()
      );
      Job job = client.submitJob(request);
      LOG.info("Launched hadoop job on dataproc");
    } catch (Exception e) {
      LOG.error("Error while launching hadoop job on dataproc {}", e.getMessage(), e);
    }
  }
}
