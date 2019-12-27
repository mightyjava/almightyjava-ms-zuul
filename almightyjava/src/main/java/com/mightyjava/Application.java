package com.mightyjava;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.mightyjava.captcha.CaptchaDetailsSource;
import com.mightyjava.captcha.CaptchaGenerator;
import com.mightyjava.listener.CounterApplicationListener;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrix
@EnableZuulProxy
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CaptchaGenerator getCaptchaGenerator() {
		return new CaptchaGenerator();
	}

	@Bean
	public CounterApplicationListener getCounterApplicationListener() {
		return new CounterApplicationListener();
	}

	@Bean
	public CaptchaDetailsSource getCaptchaDetailsSource() {
		return new CaptchaDetailsSource();
	}

	@Bean
	public ZuulFilter postFilter() {
		return new ZuulFilter() {

			@Override
			public boolean shouldFilter() {
				return true;
			}

			@Override
			public Object run() throws ZuulException {
				System.out.println("Pre Filter - run");
				HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
				System.out.println("Request Method : " + request.getMethod());
				System.out.println("Request URL : " + request.getRequestURL().toString());
				return null;
			}

			@Override
			public String filterType() {
				return "pre";
			}

			@Override
			public int filterOrder() {
				return 1;
			}
		};
	}
}