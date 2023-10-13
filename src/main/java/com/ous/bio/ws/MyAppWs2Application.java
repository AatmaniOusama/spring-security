package com.ous.bio.ws;

import com.ous.bio.ws.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ous.bio.ws.controllers.UserController;
import com.ous.bio.ws.services.Impl.UserServiceImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;


@SpringBootApplication//(exclude={DataSourceAutoConfiguration.class}/*, scanBasePackages = {"com.ous.bio.ws.repositories"}*/)
//@ComponentScan(basePackageClasses = { UserServiceImpl.class, UserController.class})
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class MyAppWs2Application {

	public static void main(String[] args) {
		SpringApplication.run(MyAppWs2Application.class, args);
	}


	/*@Bean(name="entityManagerFactory")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		return sessionFactory;
	}*/
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext(){
		return new SpringApplicationContext();
	}
}
