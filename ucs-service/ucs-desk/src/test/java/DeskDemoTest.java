import org.junit.Test;
import org.junit.runner.RunWith;
import com.sailmi.core.test.AppBootTest;
import com.sailmi.core.test.AppSpringRunner;
import com.sailmi.desk.DeskApplication;
import com.sailmi.desk.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * sailmi单元测试
 *
 * @author Chill
 */
@RunWith(AppSpringRunner.class)
@SpringBootTest(classes = DeskApplication.class)
@AppBootTest(appName = "ucs-desk", profile = "test", enableLoader = true)
public class DeskDemoTest {

	@Autowired
	private INoticeService noticeService;

	@Test
	public void contextLoads() {
		int count = noticeService.count();
		System.out.println("notice数量：[" + count + "] 个");
	}

}
