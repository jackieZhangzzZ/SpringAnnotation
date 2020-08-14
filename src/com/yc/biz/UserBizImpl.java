package com.yc.biz;

import com.yc.dao.UserDao;
import com.yc.springframework.context.annotation.Resource;

//托管业务            加参数修改类的别名
public class UserBizImpl implements UserBiz {
	@Resource(name="userDaoRedisImpl")
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public UserBizImpl(UserDao userDao){
		this.userDao=userDao;
	}
	public UserBizImpl(){
		
	}

	@Override
	public void addUser() {
		userDao.addUser();
	}

	@Override
	public void deleteUser() {
		userDao.deleteUser();
	}

	@Override
	public void updateUser() {
		userDao.updateUser();
	}

	@Override
	public void findUser() {
		userDao.findUser();
	}

}
