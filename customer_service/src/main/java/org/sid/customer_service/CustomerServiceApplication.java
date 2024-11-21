package org.sid.customer_service;

import org.sid.customer_service.Repositories.CustomerRepository;
import org.sid.customer_service.config.CustomerConfigParams;
import org.sid.customer_service.entities.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(CustomerConfigParams.class)
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
                                               RepositoryRestConfiguration restConfiguration){
        return args -> {
            restConfiguration.exposeIdsFor(Customer.class);
            customerRepository.saveAll(
                    List.of(
                            Customer.builder().name("Hassan").email("hassan@gmail.com").build(),
                            Customer.builder().name("Hanane").email("hanan@gmail.com").build(),
                            Customer.builder().name("Imane").email("imane@gmail.com").build()

                    )
            );
            customerRepository.findAll().forEach(System.out::println);
        };
    }

}
