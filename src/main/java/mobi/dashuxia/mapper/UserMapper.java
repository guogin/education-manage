package mobi.dashuxia.mapper;

import java.util.Collection;

import mobi.dashuxia.domain.User;

public interface UserMapper {
    User findById(String id);

    Collection<User> findAll();

    void insert(String name, int age);
}
