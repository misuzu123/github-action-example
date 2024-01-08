package com.training.controller;

import com.training.common.constant.Constant;
import com.training.dto.ResponseJson;
import com.training.dto.product.ProductDto;
import com.training.dto.product.ProductRequest;
import com.training.service.product.ProductService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseJson<Boolean>> createNewProduct(@RequestBody ProductRequest request) {
        productService.createNewProduct(request);
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, Constant.SUCCESS));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseJson<ProductDto>> getInfoProduct(@PathVariable("id") Integer id) {
        ProductDto product = productService.getInfoProduct(id);
        return ResponseEntity.ok().body(new ResponseJson<>(product, HttpStatus.OK, Constant.SUCCESS));
    }

    @GetMapping()
    public ResponseEntity<ResponseJson<List<ProductDto>>> getListProduct() {
        List<ProductDto> products = productService.getListProduct();
        return ResponseEntity.ok().body(new ResponseJson<>(products, HttpStatus.OK, Constant.SUCCESS));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseJson<ProductDto>> updateProduct(@PathVariable("id") Integer id, @RequestBody ProductRequest request) {
        ProductDto product = productService.updateProduct(id, request);
        return ResponseEntity.ok().body(new ResponseJson<>(product, HttpStatus.OK, Constant.SUCCESS));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseJson<Boolean>> deleteProduct(@RequestBody List<Integer> ids) {
        productService.deleteProduct(ids);
        return ResponseEntity.ok().body(new ResponseJson<>(Boolean.TRUE, HttpStatus.OK, Constant.SUCCESS));
    }

}
