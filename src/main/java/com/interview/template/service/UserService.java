package com.interview.template.service;

import com.interview.template.config.UserBlockDataConfig;
import com.interview.template.dao.UserDao;
import com.interview.template.exceptions.UserNotFoundException;
import com.interview.template.model.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.security.auth.message.callback.PasswordValidationCallback;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserService {

	private final UserDao userDao;
	private final UserBlockDataConfig userBlockDataConfig;

	public List<UserEntity> getAllUsers() {
		return userDao.findAll();
	}

	public UserEntity getUser(long id) throws UserNotFoundException {
		return userDao.findOrDie(id);
	}

	public void checkUserExists(long id) throws UserNotFoundException {
		userDao.checkExists(id);
	}
	
	//create user
	public UserEntity createUser(UserEntity userEntity) throws Exception
	{
		if(validateUserInfo(userEntity)) {
			try {
				return userDao.create(userEntity);
			} catch (ConstraintViolationException e) {
				throw new ConstraintViolationException("Username/email already existed!!", null, null);
			}
		}
		return userEntity;
	}
	
	
	//validation before creating user
	public boolean validateUserInfo(UserEntity userEntity) throws Exception 
	{
		if(Strings.isBlank(userEntity.getPassword()))
		{
			throw new UserNotFoundException("Password Should not be Empty or Blank");
		}
		
		if (userBlockDataConfig.getUsernameBlackSet().contains(userEntity.getUsername().toLowerCase())) {
			throw new IllegalAccessException(
					"User  '" + userEntity.getUsername() + "' is not allowed to create since he is in block list.");
		}
		return true;
	}
	
	//delete user
	public void deleteById(long id) throws UserNotFoundException
	{
		userDao.delete(id);
	}
	
	//getMatchedList
	public List<UserEntity> getMatchedUserList(String enterUserName)
	{
		try {
		return userDao.getMatchedUserList(enterUserName);
		}catch(Exception e)
		{
			throw new RuntimeException("Exception While Fetching the Matched User List: "+enterUserName);
		}
	}


}
