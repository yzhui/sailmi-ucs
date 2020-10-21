package com.sailmi.message.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.message.dao.model.BatchMessage;
import com.sailmi.message.model.vo.BatchMessageVO;


import java.util.List;

public interface BatchMessageMapper extends BaseMapper<BatchMessage> {
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param batchMessage
	 * @return
	 */
	List<BatchMessageVO> selectBatchMessagePage(IPage page, BatchMessageVO batchMessage);
}
