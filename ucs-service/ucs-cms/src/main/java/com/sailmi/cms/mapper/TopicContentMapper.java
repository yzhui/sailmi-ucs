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
package com.sailmi.cms.mapper;

import com.sailmi.cms.entity.TopicContent;
import com.sailmi.cms.vo.TopicContentVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 专题内容关系 Mapper 接口
 *
 * @author sailmi
 * @since 2020-12-16
 */
public interface TopicContentMapper extends BaseMapper<TopicContent> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param topicContent
	 * @return
	 */
	List<TopicContentVO> selectTopicContentPage(IPage page, TopicContentVO topicContent);

}
