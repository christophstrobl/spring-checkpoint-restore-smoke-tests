package com.example.data.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebMvcController {

	StringRedisTemplate redisTemplate;

	public WebMvcController(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@GetMapping("/")
	public String hello() {
		return "Redis connection is %s".formatted(redisTemplate.opsForValue().get("data-redis"));
	}

}
