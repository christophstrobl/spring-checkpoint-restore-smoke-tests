package com.example.webmvc;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

@DataRedisTest
class RedisStoreTests {

	@Autowired
	private StringRedisTemplate template;

	@Test
	void check(GenericApplicationContext applicationContext) {

		String s = template.opsForValue().get("data-redis");
		assertThat(s).isNotNull();
		applicationContext.stop();
		assertThatExceptionOfType(IllegalStateException.class)
			.isThrownBy(() -> template.getConnectionFactory().getConnection());
		applicationContext.start();
		assertThat(template.opsForValue().get("data-redis")).contains("was stopped");
	}

}
