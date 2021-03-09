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

import com.sailmi.system.entity.UserEnterprise;
import com.sailmi.system.vo.UserEnterpriseVO;
import com.sailmi.core.mp.support.BaseEntityWrapper;
import com.sailmi.core.tool.utils.BeanUtil;

/**
 * 用户-企业关系表包装类,返回视图层所需的字段
 *
 * @author sailmi
 * @since 2020-10-16
 */
public class UserEnterpriseWrapper extends BaseEntityWrapper<UserEnterprise, UserEnterpriseVO>  {

    public static UserEnterpriseWrapper build() {
        return new UserEnterpriseWrapper();
    }

	@Override
	public UserEnterpriseVO entityVO(UserEnterprise userEnterprise) {
		UserEnterpriseVO userEnterpriseVO = BeanUtil.copy(userEnterprise, UserEnterpriseVO.class);

		return userEnterpriseVO;
	}

}
