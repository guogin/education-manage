package com.buyrui.finding.ebayitems.web.api;

import java.util.Collection;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.buyrui.finding.ebayitems.domain.Category;
import com.buyrui.finding.ebayitems.service.CategoryService;

@RestController
public class CategoryController extends BaseController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/category",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Category>> findAll() throws Exception {
        Future<Collection<Category>> future = categoryService.findAll();
        Collection<Category> categories = future.get(10000,
                TimeUnit.MILLISECONDS);
        return new ResponseEntity<Collection<Category>>(categories,
                HttpStatus.OK);
    }

    @RequestMapping(value = { "/category/{category_id}" },
                    method = { RequestMethod.GET })
    @ResponseBody
    public Category findOne(@PathVariable("category_id") String category_id) throws Exception {
        Future<Category> res = categoryService.findOne(category_id);
        return res.get(1000, TimeUnit.MILLISECONDS);
    }
}
