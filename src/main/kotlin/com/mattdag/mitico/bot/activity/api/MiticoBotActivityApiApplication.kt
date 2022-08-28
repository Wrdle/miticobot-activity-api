package com.mattdag.mitico.bot.activity.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MiticoBotActivityApiApplication

@SuppressWarnings("SpreadOperator")
fun main(args: Array<String>) {
	runApplication<MiticoBotActivityApiApplication>(*args)
}
