package com.mattdag.mitico.bot.activity.api.builders

import com.mattdag.mitico.bot.activity.api.util.PresenceAssetsUtils
import com.mattdag.mitico.bot.activity.api.util.findAndReplaceFirst
import dev.kord.core.entity.Activity
import kotlinx.datetime.toJavaInstant
import java.time.Duration
import kotlinx.datetime.Instant as KtInstant
import java.time.Instant as JavaInstant

private const val TEXT_START_Y_POS = 18
private const val CENTER_MULTIPLIER = 14

private const val PH_NAME = "\${APPLICATION_NAME}"
private const val PH_DETAILS = "\${DETAILS}"
private const val PH_STATE = "\${STATE}"
private const val PH_ELAPSED = "\${ELAPSED}"

private const val PH_LARGE_IMAGE_URL = "\${LARGE_IMAGE_URL}"

private const val PH_TEXT_Y_POS = "\${TEXT_Y_POS}"

class ActivitySvgBuilder(template: String) {

    private var templateStrBuilder = StringBuilder(template)

    var name: String? = null

    var details: String? = ""
        set(value) {
            field = value ?: ""
        }

    var state: String? = ""
        set(value) {
            field = value ?: ""
        }

    var startTime: KtInstant? = null

    var assets: Activity.Assets? = null

    var applicationId: String? = null

    fun build(): String {
        if (name != null) {
            applyActivityText()
            applyActivityImages()
            applyVerticalTextCentering()
            return templateStrBuilder.toString()
        }

        throw NullPointerException("Cannot create ActivitySVG with null application name")
    }

    private fun applyActivityText() {
        templateStrBuilder.findAndReplaceFirst(PH_NAME, name!!)
        templateStrBuilder.findAndReplaceFirst(PH_DETAILS, details!!)
        templateStrBuilder.findAndReplaceFirst(PH_STATE, state!!)
        templateStrBuilder.findAndReplaceFirst(PH_ELAPSED, getElapsedTimeStringOrBlank())
    }

    private fun applyActivityImages() {
        if (assets?.largeImage != null) {
            val url = PresenceAssetsUtils.getAssetUrl(applicationId!!, assets!!.largeImage!!)
            templateStrBuilder.findAndReplaceFirst(PH_LARGE_IMAGE_URL, url)
        }

        if (assets?.smallImage != null) {
            val url = PresenceAssetsUtils.getAssetUrl(applicationId!!, assets!!.smallImage!!)
            //templateStrBuilder.findAndReplaceFirst(PH_LARGE_IMAGE_URL, url)
        }
    }

    private fun applyVerticalTextCentering() {
        templateStrBuilder.findAndReplaceFirst(PH_TEXT_Y_POS, calculateTextYPos().toString())
    }

    private fun calculateTextYPos(): Int {
        var startPos = TEXT_START_Y_POS
        startPos += if (details.isNullOrBlank()) CENTER_MULTIPLIER else 0
        startPos += if (state.isNullOrBlank()) CENTER_MULTIPLIER else 0
        startPos += if (startTime == null) CENTER_MULTIPLIER else 0
        return startPos
    }

    private fun getElapsedTimeStringOrBlank(): String {
        if (startTime != null) {
            return Duration.between(startTime?.toJavaInstant(), JavaInstant.now()).toSeconds().toString() + " Elapsed"
        }
        return ""
    }
}
