package mobi.dashuxia.service;

import java.util.Collection;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import mobi.dashuxia.domain.Customer;
import mobi.dashuxia.mapper.CustomerMapper;

@Service
public class CustomerService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CustomerMapper customerMapper;


	@Async
	public Future<Void> execute() {
		return new AsyncResult<Void>(null);
	}
	
	@Async
	public Future<Collection<Customer>> findAll(){
	    logger.info("> Find All");
	    return new AsyncResult<Collection<Customer>>(customerMapper.findAll());
	}
	
    @SuppressWarnings("rawtypes")
    @Async
    public Future<PageInfo> search(String keys, Integer page, Integer size) {
        logger.info("> Search...");
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(keys);
        String[] keywords = m.replaceAll("").trim().split(" ");
        PageHelper.startPage(page, size);
        PageInfo<Customer> pageInfo = new PageInfo<Customer>(customerMapper.search(
                keywords));
        return new AsyncResult<PageInfo>(pageInfo);
    }

    
	@Async
	public Future<Customer> findOne( Long id ) {
	    logger.info("> find one customer: ",  id);
		return new AsyncResult<Customer>(customerMapper.findById(id));
	}
	
	@Async
	public Future<Integer> update(Customer customer) {
	    Long customerId = customer.getId();
	    logger.info("> invoke activate method {}", customerId);
	    
	    Customer foundOne = customerMapper.findById(customerId);
	    if (null == foundOne) {
	        logger.info("> can not find customer {}", customerId);
	        return new AsyncResult<Integer>(-1);
        }
	    
	    if (!foundOne.isActivated()) {
	        logger.info("> activate customer {}", customerId);
	        return new AsyncResult<Integer>(customerMapper.update(customer));
        } else {
            logger.info("> customer {} is actviated.", customerId);
            return new AsyncResult<Integer>(-1);
        }
	}
}
