package org.litespring.beans.factory.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.exception.BeanCreationException;
import org.litespring.beans.exception.BeanDefineStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.support.GenericBeanDefinition;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory {

	public static final String ID_ATTRIBUTE="id";
	public static final String CLASS_ATTRIBUTE="class";
	
	private final Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
	
	public DefaultBeanFactory(String fileConfigUlr) {
		this.loanBeanDefine(fileConfigUlr);//加载
		
	}

	private void loanBeanDefine(String fileConfigUlr) {
		//读取配置文件
		InputStream is = null;
		try {
			ClassLoader classLoader = this.getClass().getClassLoader();
			is = classLoader.getResourceAsStream(fileConfigUlr);
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			
			Element root = doc.getRootElement();
			Iterator<Element> iter  = root.elementIterator();
			while(iter.hasNext()) {
				Element ele = iter.next();
				String beanId = ele.attributeValue(ID_ATTRIBUTE);
				String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
				BeanDefinition bd = new GenericBeanDefinition(beanId,beanClassName);
				beanDefinitionMap.put(beanId, bd);
			}
			
		} catch (DocumentException e) {
			throw new BeanDefineStoreException("BeanDefineStoreException");
		}finally {
			if(is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	

	public BeanDefinition getBeanDefinition(String beanId) {
		return  beanDefinitionMap.get(beanId);
	}

	public Object getBean(String beanId) {
		BeanDefinition bd =  beanDefinitionMap.get(beanId);
		if(bd==null) {
			throw new BeanCreationException("no this bean");
		}
		String beanClassName = bd.getBeanClassName();
		try {
			Class<?> cl = Class.forName(beanClassName);
			return cl.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException("create bean for "+beanClassName+" exception") ;
		}
	}

}
