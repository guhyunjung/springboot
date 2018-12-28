package com.example.demo.service

import com.example.demo.controller.UserVO

interface DemoService {

	fun selectUserAllList(userVO: UserVO): List<UserVO>

	fun addUser(userVO: UserVO)
}