package mobi.dashuxia.web.api;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import mobi.dashuxia.domain.Category;
import mobi.dashuxia.service.CategoryService;

@RestController
public class CategoryController extends BaseController {

    @Autowired
    CategoryService categoryService;

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/api/category",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageInfo> findAll(
            @RequestParam(required=false,value="page",defaultValue="1") Integer page,
            @RequestParam(required=false,value="size",defaultValue="10") Integer size) throws Exception {
        Future<PageInfo> future = categoryService.findAll(page, size);
        PageInfo pageInfo = future.get(10000,
                TimeUnit.MILLISECONDS);
        return new ResponseEntity<PageInfo>(pageInfo,
                HttpStatus.OK);
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/api/category/search",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageInfo> search(
            @RequestParam("s") String keywords,
            @RequestParam(required=false,value="page",defaultValue="1") Integer page,
            @RequestParam(required=false,value="size",defaultValue="10") Integer size) throws Exception {
        Future<PageInfo> future = categoryService.search(keywords,page,size);
        PageInfo pageInfo = future.get(10000,
                TimeUnit.MILLISECONDS);
        return new ResponseEntity<PageInfo>(pageInfo,
                HttpStatus.OK);
    }

    @RequestMapping(value = { "/api/category/{category_id}" },
                    method = { RequestMethod.GET })
    @ResponseBody
    public Category findOne(@PathVariable("category_id") String category_id)
            throws Exception {
        Future<Category> res = categoryService.findOne(category_id);
        return res.get(1000, TimeUnit.MILLISECONDS);
    }

    @RequestMapping(value = { "/api/category/update" },
                    method = { RequestMethod.POST })
    public Integer update(@RequestBody Category category) throws Exception {
        Future<Integer> res = categoryService.update(category);
        return res.get(1000, TimeUnit.MILLISECONDS);
    }

    @RequestMapping(value = "/api/category/create",
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> register(@RequestBody Category category)
            throws Exception {
        Integer id = categoryService.addCategory(category).get();
        return new ResponseEntity<Integer>(id, HttpStatus.CREATED);
    }
}
