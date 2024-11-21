package org.sid.billingservice;

import org.sid.billingservice.Repository.BillRepository;
import org.sid.billingservice.Repository.ProductItemRepository;
import org.sid.billingservice.Services.CustomerRestClient;
import org.sid.billingservice.Services.ProductRestClient;
import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BillRepository billRepository,
                                        ProductItemRepository productItemRepository,
                                        CustomerRestClient customerRestClient,
                                        ProductRestClient productRestClient) {

        return args -> {
            Collection<Product> products = productRestClient.AllProducts().getContent();
            Long customerId = 1L;
            Customer customer = customerRestClient.findCustomerById(customerId);
            if (customer == null) throw new RuntimeException("customer not found");

            Bill bill = new Bill();
            bill.setBillDate(new Date());
            bill.setCustomerId(customerId);

            // Exemple de code commenté pour une création de facture pour chaque client
//            customers.forEach(customer -> {
//                Bill bill = Bill.builder()
//                        .billingDate(new Date())
//                        .customerId(customer.getId())
//                        .build();

            Bill savedBill = billRepository.save(bill);

            products.forEach(product -> {
                ProductItem productItem = new ProductItem();
                productItem.setBill(savedBill);
                productItem.setProductId(product.getId());
                productItem.setQuantity(1 + new Random().nextInt(10));
                productItem.setPrice(product.getPrice());
                productItem.setDiscount(Math.random());
                productItemRepository.save(productItem);
            });
        };
    }
}
