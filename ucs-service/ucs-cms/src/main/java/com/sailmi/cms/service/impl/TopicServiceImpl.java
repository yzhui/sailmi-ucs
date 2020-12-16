/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
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
package com.sailmi.cms.service.impl;

import com.sailmi.cms.entity.Topic;
import com.sailmi.cms.vo.TopicVO;
import com.sailmi.cms.mapper.TopicMapper;
import com.sailmi.cms.service.ITopicService;
import com.sailmi.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 专题表 服务实现类
 *
 * @author sailmi
 * @since 2020-12-16
 */
@Service
public class TopicServiceImpl extends BaseServiceImpl<TopicMapper, Topic> implements ITopicService {

	@Override
	public IPage<TopicVO> selectTopicPage(IPage<TopicVO> page, TopicVO topic) {
		return page.setRecords(baseMapper.selectTopicPage(page, topic));
	}

}
