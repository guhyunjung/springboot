package com.example.demo

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("com.**.service.impl")
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

