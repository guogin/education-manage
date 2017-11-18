package mobi.dashuxia.web.api;

import java.util.Collection;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mobi.dashuxia.domain.User;
import mobi.dashuxia.service.UserService;

@RestController
@RequestMapping("/")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @RequestMapping("/public/")
    public String index() {
        return "public/index";
    }

    @RequestMapping(value = "/api/user",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<User>> findAll() throws Exception {
        Future<Collection<User>> future = userService.findAll();
        Collection<User> users = future.get(10000, TimeUnit.MILLISECONDS);
        return new ResponseEntity<Collection<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = { "/api/user/{id}" },
                    method = { RequestMethod.GET })
    @ResponseBody
    public User findOne(@PathVariable("id") String id) throws Exception {
        Future<User> res = userService.findOne(id);
        return res.get(1000, TimeUnit.MILLISECONDS);
    }

    @RequestMapping(value = { "/api/user" },
                    method = { RequestMethod.GET })
    @ResponseBody
    public String register(@RequestParam("name") String name,
            @RequestParam("age") Integer age) {
        userService.addUser(name, age);
        return "success";
    }

    @RequestMapping("/velocity")
    public String velocity(Model model) {
        model.addAttribute("testparam", "Test Dev Tools");
        return "index";
    }
}
