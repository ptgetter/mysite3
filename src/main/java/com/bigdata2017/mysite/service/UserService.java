package com.bigdata2017.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bigdata2017.mysite.repository.UserDao;
import com.bigdata2017.mysite.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public void join(UserVo userVo) {
		userDao.insert(userVo);
	}
	
	public UserVo getUser(String email, String password) {
		UserVo userVo = userDao.get(email, password);
		return userVo;
	}
	
	public UserVo getUser(Long no) {
		UserVo userVo = userDao.get(no);
		return userVo;
	}
	
	public boolean modifyUser(UserVo userVo) {
		return userDao.update(userVo) == 1;
	}

	public boolean existUser(String email) {
		UserVo userVo = userDao.get(email);
		return userVo != null;
	}
	
	
}
