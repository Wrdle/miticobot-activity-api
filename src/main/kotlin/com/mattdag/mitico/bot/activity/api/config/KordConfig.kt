package com.mattdag.mitico.bot.activity.api.config

import com.mattdag.mitico.bot.activity.api.commands.attachCommands
import dev.kord.core.Kord
import dev.kord.gateway.Intent
import dev.kord.gateway.Intents
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.concurrent.thread

@Configuration
@EnableConfigurationProperties(MiticoConfigurationProperties::class)
class KordConfig(val config: MiticoConfigurationProperties) {

    private val logger = KotlinLogging.logger { }

    @Bean
    fun kordClient(): Kord {
        val client = runBlocking { getClient() }
        thread { runBlocking { login(client) } }
        return client
    }

    suspend fun getClient(): Kord{
        val client = Kord(config.token)
        logger.info { "Attaching commands" }
        attachCommands(client)
        return client
    }

    private suspend fun login(client: Kord) = client.login {
        @OptIn(PrivilegedIntent::class)
        intents += Intents.all
    }
}
