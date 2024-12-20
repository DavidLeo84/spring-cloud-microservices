package co.edu.uniquindio.services;

import co.edu.uniquindio.dtos.ProductDTO;
import co.edu.uniquindio.entities.Item;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.List;

@Service
public interface ItemService {

    List<Item> findAll();

    Item findById(Long id) throws WebClientResponseException;

    void saveProduct(ProductDTO dto);

    ProductDTO updateProduct(ProductDTO dto) throws Exception;

    void delete(Long id) throws Exception;
}
