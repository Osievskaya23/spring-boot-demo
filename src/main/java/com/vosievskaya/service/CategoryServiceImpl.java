package com.vosievskaya.service;

import com.vosievskaya.dao.CategoryDao;
import com.vosievskaya.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> getAll() {
        return categoryDao.findAll();
    }

    @Override
    public Optional<Category> getById(Long id) {
        return categoryDao.findById(id);
    }

    @Override
    public Optional<Category> create(Category category) {
        return Optional.of(categoryDao.save(category));
    }

    @Override
    public Optional<Category> update(Category category) {
        return Optional.of(categoryDao.save(category));
    }
}
