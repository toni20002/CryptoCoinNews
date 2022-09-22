package com.cryptocoinnews.cryptoschedule.service;

import com.cryptocoinnews.cryptocoins.service.CryptoCoinsService;
import com.cryptocoinnews.cryptoschedule.data.CryptoScheduleMapper;
import com.cryptocoinnews.cryptoschedule.data.entity.CryptoSchedule;
import com.cryptocoinnews.email.MailSenderService;
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

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executor;

@Configuration
@EnableScheduling
@Slf4j
@AllArgsConstructor
public class CryptoScheduleConfig implements SchedulingConfigurer {
    private final CryptoScheduleMapper cryptoScheduleMapper;
    private final MailSenderService mailSenderService;
    private final CryptoCoinsScraper scraper;
    private final CryptoCoinsService cryptoCoinsService;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setThreadNamePrefix("TaskSchedulerThreadPool");
        taskScheduler.setErrorHandler(t -> {
            log.error("Error in Crypto Scheduler" + t.getMessage());
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
                    if (!cryptoSchedule.isExecuted()) {
                        scraper.getAndInsertTopTenCoins(LocalDateTime.now());
                        String fileName = scraper.writeTopTenCoinsToCsv(cryptoCoinsService.getTopTenCryptoCoins());
                        mailSenderService.sendEmailWithAttachment(cryptoSchedule.getEmail(), "do_not_reply@cryptonews.bg", "Top Ten Crypto Coins Update",
                                "Hello, " + cryptoSchedule.getFirstName(), fileName,
                                fileName, "");

                        cryptoSchedule.setExecuted(true);
                        cryptoScheduleMapper.updateExecutedStatus(cryptoSchedule);

                        Files.deleteIfExists(Path.of(fileName));
                        log.info("Temp file deleted successfully!");
                    } else
                        log.info("CryptoSchedule was already executed for -> " + cryptoSchedule.getFirstName() + " "
                                + cryptoSchedule.getLastName() + " " + cryptoSchedule.getEmail());
                } catch (IOException | MessagingException e) {
                    log.error(e.getMessage());
                }
            }, new CronTrigger(cryptoSchedule.getCron()));
        }
    }
}
