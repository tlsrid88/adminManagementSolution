package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Category;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public class CategoryRepositoryTest extends StudyApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create() {
        String type     = "COMPUTER";
        String title    = "컴퓨터";
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        Category category = new Category();
        category.setType(type);
        category.setTitle(title);
        category.setCreatedAt(createdAt);
        category.setCreatedBy(createdBy);

        Category newCategory = categoryRepository.save(category);

        Assert.assertNotNull(newCategory);
        Assert.assertEquals(newCategory.getType(), type);
        Assert.assertEquals(newCategory.getTitle(), title);
    }

    @Test
    public void read() {
        String type = "COMPUTER";

        Optional<Category> category = categoryRepository.findByType(type);

        category.ifPresent(c -> {
            Assert.assertEquals(c.getType(),type);

            System.out.println(c.getTitle());
            System.out.println(c.getType());
            System.out.println(c.getCreatedAt());
        });
    }

    @Test
    public void update() {
        Optional<Category> category = categoryRepository.findById(1L);

        category.ifPresent(selectValue -> {
            selectValue.setTitle("내가원하는대로");

            categoryRepository.save(selectValue);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<Category> category= categoryRepository.findById(2L);

        Assert.assertTrue(category.isPresent());

        category.ifPresent(selectCategory -> {
            categoryRepository.delete(selectCategory);
        });

        Optional<Category> deleteCategory = categoryRepository.findById(2L);

        Assert.assertFalse(deleteCategory.isPresent());


    }


}
