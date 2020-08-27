package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.Category;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.CategoryApiRequest;
import com.example.study.model.network.response.CategoryApiResponse;
import com.example.study.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryApiService extends BaseService<CategoryApiRequest, CategoryApiResponse, Category> {


    @Override
    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> req) {

        CategoryApiRequest body = req.getData();

        Category category = Category.builder()
                .type(body.getType())
                .title(body.getTitle())
                .build();

        Category newCategory = baseRepository.save(category);

        return response(newCategory);
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {

        return baseRepository.findById(id)
                .map(category -> response(category))
                .orElseGet(() -> Header.ERROR("데이터가 존재하지 않음"));
    }

    @Override
    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> req) {

        CategoryApiRequest body = req.getData();

        Category category = Category.builder()
                .id(body.getId())
                .title(body.getTitle())
                .type(body.getType())
                .build();

        Category changeCategory = baseRepository.save(category);

        return response(changeCategory);
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(category -> {baseRepository.delete(category);
                return Header.OK();})
                .orElseGet(() -> Header.ERROR("데이터가 존재하지 않음"));

    }

    public Header<CategoryApiResponse> response(Category category) {
        CategoryApiResponse categoryApiResponse = CategoryApiResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .type(category.getType())
                .build();


        return Header.OK(categoryApiResponse);
    }

    @Override
    public Header<List<CategoryApiResponse>> search(Pageable pageable) {
        return null;
    }
}
