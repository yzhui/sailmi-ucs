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
package com.sailmi.cms.wrapper;

import lombok.AllArgsConstructor;
import com.sailmi.core.mp.support.BaseEntityWrapper;
import com.sailmi.core.tool.utils.BeanUtil;
import com.sailmi.cms.entity.Content;
import com.sailmi.cms.vo.ContentVO;

/**
 * 内容包装类,返回视图层所需的字段
 *
 * @author sailmi
 * @since 2020-12-16
 */
public class ContentWrapper extends BaseEntityWrapper<Content, ContentVO>  {

    public static ContentWrapper build() {
        return new ContentWrapper();
    }

	@Override
	public ContentVO entityVO(Content content) {
		ContentVO contentVO = BeanUtil.copy(content, ContentVO.class);

		return contentVO;
	}

}
