package com.sailmi.message.service.task;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import com.sailmi.message.core.dao.entity.Message;
import com.sailmi.message.core.service.ChannelSMSServices;
import com.sailmi.message.core.service.IBatchQueryable;
import com.sailmi.message.core.service.IChannelService;
import com.sailmi.message.core.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QuerySendTask {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IMessageService messageService;

	@Autowired
	private ChannelSMSServices channelSMSServices;

	@Scheduled(fixedDelay = 5 * 60 * 1000)
	public void querySend() {
		logger.debug("开始查询短信发送结果");
		for (IChannelService channelSMSService : channelSMSServices) {
			if (channelSMSService instanceof IBatchQueryable) {
				((IBatchQueryable) channelSMSService).batchQueryAndUpdateSendStatus();
			}else {
				Date yesterday = Date.from(Instant.now().minus(Duration.ofDays(1)));
				List<Message> list = messageService.querySendingMessages(yesterday, channelSMSService.getChannel());
				list.forEach(message -> messageService.queryAndUpdateSendStatus(message, channelSMSService));
			}
		}
	}

}
