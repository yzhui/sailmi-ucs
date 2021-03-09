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
package com.sailmi.enterprise.wrapper;

import com.sailmi.core.mp.support.BaseEntityWrapper;
import com.sailmi.core.tool.utils.BeanUtil;
import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.vo.EnterpriseVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author sailmi
 * @since 2020-09-02
 */
public class EnterpriseWrapper extends BaseEntityWrapper<Enterprise, EnterpriseVO>  {

    public static EnterpriseWrapper build() {
        return new EnterpriseWrapper();
    }

	@Override
	public EnterpriseVO entityVO(Enterprise enterprise) {
		EnterpriseVO enterpriseVO = BeanUtil.copy(enterprise, EnterpriseVO.class);

		return enterpriseVO;
	}

}
