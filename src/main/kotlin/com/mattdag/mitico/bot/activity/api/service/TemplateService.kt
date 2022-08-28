package com.mattdag.mitico.bot.activity.api.service

import com.mattdag.mitico.bot.activity.api.config.TemplatesConfig
import com.mattdag.mitico.bot.activity.api.util.ImageType
import com.mattdag.mitico.bot.activity.api.util.PresenceAssetsUtils.getImageUrl
import com.mattdag.mitico.bot.activity.api.util.findAndReplaceFirst
import dev.kord.core.Kord
import dev.kord.core.entity.Activity
import dev.kord.core.entity.Presence
import org.springframework.stereotype.Service



private const val NAME_PLACEHOLDER = "\${APPLICATION_NAME}"
private const val DETAILS_PLACEHOLDER = "\${DETAILS}"
private const val STATE_PLACEHOLDER = "\${STATE}"
private const val DESCRIPTION_3_PLACEHOLDER = "\${DESCRIPTION_3}"

private const val LARGE_IMAGE_URL = "\${LARGE_IMAGE_URL}"

@Service
class TemplateService(
    val client: Kord,
    val templatesConfig: TemplatesConfig
) {
    fun createActivitySVG(presence: Presence): String? {
        val activity = presence.activities.firstOrNull()
        if (activity != null) {
            val contents = applyActivityText(templatesConfig.activityTemplate(), activity)
            return applyActivityImages(contents, activity);
        }
        return null
    }

    private fun applyActivityText(fileContents: String, activity: Activity): String {
        val contentsBuilder = StringBuilder(fileContents)
        contentsBuilder.findAndReplaceFirst(NAME_PLACEHOLDER, activity.name)

        if (activity.details != null) {
            contentsBuilder.findAndReplaceFirst(DETAILS_PLACEHOLDER, activity.details!!)
        } else {
            contentsBuilder.findAndReplaceFirst(DETAILS_PLACEHOLDER, "")
        }

        if (activity.state == null) {
            contentsBuilder.findAndReplaceFirst(STATE_PLACEHOLDER, activity.state!!)
        } else {
            contentsBuilder.findAndReplaceFirst(STATE_PLACEHOLDER, "")
        }

        if (activity.state == null) {
            contentsBuilder.findAndReplaceFirst(DESCRIPTION_3_PLACEHOLDER, activity.state!!)
        } else {
            contentsBuilder.findAndReplaceFirst(DESCRIPTION_3_PLACEHOLDER, "")
        }

        return contentsBuilder.toString()
    }

    private fun applyActivityImages(fileContents: String, activity: Activity): String {
        val contentsBuilder = StringBuilder(fileContents)

        if (activity.assets != null) {
            val url = getImageUrl(activity.applicationId.toString(), activity.assets!!, ImageType.BIG)
            contentsBuilder.findAndReplaceFirst(LARGE_IMAGE_URL, url)
        }

        if (activity.assets != null) {
            val url = getImageUrl(activity.applicationId.toString(), activity.assets!!, ImageType.SMALL)
            //contentsBuilder.replace(LARGE_IMAGE_URL, url)
        }
        return contentsBuilder.toString()
    }
}
