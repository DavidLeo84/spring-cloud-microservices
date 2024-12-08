package co.edu.uniquindio.services;

import co.edu.uniquindio.dtos.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    void createProduct(ProductDTO dto);
    ProductDTO updateProduct(ProductDTO dto) throws Exception;
    void deleteProduct(Long id) throws Exception;
    List<ProductDTO> getProducts() throws Exception;
    ProductDTO getProduct(Long id) throws InterruptedException;
}
