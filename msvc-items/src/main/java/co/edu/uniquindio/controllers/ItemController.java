package co.edu.uniquindio.controllers;

import co.edu.uniquindio.entities.Item;
import co.edu.uniquindio.entities.Product;
import co.edu.uniquindio.services.ItemService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;
    private final CircuitBreakerFactory breaker;

    public ItemController(@Qualifier("itemServiceWebClient") ItemService itemService,
                          CircuitBreakerFactory breaker) {
        this.itemService = itemService;
        this.breaker = breaker;
    }

    @GetMapping
    public List<Item> getAllItems(@RequestParam(name = "name", required = false) String name,
                                  @RequestHeader(name = "token-request", required = false) String token) {
        System.out.println("name = " + name);
        System.out.println("token = " + token);
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) throws WebClientResponseException {
        Item item = breaker.create("items").run(() -> itemService.findById(id) /*, e ->{
            logger.error(e.getMessage());

            Product product = new Product();
            product.setCreateAt(LocalDate.now());
            product.setId(1L);
            product.setName("VHS SONY");
            product.setPrice(250.00);
            return new Item(product, 5);
        }*/);
        if (item == null) {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "No existe el producto"));
        }
        return ResponseEntity.ok().body(item);
    }
}
