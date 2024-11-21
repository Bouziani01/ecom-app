package org.sid.inventory_service;

import org.sid.inventory_service.entities.Product;
import org.sid.inventory_service.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);

    }
    @Bean
    CommandLineRunner start(ProductRepository productRepository, RepositoryRestConfiguration restConfiguration){
        return args -> { restConfiguration.exposeIdsFor(Product.class);
            productRepository.saveAll(
                    List.of(
                            Product.builder().name("Computer").quantity(12).price(1200).build(),
                            Product.builder().name("Printer").quantity(22).price(1100).build(),
                            Product.builder().name("Smartphone").quantity(30).price(1500).build()


                    )
            );

        };

    }
}