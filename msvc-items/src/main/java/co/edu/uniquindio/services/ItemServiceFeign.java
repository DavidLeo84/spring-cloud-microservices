package co.edu.uniquindio.services;

import co.edu.uniquindio.client.ProductFeignClient;
import co.edu.uniquindio.dtos.ProductDTO;
import co.edu.uniquindio.entities.Item;
import co.edu.uniquindio.entities.Product;
import feign.FeignException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Random;

@Service
public class ItemServiceFeign implements ItemService{

    private final ProductFeignClient productFeignClient;

    public ItemServiceFeign(ProductFeignClient productFeignClient) {
        this.productFeignClient = productFeignClient;
    }

    @Override
    public List<Item> findAll() {
        return productFeignClient.findAll()
                .stream()
                .map(product ->
            new Item(product, new Random().nextInt(10) + 1)
        ).toList();
    }

    @Override
    public Item findById(Long id) throws WebClientResponseException {
        Item item = new Item();
        try {
            Product product = productFeignClient.detailsProduct(id);
            //return new Item(product,new Random().nextInt(10) + 1);
            item.setProduct(product);
            item.setQuantity(new Random().nextInt(10) + 1);
            return item;
        }
        catch (FeignException e) {
            return null;
        }
    }

    @Override
    public void saveProduct(ProductDTO dto) {
        productFeignClient.createProduct(dto);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO dto) throws Exception {
        try {
            productFeignClient.updateProduct(dto);
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
       return productFeignClient.updateProduct(dto);

    }

    @Override
    public void delete(Long id) throws Exception {
        try {
            productFeignClient.deleteProduct(id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }
}
