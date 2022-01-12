package lev.philippov.originmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsynkTastConfig {

    TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor();
    }

}