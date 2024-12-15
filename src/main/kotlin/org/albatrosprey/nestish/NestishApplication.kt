package org.albatrosprey.nestish

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NestishApplication

fun main(args: Array<String>) {
	runApplication<NestishApplication>(*args)
}
