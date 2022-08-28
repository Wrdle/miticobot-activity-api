package com.mattdag.mitico.bot.activity.api.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix="mitico")
data class MiticoConfigurationProperties(
    val token: String
)
