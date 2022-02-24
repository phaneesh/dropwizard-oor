package io.dropwizard.oor.resources;

import com.codahale.metrics.health.HealthCheckRegistry;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.val;

@Produces(MediaType.APPLICATION_JSON)
@Path("/healthcheck/status")
public class HealthCheckResource {

  private HealthCheckRegistry registry;

  public HealthCheckResource(HealthCheckRegistry registry) {
    this.registry = registry;
  }

  @GET
  public Response getHealthcheckStatus() {
    val healthCheck = registry.runHealthChecks().entrySet();
    return healthCheck.stream()
        .filter(entry -> !entry.getValue().isHealthy())
        .findFirst()
        .map(result ->
            Response.serverError()
                .entity(healthCheck)
                .build()
        )
        .orElse(Response.ok()
            .entity(healthCheck)
            .build());
  }

}