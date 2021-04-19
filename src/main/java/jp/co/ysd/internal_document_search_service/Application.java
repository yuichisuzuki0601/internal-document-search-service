package jp.co.ysd.internal_document_search_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author yuichi
 *
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan({ "jp.co.ysd" })
@ImportResource("classpath*:application-context.xml")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
