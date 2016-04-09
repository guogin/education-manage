package com.buyrui.finding.ebayitems.mapper;

import java.util.List;

import com.buyrui.finding.ebayitems.domain.Category;

public interface CategoryMapper {
    Category findById(String category_id);

    List<Category> findAll();
    
    List<Category> search(String[] keywords);

    Integer insert(Category category);
    
    Category save(Category category);
    
    Integer update(Category category);
}
