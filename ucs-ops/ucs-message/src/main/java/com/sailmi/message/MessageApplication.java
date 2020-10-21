package com.sailmi.message;

import com.sailmi.core.launch.AppLauncher;
import com.sailmi.core.launch.constant.LaunchConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.spring4all.swagger.EnableSwagger2Doc;

@EnableScheduling
@SpringCloudApplication
@EnableFeignClients(LaunchConstant.BASE_PACKAGES)
@MapperScan(value = "com.sailmi.message.dao.mapper")
public class MessageApplication {
	public static void main(String[] args) {
		AppLauncher.run(LaunchConstant.APPLICATION_MESSAGE_NAME, MessageApplication.class, args);
	}
}
