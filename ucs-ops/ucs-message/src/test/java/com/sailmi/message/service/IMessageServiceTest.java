package com.sailmi.message.service;

import com.sailmi.message.core.dao.mapper.MessageMapper;
import com.sailmi.message.core.dao.entity.Message;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IMessageServiceTest {

	@Autowired
	private MessageMapper messageMapper;

	@Test
	public void testQueryAndUpdateSendStatus() {
		Message message =messageMapper.selectById(244);
		//messageService.queryAndUpdateSendStatus(message);
		Assert.assertNotNull(message);
	}
}
