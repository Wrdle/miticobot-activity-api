package com.mattdag.mitico.bot.activity.api.util

import dev.kord.core.entity.Activity

private const val ASSET_URL = "https://cdn.discordapp.com/app-assets/%s/%s.png"

enum class ImageType(val value: Int) {
    BIG(0), SMALL(1)
}

object PresenceAssetsUtils {
    fun getImageUrl(applicationId: String, assets: Activity.Assets, imageType: ImageType): String {
        val imageId = if (imageType == ImageType.BIG) assets.largeImage else assets.smallImage
        return formatAssetUrl(applicationId, imageId.toString())
    }

    private fun formatAssetUrl(applicationId: String, assetId: String) = String.format(ASSET_URL, applicationId, assetId)
}