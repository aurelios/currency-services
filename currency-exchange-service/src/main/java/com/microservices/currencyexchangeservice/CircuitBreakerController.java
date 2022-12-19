package com.microservices.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class  CircuitBreakerController {
	
	private Logger logger = 
				LoggerFactory.getLogger(CircuitBreakerController.class);

	/**
	 * Retries the number of time configured in application.properties before return a fallback response.
	 * @return
	 */
	@GetMapping("/sample-api/retry")
	@Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
	public String sampleApiRetry() {
		logger.info("Sample api call received");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url",
					String.class);
		return forEntity.getBody();
	}

	/**
	 * Uses CircuitBreak to stop doing calls to an endpoint with error and direclty returns the fallback-response depending
	 * on the states OPEN, HALF_OPEN, CLOSED.
	 * @return
	 */
	@GetMapping("/sample-api/circuit-breaker")
	@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
	public String sampleApiCircuitBreaker() {
		logger.info("Sample api call received");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url",
					String.class);
		return forEntity.getBody();
	}

	/**
	 * Uses CircuitBreak to stop doing calls to an endpoint with error and direclty returns the fallback-response depending
	 * on the states OPEN, HALF_OPEN, CLOSED.
	 * @return
	 */
	@GetMapping("/sample-api/rate-limiter")
	@RateLimiter(name="default")
	//10s => 2 calls to the sample api
	public String sampleApiCircuitBreakerRateLimiter() {
		logger.info("Sample api call received");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url",
				String.class);
		return forEntity.getBody();
	}

	@GetMapping("/sample-api/bulkhead")
	@Bulkhead(name="sample-api")
	public String sampleApi() {
		logger.info("Sample api call received");
		return "sample-api";
	}
	
	public String hardcodedResponse(Exception ex) {
		return "fallback-response";
	}
}
