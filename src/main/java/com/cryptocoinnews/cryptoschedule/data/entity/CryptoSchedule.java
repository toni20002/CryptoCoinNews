package com.cryptocoinnews.cryptoschedule.data.entity;

import lombok.Data;

@Data
public class CryptoSchedule {
    private String cron;
    private String firstName;
    private String lastName;
    private String email;
    private boolean executed;
}
