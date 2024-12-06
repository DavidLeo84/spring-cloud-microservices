package co.edu.uniquindio.services;

import co.edu.uniquindio.entities.Item;
import co.edu.uniquindio.entities.Product;
//import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

//@Primary
@Service
public class ItemServiceWebClient implements ItemService {

    private final WebClient.Builder webClient;

    public ItemServiceWebClient(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<Item> findAll() {
        return this.webClient.build()
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Product.class)
                .map(product -> new Item(product, new Random().nextInt(10) + 1))
                .collectList()
                .block();
    }

    @Override
    public Item findById(Long id) throws WebClientResponseException {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        //Bloque comentado para la implementación de las pruebas con el circuit-breaker
        //try {
            return webClient.build().get().uri("/{id}", params)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .map(product -> new Item(product, new Random().nextInt(10) + 1))
                    .block();
        /*} catch (WebClientResponseException e) {
            return null;
        }*/
    }
}
