package com.example.demo.controller

import com.example.demo.service.DemoService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import javax.annotation.Resource

@Controller
class DemoController {

	companion object {
		private val log = LoggerFactory.getLogger(DemoController::class.java)
	}

	@Resource(name = "demoService")
	lateinit var demoService: DemoService

	@GetMapping("/user")
	fun list(@ModelAttribute("userVO") userVO: UserVO, model: Model): String {
		log.info("2. Actual Excuting - Welcome to @GetMapping('/')")

		val result = demoService.selectUserAllList(userVO)

		model.addAttribute("result", result)

		return "user"
	}

	@GetMapping("/add.do")
	fun add(): String = "add"

	@GetMapping("/home")
	fun home(): String = "home"

	@PostMapping("/addproc")
	fun addproc(@ModelAttribute("userVO") userVO: UserVO, model: Model): String {
		log.info("회원저장")

		demoService.addUser(userVO)

		return "redirect:/user"
	}
}