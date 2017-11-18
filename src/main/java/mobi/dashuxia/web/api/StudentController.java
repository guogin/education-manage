package mobi.dashuxia.web.api;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
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

import mobi.dashuxia.domain.Student;
import mobi.dashuxia.service.StudentService;

@RestController
public class StudentController extends BaseController {

    @Autowired
    StudentService studentService;

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/api/v1/students",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageInfo> findAll(@RequestParam(required = false,
        value = "page",
        defaultValue = "1") Integer page, @RequestParam(required = false,
            value = "size",
            defaultValue = "10") Integer size) throws Exception {
        Future<PageInfo> future = studentService.findAll(page, size);
        PageInfo pageInfo = future.get(10000, TimeUnit.MILLISECONDS);
        return new ResponseEntity<PageInfo>(pageInfo, HttpStatus.OK);
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/api/v1/students/search",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageInfo> search(@RequestParam("s") String keywords,
        @RequestParam(required = false,
            value = "page",
            defaultValue = "1") Integer page,
        @RequestParam(required = false,
            value = "size",
            defaultValue = "10") Integer size) throws Exception {
        Future<PageInfo> future = studentService.search(keywords, page, size);
        PageInfo pageInfo = future.get(10000, TimeUnit.MILLISECONDS);
        return new ResponseEntity<PageInfo>(pageInfo, HttpStatus.OK);
    }

    @RequestMapping(value = { "/api/v1/students/{id}" },
        method = { RequestMethod.GET })
    @ResponseBody
    public Student findOne(@PathVariable("id") String id) throws Exception {
        Future<Student> res = studentService.findOne(id);
        return res.get(1000, TimeUnit.MILLISECONDS);
    }

    @RequestMapping(value = { "/api/v1/students/{id}" },
        method = { RequestMethod.PUT })
    public Integer update(@PathVariable("id") @NotEmpty @NotNull String id,
        @RequestBody Student student) throws Exception {
        if (!id.equals(student.getId())) {
            return -1;
        }
        Future<Integer> res = studentService.update(student);
        return res.get(1000, TimeUnit.MILLISECONDS);
    }
}
