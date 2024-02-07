package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        productData.add(product);
        return product;
    }

    public Product findProductByProductId(String productId) {
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        throw new IllegalArgumentException("Bad productId");
    }

    public Product edit(Product product) {
        Product productToBeEdited = findProductByProductId(product.getProductId());
        int productToBeEditedIndex = productData.indexOf(productToBeEdited);
        productData.set(productToBeEditedIndex, product);
        return product;
    }
    public Iterator<Product> findAll() {
        return productData.iterator();
    }
}
