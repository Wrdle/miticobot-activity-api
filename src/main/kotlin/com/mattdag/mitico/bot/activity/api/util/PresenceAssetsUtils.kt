package com.mattdag.mitico.bot.activity.api.util

private const val ASSET_URL = "https://cdn.discordapp.com/app-assets/%s/%s.png"

object PresenceAssetsUtils {
    fun getAssetUrl(applicationId: String, assetId: String): String =
        String.format(ASSET_URL, applicationId, assetId)
}
