package com.packt.cardatabase.web;

import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.Product;
import com.packt.cardatabase.domain.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @GetMapping(value="/product")
    public Iterable<Product> getProducts(){
        return repository.findAll();
    }

    @GetMapping(value="/product/{id}")
    public Optional<Product> getProduct(@PathVariable String id){
        return repository.findById(id);
    }
}
