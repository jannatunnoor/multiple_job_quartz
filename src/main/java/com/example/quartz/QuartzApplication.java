package com.example.quartz;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication
//public class QuartzApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(QuartzApplication.class, args);
//	}
//
//}

@ComponentScan
@EnableScheduling
public class QuartzApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(QuartzApplication.class).bannerMode(Banner.Mode.OFF).run(args);
	}
}
