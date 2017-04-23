package cn.threewaters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import cn.threewaters.service.DataService;
import cn.threewaters.service.GrainCodeService;
import cn.threewaters.service.StoreHouseService;
import cn.threewaters.service.TestService;
import cn.threewaters.service.TypeCodeService;

@ConfigurationProperties(prefix = "myini")
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);

	@Autowired
	private StoreHouseService storeHouseService;

	@Autowired
	private TypeCodeService typeCodeService;

	@Autowired
	private GrainCodeService grainCodeService;

	@Autowired
	private TestService testService;

	@Autowired
	private DataService dataService;

	private String lkbh;

	public String getLkbh() {
		return lkbh;
	}

	public void setLkbh(String lkbh) {
		this.lkbh = lkbh;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		logger.info("=======================开始进行数据库连接测试=====================");
		boolean checkDB = testService.checkDB();
		if (checkDB) {
			logger.info("=======================数据库连接测试通过=====================");
		} else {
			logger.info("=======================数据库连接测试未通过=====================");
		}
		if (checkDB) {
			logger.info("");
			logger.info("");
			logger.info("============================================");
			logger.info("===================请注意===================");
			logger.info("============================================");
			logger.info("20秒后开始进行数据转换，转换粮库编号：" + lkbh);
			logger.info("============================================");
			logger.info("===================请注意===================");
			logger.info("============================================");
			logger.info("");
			logger.info("");
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
					}

					logger.info("===================================================");
					logger.info("===================================================");
					logger.info("===================================================");
					logger.info("================数据转换正式开始====================");
					logger.info("===================================================");
					logger.info("===================================================");
					logger.info("===================================================");

					try {
						storeHouseService.execute(lkbh);
						logger.info("");
						logger.info("");
						typeCodeService.execute();
						logger.info("");
						logger.info("");
						grainCodeService.execute();
						logger.info("");
						logger.info("");
						dataService.execute(lkbh);
						logger.info("");
						logger.info("");
						logger.info("===================================================");
						logger.info("===================================================");
						logger.info("===================================================");
						logger.info("================数据转换正式结束====================");
						logger.info("===================================================");
						logger.info("===================================================");
						logger.info("===================================================");
					} catch (Exception e) {
						e.printStackTrace();
						logger.info("================数据转换出现问题，请分析数据====================");
					}
				}
			}).start();
		}
	}

}
