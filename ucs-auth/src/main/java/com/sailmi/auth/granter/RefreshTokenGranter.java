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
package com.sailmi.auth.granter;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import com.sailmi.core.launch.constant.TokenConstant;
import com.sailmi.core.secure.utils.SecureUtil;
import com.sailmi.core.tool.api.R;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.system.user.entity.UserInfo;
import com.sailmi.system.user.feign.IUserClient;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * RefreshTokenGranter
 *
 * @author Chill
 */
@Component
@AllArgsConstructor
public class RefreshTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "refresh_token";

	private IUserClient userClient;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		String tenantId = tokenParameter.getArgs().getStr("tenantId");
		String grantType = tokenParameter.getArgs().getStr("grantType");
		String refreshToken = tokenParameter.getArgs().getStr("refreshToken");
		UserInfo userInfo = null;
		if (Func.isNoneBlank(grantType, refreshToken) && grantType.equals(TokenConstant.REFRESH_TOKEN)) {
			Claims claims = SecureUtil.parseJWT(refreshToken);
			String tokenType = Func.toStr(Objects.requireNonNull(claims).get(TokenConstant.TOKEN_TYPE));
			if (tokenType.equals(TokenConstant.REFRESH_TOKEN)) {
				R<UserInfo> result = userClient.userInfo(Func.toLong(claims.get(TokenConstant.USER_ID)));
				userInfo = result.isSuccess() ? result.getData() : null;
			}
		}
		return userInfo;
	}
}
