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
package com.sailmi.resource;

import com.sailmi.core.launch.AppLauncher;
import com.sailmi.core.launch.constant.LaunchConstant;
import com.sailmi.core.oss.props.OssProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

/**
 * 资源启动器
 *
 * @author Chill
 */
@EnableSwagger2
@SpringCloudApplication
@EnableFeignClients(LaunchConstant.BASE_PACKAGES)
@ComponentScan({"com.sailmi.resource","com.sailmi.core.oss.provider.mongodb","com.sailmi.core.tool.context"})
@EnableMongoRepositories
public class ResourceApplication {
	//创建application context
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ResourceApplication.class);
	}

	public static void main(String[] args) {
		AppLauncher.run(LaunchConstant.APPLICATION_RESOURCE_NAME, ResourceApplication.class, args);
	}

}

