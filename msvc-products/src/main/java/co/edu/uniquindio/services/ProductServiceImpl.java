package co.edu.uniquindio.services;

import co.edu.uniquindio.dtos.ProductDTO;
import co.edu.uniquindio.entities.Product;
import co.edu.uniquindio.repositories.ProductRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final Environment env;

    public ProductServiceImpl(ProductRepository productRepository, Environment env) {
        this.productRepository = productRepository;
        this.env = env;
    }

    @Override
    public void createProduct(ProductDTO dto) {
        Product nuevo = Product.builder()
                .name(dto.name())
                .price(dto.price())
                .createAt(dto.createAt())
                .build();
    }

    @Override
    public void updateProduct(ProductDTO dto, Long id) throws Exception {

        Optional<Product> optional = productRepository.findById(id);

        if (!optional.isPresent()) {
            throw new Exception("No existe el producto");
        }
        Product product = optional.get();
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setCreateAt(dto.createAt());
        productRepository.save(product);

    }

    @Override
    public void deleteProduct(Long id) throws Exception {

        Optional<Product> optional = productRepository.findById(id);

        if (!optional.isPresent()) {
            throw new Exception("No existe el producto");
        }
        Product product = optional.get();
        productRepository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProducts() throws Exception {
        List<Product> list = productRepository.findAll();
        if (list.isEmpty()) {
            throw new Exception("La lista esta vacía");
        }
        list.stream().forEach(p -> p.setPort(Integer.parseInt(env.getProperty("local.server.port"))));

        return list.stream().map(p -> new ProductDTO(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getCreateAt(),
                p.getPort()
        )).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProduct(Long id) throws InterruptedException {

        Optional<Product> optional = productRepository.findById(id);

        if (!optional.isPresent()) {
            throw new InterruptedException("No existe el producto");
        }
        Product product = optional.get();
        product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCreateAt(),
                product.getPort()
        );
    }
}
