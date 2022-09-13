package com.cryptocoinnews.cryptocoins.data.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CryptoCoins {
    @Positive
    @NotNull
    private Long id;
    private short Position;
    private Long Price;
    private String hourlyPercentage;
    private String dailyPercentage;
    private String weeklyPercentage;
    private String marketCap;
    private String dailyVolume;
    private String circulatingSupply;
}
