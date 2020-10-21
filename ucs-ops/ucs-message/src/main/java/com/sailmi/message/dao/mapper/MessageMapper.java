package com.sailmi.message.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.message.dao.model.Message;
import com.sailmi.message.model.vo.MessageVO;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param message
	 * @return
	 */
	List<MessageVO> selectMessagePage(IPage page, MessageVO message);
}
