package com.ocwduo.demojmssender;

import com.ocwduo.demojmssender.sender.SimpleSenderExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class DemoJmsSenderApplication implements CommandLineRunner {

    private SimpleSenderExecutorService executorService;

    @Autowired
    public DemoJmsSenderApplication(SimpleSenderExecutorService executorService) {
        this.executorService = executorService;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoJmsSenderApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        executorService.execute();
    }
}
