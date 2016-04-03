package com.buyrui.finding.ebayitems.service;

import java.util.Collection;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.buyrui.finding.ebayitems.domain.Category;
import com.buyrui.finding.ebayitems.mapper.CategoryMapper;

@Service
public class CategoryService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private CategoryMapper categoryMapper;
    
    @Async
    public Future<Void> execute() {
        return new AsyncResult<Void>(null);
    }
    
    @Async
    public Future<Category> findOne( String category_id ) {
        logger.info("> findOne");
        return new AsyncResult<Category>(categoryMapper.findById(category_id));
    }
    
    @Async
    public Future<Collection<Category>> findAll(){
        logger.info("> Find All");
        return new AsyncResult<Collection<Category>>(categoryMapper.findAll());
    }
    
    
    @Async
    public Future<Void> addCategory(Category category) {
        categoryMapper.insert(category);
        return new AsyncResult<Void>(null);
    }
}
