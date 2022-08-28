package com.mattdag.mitico.bot.activity.api.commands

import dev.kord.core.Kord
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import kotlinx.coroutines.delay
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

val pingPong = ReactionEmoji.Unicode("\uD83C\uDFD3")

suspend fun attachCommands(kordClient: Kord) {
    attachPing(kordClient)
}

private suspend fun attachPing(kordClient: Kord) = kordClient.on<MessageCreateEvent> {
    logger.info { "Message content received: ${message.content}" }
    if (message.content != "!ping") return@on

    logger.info { "OMG we are pinging" }
    val response = message.channel.createMessage("Pong!")
    response.addReaction(pingPong)

    delay(5000)
    message.delete()
    response.delete()
}
