package com.mattdag.mitico.bot.activity.api.service

import dev.kord.cache.api.query
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.cache.idEq
import dev.kord.core.entity.Presence
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Component

@Component
class PresenceService(private val kordClient: Kord) {

    suspend fun getMemberPresenceFromAnyGuild(memberId: Snowflake): Presence {
        return getMemberPresenceFromCache(memberId) ?: findMemberPresenceFromGuilds(memberId)
    }

    private suspend fun getMemberPresenceFromCache(memberId: Snowflake): Presence? =
        kordClient.cache.query {
            idEq(Presence::userId, memberId)
        }.toCollection().firstOrNull()

    private suspend fun findMemberPresenceFromGuilds(memberId: Snowflake): Presence =
        kordClient.guilds.toList().first { guild ->
            guild.members.toList().any { member -> member.id == memberId }
        }.getMember(memberId).getPresence()
}