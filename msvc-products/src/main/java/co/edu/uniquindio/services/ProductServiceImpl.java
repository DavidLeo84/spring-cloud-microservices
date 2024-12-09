package co.edu.uniquindio.services;

import co.edu.uniquindio.dtos.ProductDTO;

import co.edu.uniquindio.libs.commons.msvc.libs.commons.entities.Product;
import co.edu.uniquindio.repositories.ProductRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

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
                .createAt(LocalDate.now())
                .build();
        productRepository.save(nuevo);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO dto) throws Exception {
        Optional<Product> optional = productRepository.findById(dto.id());
        if (optional.isEmpty()) {
            throw new Exception("No existe el producto");
        }
        Product product = optional.get();
        product.setName(dto.name());
        product.setPrice(dto.price());
        productRepository.save(product);
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                LocalDate.now(),
                product.getPort()
        );

    }

    @Override
    public void deleteProduct(Long id) throws Exception {

        Optional<Product> optional = productRepository.findById(id);

        if (optional.isEmpty()) {
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

        if (optional.isEmpty()) {
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
