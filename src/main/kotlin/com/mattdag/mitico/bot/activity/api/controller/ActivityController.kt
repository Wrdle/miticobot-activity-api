package com.mattdag.mitico.bot.activity.api.controller

import com.mattdag.mitico.bot.activity.api.service.TemplateService
import com.mattdag.mitico.bot.activity.api.config.TemplatesConfig
import com.mattdag.mitico.bot.activity.api.service.PresenceService
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.entity.Presence
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/activity")
class ActivityController(
    val kordClient: Kord,
    val presenceService: PresenceService,
    val templateService: TemplateService,
    val templatesConfig: TemplatesConfig
) {

    @GetMapping(path = ["/activity"])
    suspend fun activity(@RequestParam(name = "memberId") memberId: Long): Presence {
        val member = Snowflake(memberId)
        return presenceService.getMemberPresenceFromAnyGuild(member)
    }

    @GetMapping(path = ["/getActivityImage"])
    suspend fun getActivityImage(@RequestParam(name = "memberId") memberId: Long): String? {
        val member = Snowflake(memberId)
        val presence = presenceService.getMemberPresenceFromAnyGuild(member)
        return templateService.createActivitySVG(presence)
    }
}


