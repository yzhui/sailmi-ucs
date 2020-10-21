package com.sailmi.message;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.mybatis.spring.annotation.MapperScan;
import com.sailmi.core.test.*;

@RunWith(AppSpringRunner.class)
@SpringBootTest(classes = MessageApplication.class)
@AppBootTest(appName="ucs-message",profile="test",enableLoader=true)
@MapperScan(value = "com.sailmi.message.dao.mapper")
public class SmsApplicationTests {

	//@Test
	public void contextLoads() {
	}

}
