package com.buyrui.finding.ebayitems.mapper;

import java.util.Collection;

import com.buyrui.finding.ebayitems.domain.User;

public interface UserMapper {
    User findById(String id);

    Collection<User> findAll();

    void insert(String name, int age);
}
