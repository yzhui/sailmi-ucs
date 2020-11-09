package com.sailmi.resource;

import com.sailmi.core.oss.props.OssProperties;
import com.sailmi.core.oss.provider.OssProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class ResourceAutoConfigration {

	@Bean(name = "ossProperties")
	OssProperties ossProperties(){
		return new OssProperties();
	}
	/**
	 * 初始化 OssProvider
	 */

	@Bean(name = "ossProvider")
	public OssProvider ossProvider() {
		System.out.println(ossProperties().getProvider());
		return new OssProvider(ossProperties());
	}

}
