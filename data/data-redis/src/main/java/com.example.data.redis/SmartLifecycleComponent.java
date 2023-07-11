/*
 * Copyright 2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.data.redis;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.context.SmartLifecycle;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SmartLifecycleComponent implements SmartLifecycle {

	private final AtomicBoolean running = new AtomicBoolean(false);

	private final StringRedisTemplate redisTemplate;

	public SmartLifecycleComponent(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void start() {

		running.set(true);
		try {
			String currentState = redisTemplate.opsForValue().getAndSet("data-redis", "started");
			System.out.println("Start Redis Connection - was %s before.".formatted(currentState));
		}
		catch (IllegalStateException e) {
			System.out.println("Connection Factory not ready");
		}
	}

	@Override
	public void stop() {

		String currentState = redisTemplate.opsForValue().getAndSet("data-redis", "stopped");
		System.out.println("Stop Redis Connection - was %s before.".formatted(currentState));
		running.set(false);
	}

	@Override
	public boolean isRunning() {
		return running.get();
	}

}
