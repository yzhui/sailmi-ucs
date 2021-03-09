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
package com.sailmi.desk.wrapper;

import com.sailmi.core.mp.support.BaseEntityWrapper;
import com.sailmi.core.tool.api.R;
import com.sailmi.core.tool.utils.BeanUtil;
import com.sailmi.core.tool.utils.SpringUtil;
import com.sailmi.desk.entity.Notice;
import com.sailmi.desk.vo.NoticeVO;
import com.sailmi.system.feign.IDictClient;

/**
 * Notice包装类,返回视图层所需的字段
 *
 * @author Chill
 */
public class NoticeWrapper extends BaseEntityWrapper<Notice, NoticeVO> {

	private static IDictClient dictClient;

	static {
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static NoticeWrapper build() {
		return new NoticeWrapper();
	}

	@Override
	public NoticeVO entityVO(Notice notice) {
		NoticeVO noticeVO = BeanUtil.copy(notice, NoticeVO.class);
		R<String> dict = dictClient.getValue("notice", noticeVO.getCategory());
		if (dict.isSuccess()) {
			String categoryName = dict.getData();
			noticeVO.setCategoryName(categoryName);
		}
		return noticeVO;
	}

}
