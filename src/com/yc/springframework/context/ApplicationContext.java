package com.yc.springframework.context;

public interface ApplicationContext {
	public Object getBean(String beanId);
	
	public Object getBean(Class clz);
}
