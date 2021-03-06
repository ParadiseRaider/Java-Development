package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.ProductRepository;
import ru.geekbrains.repo.ProductSpecification;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return repository.findAll();
    }

    public Page<Product> filterByCost(BigDecimal minCost, BigDecimal maxCost, String names, Pageable pageable) {
        Specification<Product> specification = ProductSpecification.trueLiteral();

        if (minCost != null) {
            specification = specification.and(ProductSpecification.costGreaterThanOrEqual(minCost));
        }
        if (maxCost != null) {
            specification = specification.and(ProductSpecification.costLessThanOrEqual(maxCost));
        }
        if (names != null) {
            specification = specification.and(ProductSpecification.findByProductName(names));
        }
        return repository.findAll(specification, pageable);
    }

    @Transactional
    public void save(Product product) {
        repository.save(product);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
