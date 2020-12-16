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

import com.sailmi.cms.entity.Content;
import com.sailmi.cms.vo.ContentVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 内容 Mapper 接口
 *
 * @author sailmi
 * @since 2020-12-16
 */
public interface ContentMapper extends BaseMapper<Content> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param content
	 * @return
	 */
	List<ContentVO> selectContentPage(IPage page, ContentVO content);

}
