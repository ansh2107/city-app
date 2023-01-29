package com.cities.springbatchcities;

import com.cities.springbatchcities.common.UserConstant;
import com.cities.springbatchcities.dao.User;
import com.cities.springbatchcities.repo.UserRepository;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.logging.Logger;

@SpringBootApplication
public class SpringBatchCitiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchCitiesApplication.class, args);
	}

	@Component
	public class PostConstructExampleBean {

		@Autowired
		private UserRepository repository;

		@Autowired
		private BCryptPasswordEncoder passwordEncoder;

		@PostConstruct
		public void init() {
			// create regular user
			User regularUser = new User(1, "Regular_User", "User123@", true, UserConstant.DEFAULT_ROLE);
			String encryptedPwd = passwordEncoder.encode(regularUser.getPassword());
			regularUser.setPassword(encryptedPwd);
			repository.save(regularUser);

			// create admin user
			User adminUser = new User(2, "Admin_User", "Admin123@", true, UserConstant.ADMIN_ACCESS[0]);
			String encryptedPwdForAdmin = passwordEncoder.encode(adminUser.getPassword());
			adminUser.setPassword(encryptedPwdForAdmin);
			repository.save(adminUser);
		}
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin",
															"Content-Type",
															"Accept",
															"Authorization",
															"Access-Control-Allow-Methods",
															"Access-Control-Allow-Headers",
															"Access-Control-Max-Age",
															"Access-Control-Allow-Origin",
															"Access-Control-Allow-Origin",
															"Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}
