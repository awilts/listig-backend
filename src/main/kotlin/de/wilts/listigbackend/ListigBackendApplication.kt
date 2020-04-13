package de.wilts.listigbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class ListigBackendApplication : SpringBootServletInitializer()

fun main(args: Array<String>) {
	runApplication<ListigBackendApplication>(*args)
}
