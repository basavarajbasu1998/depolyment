package com.yethi.code.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ViewController {

	@GetMapping(value = { "/", "/{x:[\\w\\-]+}", "/{x:[\\w\\-]+}/", "/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}",
			"/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}/" })
	public String forward(HttpServletRequest request) {
		final String url = request.getRequestURI();
		log.info("inside the contolerr");
		System.out.println("inside the contolerr");
		if (url.startsWith("/docs")) {
			return "forward:/docs/index.html";
		}

		return "forward:/index.html";
	}
}
