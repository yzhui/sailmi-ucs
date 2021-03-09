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
package com.sailmi.admin;

import com.sailmi.core.launch.AppLauncher;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import com.sailmi.core.launch.constant.LaunchConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * admin启动器
 *
 * @author Chill
 */
@EnableAdminServer
@SpringCloudApplication
public class AdminApplication {

	public static void main(String[] args) {
		AppLauncher.run(LaunchConstant.APPLICATION_ADMIN_NAME, AdminApplication.class, args);
	}

}
