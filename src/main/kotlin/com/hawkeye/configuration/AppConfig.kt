package com.hawkeye.configuration

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration

class AppConfig(@JsonProperty("environment") val environment: String): Configuration()
