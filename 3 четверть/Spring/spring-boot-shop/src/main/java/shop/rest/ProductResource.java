package shop.rest;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.persist.entity.Product;
import shop.service.ProductService;

import java.util.List;

@RequestMapping("/api/v1/product")
@RestController
public class ProductResource {
    private final ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping(path = "/{id}/id")
    public Product findById(@PathVariable("id") long id) throws NotFound {
        return productService.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public void createProduct(@RequestBody Product product) {
        if (product.getId()!=null) {
            throw new IllegalArgumentException("Id found in the create request!");
        }
        productService.save(product);
    }

    @PutMapping
    public void updateProduct(@RequestBody Product product) {
        productService.save(product);
    }

    @DeleteMapping(path = "/{id}/id")
    public void deleteProduct(@PathVariable("id") long id) {
        productService.delete(id);
    }

    @ExceptionHandler
    public ResponseEntity<MessageError> notFoundExceptionHandler(NotFoundException exception) {
        return new ResponseEntity<>(new MessageError("Not Found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        return new ResponseEntity<>(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}
