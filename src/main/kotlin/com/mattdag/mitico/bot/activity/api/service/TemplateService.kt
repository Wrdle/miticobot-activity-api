package com.mattdag.mitico.bot.activity.api.service

import com.mattdag.mitico.bot.activity.api.config.TemplatesConfig
import dev.kord.core.entity.Presence
import org.springframework.stereotype.Service

@Service
class TemplateService(
    val templatesConfig: TemplatesConfig
) {
    fun createActivitySVG(presence: Presence): String? {
        val activity = presence.activities.firstOrNull()
        if (activity != null) {
            return templatesConfig.newActivitySvgBuilder().apply {
                name = activity.name
                details = activity.details
                state = activity.state
                startTime = activity.start
                assets = activity.assets
                applicationId = activity.applicationId?.toString()
            }.build()
        }
        return null
    }
}
