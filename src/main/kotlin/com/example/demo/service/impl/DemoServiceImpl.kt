package com.example.demo.service.impl

import com.example.demo.controller.UserVO
import com.example.demo.service.DemoService
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service(value = "demoService")
class DemoServiceImpl : DemoService {

	@Resource(name = "demoMapper")
	lateinit var demoMapper: DemoMapper

	override fun selectUserAllList(userVO: UserVO): List<UserVO> {
		return demoMapper.selectUserAllList()
	}

	override fun addUser(userVO: UserVO) {
		demoMapper.addUser(userVO)
	}
}