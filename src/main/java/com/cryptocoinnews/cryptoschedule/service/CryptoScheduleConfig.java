package com.cryptocoinnews.cryptoschedule.service;

import com.cryptocoinnews.cryptoschedule.data.CryptoScheduleMapper;
import com.cryptocoinnews.cryptoschedule.data.entity.CryptoSchedule;
import com.cryptocoinnews.scraper.CryptoCoinsScraper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executor;

@Configuration
@EnableScheduling
@Slf4j
@AllArgsConstructor
public class CryptoScheduleConfig implements SchedulingConfigurer {
    private final CryptoScheduleMapper cryptoScheduleMapper;
    private final CryptoCoinsScraper scraper;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(3);
        taskScheduler.setThreadNamePrefix("TaskSchedulerThreadPool");
        taskScheduler.setErrorHandler(t -> {
            log.error("Error in Crypto Scheduler");
        });

        return taskScheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        List<CryptoSchedule> cryptoScheduleList = this.cryptoScheduleMapper.getAll();
        for (CryptoSchedule cryptoSchedule : cryptoScheduleList) {
            if (!CronExpression.isValidExpression(cryptoSchedule.getCron())) {
                throw new IllegalArgumentException("INCORRECT CRON PATTERN -> " + cryptoSchedule);
            }
        }
        //Create a call to the database for each crypto schedule
        for (CryptoSchedule cryptoSchedule : cryptoScheduleList) {
            taskRegistrar.addTriggerTask(() -> {
                try {
                    scraper.getAndInsertTopTenCoins(LocalDateTime.now());
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                log.info("Got top ten coins for the specified period and inserted them! Time: " + LocalDateTime.now());
            }, new CronTrigger(cryptoSchedule.getCron()));
        }
    }
}
