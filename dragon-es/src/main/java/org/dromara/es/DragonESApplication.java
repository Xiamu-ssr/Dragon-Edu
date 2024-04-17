package org.dromara.es;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.dromara.easyes.starter.register.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 * 系统模块
 *
 * @author ruoyi
 */
@EnableDubbo
@SpringBootApplication
@EsMapperScan("org.dromara.es.esmapper")
public class DragonESApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DragonESApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ  搜索引擎模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
