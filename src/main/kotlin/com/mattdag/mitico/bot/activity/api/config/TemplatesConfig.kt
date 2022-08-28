package com.mattdag.mitico.bot.activity.api.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.io.FileNotFoundException
import java.nio.file.FileSystemException

@Configuration
class TemplatesConfig {

    @Value("\${mitico.templates.activity}")
    private lateinit var activityTemplateResource: Resource

    @Bean
    fun activityTemplate(): String {
        if (isValidFile(activityTemplateResource)) {
            return activityTemplateResource.file.readText()
        }
        throw FileSystemException("Failed to read validate activityTemplateResource file")
    }

    private fun isValidFile(resource: Resource): Boolean {
        if (!resource.exists()) {
            throw FileNotFoundException("File ${resource.filename} could not be found")
        }
        if (!resource.isReadable) {
            throw FileSystemException("File is not readable")
        }
        return true
    }
}