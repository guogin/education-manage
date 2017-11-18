package mobi.dashuxia.mapper;

import java.util.Collection;
import java.util.List;

import mobi.dashuxia.domain.Customer;

public interface CustomerMapper {
    /**
     * 根据客户编号查找
     * @param id
     * @return
     */
    Customer findById(Long id);
    
    /**
     * 查出所有客户
     * @return
     */
    Collection<Customer> findAll();
    /**
     * 根据关键字搜索用户
     * @param key
     * @return
     */
    List<Customer> search(String[] keywords);
    /**
     * 更新客户信息
     * @param id 客户ID
     */
    Integer update(Customer customer);
}
