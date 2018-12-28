package com.example.demo.service.impl

import com.example.demo.controller.UserVO
import org.springframework.stereotype.Repository

@Repository(value = "demoMapper")
interface DemoMapper {

	fun selectUserAllList(): List<UserVO>

	fun addUser(userVO: UserVO)

}