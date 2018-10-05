package com.test.ingenico;

/*
 * @Author Abinaya R
 * Created TransferServiceApplication.java
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Async;

/*Class is used to bootstrap and launch a Spring application from a Java main method. By default class will perform the following steps
 *  to bootstrap your application:
Create an appropriate ApplicationContext instance (depending on your classpath)
Refresh the application context, loading all singleton beans.
Scan component classes in the path mentioned
Scan entities and JPA Repositories
*/

@SpringBootApplication
@ComponentScan(basePackages ="com.test.ingenico.*")
@EnableJpaRepositories("com.test.ingenico.*")
@EntityScan("com.test.ingenico.*")
@Async 
public class TransferServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransferServiceApplication.class, args);
		
	}
}
