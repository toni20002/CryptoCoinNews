package com.cryptocoinnews.cryptocoins.data.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
public class CryptoCoins {
    @Positive
    @NotNull
    private Long id;
    private String position;
    private String name;
    private String price;
    private String hourlyPercentage;
    private String dailyPercentage;
    private String weeklyPercentage;
    private String marketCap;
    private String dailyVolume;
    private String circulatingSupply;
    private LocalDateTime timeOfExecution;

    @Override
    public String toString() {
        return this.position  + ";" + this.name + ";" +  this.price
                + ";" + this.hourlyPercentage + ";" + this.dailyPercentage
                + ";" + this.weeklyPercentage + ";" + this.marketCap
                + ";" + this.dailyVolume + ";" + this.circulatingSupply
                + ";" + this.timeOfExecution;
    }
}
