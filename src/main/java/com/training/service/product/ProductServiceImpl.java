package com.training.service.product;

import com.training.common.constant.Errors;
import com.training.dto.product.ProductDto;
import com.training.dto.product.ProductRequest;
import com.training.entity.Product;
import com.training.exception.BadRequestException;
import com.training.exception.ErrorParam;
import com.training.exception.SysError;
import com.training.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void createNewProduct(ProductRequest request) {
        checkExistProduct(request.getProductCode());
        Product product = new Product();
        product.setName(request.getName());
        product.setProductCode(request.getProductCode());
        product.setPrice(request.getPrice());
        productRepository.save(product);
    }

    @Override
    public ProductDto getInfoProduct(Integer id) {
        Product product = findProductById(id);
        return convertToDto(product);
    }

    @Override
    public List<ProductDto> getListProduct() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Integer id, ProductRequest request) {
        Product product = findProductById(id);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        productRepository.save(product);

        return convertToDto(product);
    }

    @Override
    @Transactional
    public void deleteProduct(List<Integer> ids) {
        List<Product> products = productRepository.findProductByIds(ids);
        productRepository.deleteAll(products);

    }

    @Override
    public Product findProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(
                () -> new BadRequestException(new SysError(
                        Errors.ERROR_PRODUCT_NOT_FOUND, new ErrorParam(Errors.PRODUCT)))
        );
    }

    private ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setProductCode(product.getProductCode());
        productDto.setPrice(product.getPrice());

        return productDto;
    }

    private void checkExistProduct(String code) {
        if (productRepository.existsByProductCode(code)) {
            throw new BadRequestException(new SysError(Errors.ERROR_PRODUCT_IS_EXIST, new ErrorParam(Errors.PRODUCT)));
        }
    }

}
