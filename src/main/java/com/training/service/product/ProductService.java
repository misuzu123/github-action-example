package com.training.service.product;

import com.training.dto.product.ProductDto;
import com.training.dto.product.ProductRequest;
import com.training.entity.Product;
import java.util.List;

public interface ProductService {

    void createNewProduct(ProductRequest request);

    ProductDto getInfoProduct(Integer id);

    List<ProductDto> getListProduct();

    ProductDto updateProduct(Integer id, ProductRequest request);

    void deleteProduct(List<Integer> ids);

    Product findProductById(Integer id);

}
