package com.echo.controller;

import com.echo.Service.ProductService;
import com.echo.models.CategoryEnum;
import com.echo.models.Product;
import com.echo.models.ProductDTO;
import com.echo.models.ProductStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;


    @PostMapping("/product/add")
    public ResponseEntity<Product> addProductToCatalogHandler(@RequestHeader("token") String token,
                                                              @Valid @RequestBody Product product) throws LoginException {

        Product product1 = productService.addProductToCatalog(token, product);
        return new ResponseEntity<Product>(product1, HttpStatus.ACCEPTED);

    }

    @GetMapping("/product/seller/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProductsOfSellerHandler(@PathVariable("id") Integer id) {

        List<ProductDTO> list = productService.getAllProductsOfSeller(id);

        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);
    }


    @GetMapping("/products/{catenum}")
    public ResponseEntity<List<ProductDTO>> getAllProductsInCategory(@PathVariable("catenum") String catenum) {
        CategoryEnum ce = CategoryEnum.valueOf(catenum.toUpperCase());
        List<ProductDTO> list = productService.getProductsOfCategory(ce);
        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);

    }

    @GetMapping("/products/status/{status}")
    public ResponseEntity<List<ProductDTO>> getProductsWithStatusHandler(@PathVariable("status") String status) {

        ProductStatus ps = ProductStatus.valueOf(status.toUpperCase());
        List<ProductDTO> list = productService.getProductsOfStatus(ps);

        return new ResponseEntity<List<ProductDTO>>(list, HttpStatus.OK);

    }


    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateQuantityOfProduct(@PathVariable("id") Integer id,@RequestBody ProductDTO prodDto){

        Product prod = productService.updateProductQuantityWithId(id, prodDto);

        return new ResponseEntity<Product>(prod,HttpStatus.ACCEPTED);
    }
}
