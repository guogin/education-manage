package com.buyrui.finding.ebayitems.mapper;

import java.util.Collection;

import com.buyrui.finding.ebayitems.domain.Category;

public interface CategoryMapper {
    Category findById(String category_id);

    Collection<Category> findAll();
    
    Collection<Category> search(String[] keywords);

    Integer insert(Category category);
}
