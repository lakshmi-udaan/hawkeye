package com.hawkeye.resources

import com.fasterxml.jackson.annotation.JsonProperty
import com.udaan.snorql.extensions.performance.PerformanceEnums
import com.udaan.snorql.extensions.performance.models.ActiveQueryInput
import com.udaan.snorql.extensions.performance.models.ActiveQueryResult
import com.udaan.snorql.framework.metric.SqlMetricManager
import com.udaan.snorql.framework.models.IMetricRecommendation
import com.udaan.snorql.framework.models.MetricPeriod
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/snorql")
class SnorqlMetricsResource {
    /*
    * Fetches list of active-queries metric for a [activeQueryMetricInput]
    */
    @POST
    @Path("activeQueries")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getActiveQueryMetric(
        input: Input,
        @Suspended asyncResponse: AsyncResponse
    ) {

        val activeQueryInput = ActiveQueryInput(
            databaseName = input.databaseName,
            metricPeriod = input.metricPeriod,
            metricId = input.metricId
        )
        try {
            val response = SqlMetricManager.getMetric<ActiveQueryInput, ActiveQueryResult, IMetricRecommendation>(
                PerformanceEnums.ACTIVE_QUERIES.getId(),
                activeQueryInput
            )
            asyncResponse.resume(Response.ok(response).build())

        } catch (e: Exception) {
            println("Something went wrong. Error : ${e.message as String}")
            Response.status(500).entity(e.message as String).build()
        }
    }
}

data class Input (
    @JsonProperty("metricId")
    val metricId: String = PerformanceEnums.ACTIVE_QUERIES.getId(),
    @JsonProperty("metricPeriod")
    val metricPeriod: MetricPeriod,
    @JsonProperty("databaseName")
    val databaseName: String
)
