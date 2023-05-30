package com.Travel.Travel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class BestTravelApplication{
	public static void main(String[] args) {
		SpringApplication.run(BestTravelApplication.class, args);
		//System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
}
