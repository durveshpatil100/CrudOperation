package com.cybage.crud.service;

import com.cybage.crud.Exception.ResourceNotFoundException;
import com.cybage.crud.entity.Product;
import com.cybage.crud.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);

    }

    public Product getProduct(Integer id)  {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: "+id));
    }

    public Product updateProduct(Integer id, Product updatedProduct) throws Exception  {
    	if(updatedProduct ==null || updatedProduct.getId() ==null){
            throw new Exception("product or Id must not be null");
        }
        Optional<Product> optionalProduct = productRepository.findById(updatedProduct.getId());
        
        if(!optionalProduct.isPresent())
        {
            throw new Exception("Product with ID: "+updatedProduct.getId()+ " does not exists.");
        }
        
        Product existingProduct = optionalProduct.get();
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}

