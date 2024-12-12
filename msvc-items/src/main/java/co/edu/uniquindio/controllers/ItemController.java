package co.edu.uniquindio.controllers;

import co.edu.uniquindio.dtos.ProductDTO;
import co.edu.uniquindio.entities.Item;
import co.edu.uniquindio.libs.commons.msvc.libs.commons.entities.Product;
import co.edu.uniquindio.services.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RefreshScope
@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;
    private final CircuitBreakerFactory breaker;

    @Value("${configuration.text}")
    private String text;

    //Acceso a los variables de entorno del bootstrap.properties
    private final Environment env;

    public ItemController(@Qualifier("itemServiceWebClient") ItemService itemService,
                          CircuitBreakerFactory breaker, Environment env) {
        this.itemService = itemService;
        this.breaker = breaker;
        this.env = env;
    }

    @GetMapping("/fetch-config")
    public ResponseEntity<?> fetchConfig(@Value("${server.port}") String port) {
        Map<String, String> json = new HashMap<>();
        json.put("text", text);
        json.put("port", port);
        logger.info(text);
        logger.info(port);
        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            json.put("author.name", env.getProperty("configuration.author.name"));
            json.put("author.email", env.getProperty("configuration.author.email"));
        }
        return ResponseEntity.ok(json);
    }

    @GetMapping
    public List<Item> getAllItems(@RequestParam(name = "name", required = false) String name,
                                  @RequestHeader(name = "token-request", required = false) String token) {
        System.out.println("name = " + name);
        System.out.println("token = " + token);
        return itemService.findAll();
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) throws WebClientResponseException {
        //Bloque comentado para la implementación de las pruebas con el circuit-breaker
        Item item = breaker.create("items").run(() -> itemService.findById(id) , e ->{
            logger.error(e.getMessage());

            Product product = new Product();
            product.setCreateAt(LocalDate.now());
            product.setId(1L);
            product.setName("VHS SONY");
            product.setPrice(250.00);
            return new Item(product, 5);
        });
        if (item == null) {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "No existe el producto"));
        }
        return ResponseEntity.ok().body(item);
    }*/

    @CircuitBreaker(name = "items", fallbackMethod = "getFallBackMethodProduct")
    @GetMapping("/details/{id}")
    public ResponseEntity<?> getOneById(@PathVariable Long id) throws WebClientResponseException {
        Item item = itemService.findById(id);
        if (item == null) {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", "No existe el producto"));
        }
        return ResponseEntity.ok().body(item);
    }

    @TimeLimiter(name = "items")
    @CircuitBreaker(name = "items", fallbackMethod = "getFallBackMethodProduct2")
    @GetMapping("/{id}")
//    @GetMapping("/details2/{id}")
    public CompletableFuture<ResponseEntity<?>> getItemById(@PathVariable Long id) throws WebClientResponseException {
        return CompletableFuture.supplyAsync(() -> {

            Item item = itemService.findById(id);
            if (item == null) {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "No existe el producto"));
            }
            return ResponseEntity.ok().body(item);
        });
    }


    public ResponseEntity<?> getFallBackMethodProduct(Throwable e) {

            logger.error(e.getMessage());

            Product product = new Product();
            product.setCreateAt(LocalDate.now());
            product.setId(1L);
            product.setName("VHS SONY");
            product.setPrice(250.00);
            return ResponseEntity.ok( new Item(product, 5));
    }


    public CompletableFuture<ResponseEntity<?>> getFallBackMethodProduct2(Throwable e) {
        return CompletableFuture.supplyAsync(() -> {

            logger.error(e.getMessage());

            Product product = new Product();
            product.setCreateAt(LocalDate.now());
            product.setId(1L);
            product.setName("VHS SONY");
            product.setPrice(250.00);
            return ResponseEntity.ok( new Item(product, 5));
        });
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO dto) {
        itemService.saveProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Producto creado"));
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO dto){

        try {
            itemService.updateProduct(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(itemService.updateProduct(dto));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            itemService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.singletonMap("message", "Producto eliminado"));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
