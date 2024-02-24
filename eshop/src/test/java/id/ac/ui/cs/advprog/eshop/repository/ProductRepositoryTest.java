package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setup() {
    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditExisting() {
        Product product = new Product();
        product.setProductId("eb558e9f-7770-460e-8860-71af6af63bd6");
        product.setProductName("The Moral Imperative");
        product.setProductQuantity(1);
        productRepository.create(product);

        Product newProduct = productRepository.findProductByProductId(product.getProductId());
        newProduct.setProductName("Misericordia");
        newProduct.setProductQuantity(0);

        Product finalProduct = productRepository.edit(newProduct);
        assertEquals(newProduct.getProductId(), finalProduct.getProductId());
        assertEquals(newProduct.getProductName(), finalProduct.getProductName());
        assertEquals(newProduct.getProductQuantity(), finalProduct.getProductQuantity());
    }

    @Test
    void testEditNonExisting() {
        Product nonExistingProduct = new Product();
        nonExistingProduct.setProductId("eb558e9f-6660-460e-8860-71af6af63bd6");
        nonExistingProduct.setProductName("et Misera");
        nonExistingProduct.setProductQuantity(0);

        assertThrows(IllegalArgumentException.class,
                () -> productRepository.edit(nonExistingProduct));
    }

    @Test
    void testDeleteExisting() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-990-71af6af63bd6");
        product.setProductName("The Old Prerogative");
        product.setProductQuantity(100);
        productRepository.create(product);

        productRepository.delete(product);
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteExistingMoreThanOne() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-990-71af6af63bd6");
        product1.setProductName("The Old Prerogative");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("eb558e9f-1c39-460e-660-71af6af63bd6");
        product2.setProductName("The New Prerogative");
        product2.setProductQuantity(100);
        productRepository.create(product2);

        productRepository.delete(product1);
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertEquals(product2.getProductName(), savedProduct.getProductName());
        assertEquals(product2.getProductQuantity(), savedProduct.getProductQuantity());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteNonExisting(){
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-440-71af6af63bd6");
        product.setProductName("The Old Prerogative");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product fakeProduct = new Product();
        fakeProduct.setProductId("eb558e9f-1c39-460e-550-71af6af63bd6");
        fakeProduct.setProductName("The Old Prerogative");
        fakeProduct.setProductQuantity(100);

        productRepository.delete(fakeProduct);
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
    }
}
