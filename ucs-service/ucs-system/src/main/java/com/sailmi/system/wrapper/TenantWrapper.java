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
package com.sailmi.system.wrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sailmi.core.mp.support.BaseEntityWrapper;
import com.sailmi.core.tool.utils.BeanUtil;
import com.sailmi.system.entity.Tenant;
import com.sailmi.system.vo.TenantVo;


/**
 * 可提供的服务清单，企业可以通过服务清单包装类,返回视图层所需的字段
 *
 * @author sailmi
 * @since 2020-09-09
 */
public class TenantWrapper extends BaseEntityWrapper<Tenant, TenantVo> {

    public static TenantWrapper build() {
        return new TenantWrapper();
    }

	@Override
	public TenantVo entityVO(Tenant service) {
		TenantVo serviceVO = BeanUtil.copy(service, TenantVo.class);
		return serviceVO;
	}


}
