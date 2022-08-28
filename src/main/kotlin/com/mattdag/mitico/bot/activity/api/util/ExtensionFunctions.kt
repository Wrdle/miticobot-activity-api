package com.mattdag.mitico.bot.activity.api.util

fun StringBuilder.findAndReplaceFirst(old: String, new:String) {
    val index = this.indexOf(old)
    this.replace(index, index + old.length, new)
}