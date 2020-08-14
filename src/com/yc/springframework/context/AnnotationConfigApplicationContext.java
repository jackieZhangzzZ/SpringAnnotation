package com.yc.springframework.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.yc.springframework.context.annotation.Bean;
import com.yc.springframework.context.annotation.Resource;

public class AnnotationConfigApplicationContext implements ApplicationContext{

	private Map<String,Object> beans = new HashMap<String,Object>();
	
	@Override
	public Object getBean(String beanId) {
		return beans.get(beanId);
	}

	@Override
	public Object getBean(Class clz) {
		Collection<Object> collection = beans.values();
		Iterator<Object> its = collection.iterator();
		while(its.hasNext()){
			Object obj = its.next();
			if(obj.getClass().getName().equals(clz.getName())){
				return obj;
			}
		}
		return null;
	}
	
	public AnnotationConfigApplicationContext(Class... clz){
		if(clz==null){
			return;
		}
		for(Class c : clz){
			register(c);
		}
	}

	private void register(Class c){  //读取  AppConfigure中的@Bean方法激活方法取出返回值
		try {
			Method[] ms = c.getMethods();
			Object obj = c.newInstance();
			
			for(Method m : ms){
				Annotation[] ans = m.getAnnotations();
				for(Annotation a : ans){
					if(a instanceof Bean){
						Object o = m.invoke(obj,m.getParameters() );
						String beanId = m.getName();
						beans.put(beanId, o);
					}
				}
			}
			//将所有的 @Bean对应方法中创建的对象托管到Spring中
			
			//将上面要托管的多项中有@resource的属性进行注入
			Collection<Object> collections = beans.values();
			Iterator<Object> ites = collections.iterator();
			while(ites.hasNext()){
				Object managedBean = ites.next();
				Field[] fs = managedBean.getClass().getDeclaredFields();
				for(Field f : fs){
					Annotation[] fieldAns = f.getAnnotations();
					for(Annotation a : fieldAns){
						if(a instanceof Resource){
							String beanId = ((Resource) a).name();
							if(beanId.equals("")){
								beanId = f.getName();
							}
							Object beanObject = beans.get(beanId);
							f.setAccessible(true);
							f.set(managedBean, beanObject);
							
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	};
	
}
