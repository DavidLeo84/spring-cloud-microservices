package co.edu.uniquindio.services;

import co.edu.uniquindio.dtos.ProductDTO;
import co.edu.uniquindio.entities.Item;
//import org.springframework.context.annotation.Primary;
import co.edu.uniquindio.libs.commons.msvc.libs.commons.entities.Product;
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

    @Override
    public void saveProduct(ProductDTO dto) {

        webClient.build()
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    @Override
    public ProductDTO updateProduct(ProductDTO dto) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", dto.id());
        return webClient.build()
                .put()
                .uri("", params)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(ProductDTO.class)
                .block();

    }

    @Override
    public void delete(Long id) {

        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        webClient.build()
                .delete()
                .uri("/{id}", params)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
