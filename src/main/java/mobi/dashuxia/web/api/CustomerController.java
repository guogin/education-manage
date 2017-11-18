package mobi.dashuxia.web.api;

import java.util.Collection;
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

import mobi.dashuxia.domain.Customer;
import mobi.dashuxia.service.CustomerService;

@RestController
@RequestMapping("/")
public class CustomerController extends BaseController {

    @Autowired
    CustomerService customerService;

    @RequestMapping("/public/")
    public String index() {
        return "public/index";
    }

    @RequestMapping(value = "/api/v1/customers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Customer>> findAll() throws Exception {
        Future<Collection<Customer>> future = customerService.findAll();
        Collection<Customer> users = future.get(10000, TimeUnit.MILLISECONDS);
        return new ResponseEntity<Collection<Customer>>(users, HttpStatus.OK);
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/api/v1/customers/search",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageInfo> search(@RequestParam("s") String keywords,
        @RequestParam(required = false,
            value = "page",
            defaultValue = "1") Integer page,
        @RequestParam(required = false,
            value = "size",
            defaultValue = "10") Integer size) throws Exception {
        Future<PageInfo> future = customerService.search(keywords, page, size);
        PageInfo pageInfo = future.get(10000, TimeUnit.MILLISECONDS);
        return new ResponseEntity<PageInfo>(pageInfo, HttpStatus.OK);
    }

    @RequestMapping(value = { "/api/v1/customers/{id}" },
        method = { RequestMethod.GET })
    @ResponseBody
    public Customer findOne(@PathVariable("id") Long id) throws Exception {
        Future<Customer> res = customerService.findOne(id);
        return res.get(1000, TimeUnit.MILLISECONDS);
    }

    @RequestMapping(value = { "/api/v1/customers/{id}" },
        method = { RequestMethod.PUT })
    @ResponseBody
    public Integer activate(@PathVariable("id") @NotNull @NotEmpty Long id,
        @RequestBody Customer customer) throws Exception {
        if(!id.equals(customer.getId())) {
            return -1;
        }
        Future<Integer> res = customerService.update(customer);
        return res.get(1000, TimeUnit.MILLISECONDS);
    }
}
