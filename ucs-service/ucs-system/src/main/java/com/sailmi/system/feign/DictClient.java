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
package com.sailmi.system.feign;


import lombok.AllArgsConstructor;
import com.sailmi.core.tool.api.R;
import com.sailmi.system.entity.Dict;
import com.sailmi.system.service.IDictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * 字典服务Feign实现类
 *
 * @author Chill
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class DictClient implements IDictClient {

	IDictService service;

	@Override
	@GetMapping(API_PREFIX + "/getValue")
	public R<String> getValue(String code, Integer dictKey) {
		return R.data(service.getValue(code, dictKey));
	}

	@Override
	@GetMapping(API_PREFIX + "/getList")
	public R<List<Dict>> getList(String code) {
		return R.data(service.getList(code));
	}

}
