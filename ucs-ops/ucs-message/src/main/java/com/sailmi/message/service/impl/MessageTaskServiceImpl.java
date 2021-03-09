/**
 * Copyright (c) 2018-2028.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sailmi.message.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.core.mp.base.BaseServiceImpl;
import com.sailmi.core.message.dao.entity.MessageTask;
import com.sailmi.core.message.dao.mapper.MessageTaskMapper;
import com.sailmi.core.message.model.vo.MessageTaskVO;
import com.sailmi.core.message.service.IMessageTaskService;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author sailmi
 * @since 2020-07-17
 */
@Service
public class MessageTaskServiceImpl extends BaseServiceImpl<MessageTaskMapper, MessageTask> implements IMessageTaskService {
	@Override
	public IPage<MessageTaskVO> selectMessageTaskPage(IPage<MessageTaskVO> page, MessageTaskVO batchMessage) {
		return page.setRecords(baseMapper.selectMessageTaskPage(page, batchMessage));
	}

}
