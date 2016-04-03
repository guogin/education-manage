package com.buyrui.finding.ebayitems.service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.buyrui.finding.ebayitems.domain.User;
import com.buyrui.finding.ebayitems.mapper.UserMapper;

@Service
public class UserService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserMapper userMapper;


	@Async
	public Future<Void> execute() {
		return new AsyncResult<Void>(null);
	}
	
	@Async
	public Future<Collection<User>> findAll(){
	    logger.info("> Find All");
	    return new AsyncResult<Collection<User>>(userMapper.findAll());
	}
	@Async
	public Future<User> findOne( String id ) {
	    logger.info("> findOne");
		return new AsyncResult<User>(userMapper.findById(id));
	}


	@Async
	public Future<Void> addUser( String name, Integer age ) {
		userMapper.insert(StringUtils.defaultString(name, "testname"),age);
		return new AsyncResult<Void>(null);
	}
}
