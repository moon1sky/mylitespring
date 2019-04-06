package org.litespring.test.v1;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.exception.BeanCreationException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;

public class BeanFactoryTest {

	@Test
	public void testGetBean() {
		BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
		BeanDefinition bd = factory.getBeanDefinition("petStore");

		assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());
			
		PetStoreService service=(PetStoreService)factory.getBean("petStore");
		
		Assert.assertNotNull(service);
		
	}
	
	
	@Test
	public void testInvalidBean() {
		BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
		try {
			factory.getBean("xx");
		}catch(BeanCreationException ex) {
//			System.out.println("BeanCreationException");
			return;
		}
		
		Assert.fail("BeanCreationException throw");
		
	}
	
}
