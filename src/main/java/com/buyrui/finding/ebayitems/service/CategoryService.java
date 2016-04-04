package com.buyrui.finding.ebayitems.service;

import java.util.Collection;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.buyrui.finding.ebayitems.domain.Category;
import com.buyrui.finding.ebayitems.mapper.CategoryMapper;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CategoryService {
    private Logger         logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CategoryMapper categoryMapper;

    @Async
    public Future<Void> execute() {
        return new AsyncResult<Void>(null);
    }

    @Async
    public Future<Category> findOne(String category_id) {
        logger.info("> findOne");
        return new AsyncResult<Category>(categoryMapper.findById(category_id));
    }

    @Async
    public Future<Collection<Category>> findAll() {
        logger.info("> Find All");
        return new AsyncResult<Collection<Category>>(categoryMapper.findAll());
    }

    @Async
    public Future<Collection<Category>> search(String keys) {
        logger.info("> Search...");
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(keys);
        String[] keywords = m.replaceAll("").trim().split(" ");
        return new AsyncResult<Collection<Category>>(categoryMapper.search(
                keywords));
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Future<Integer> addCategory(Category category) {
        Integer count = categoryMapper.insert(category);
        if (count == 1) {
            logger.info("create one category success.");
            return new AsyncResult<Integer>(category.getId());
        } else {
            logger.info("create one category failure");
            return new AsyncResult<Integer>(-1);
        }
    }
    
    @Async
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Future<Category> update(Category category) {
        Category categoryPersisted = categoryMapper.findById(category.getCategory_id());

        if (categoryPersisted == null) {
            // Cannot update Category than hasn't been persisted.
            throw new NoResultException("Can't find the entity with id="
                            + category.getCategory_id());
        }

        Category updateCategory = categoryMapper.save(category);
        return new AsyncResult<Category>(updateCategory);
    }
}
