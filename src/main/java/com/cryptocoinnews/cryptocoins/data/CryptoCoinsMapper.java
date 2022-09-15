package com.cryptocoinnews.cryptocoins.data;


import com.cryptocoinnews.cryptocoins.data.entity.CryptoCoins;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CryptoCoinsMapper {

    @Select("SELECT * FROM cryptocoins")
    List<CryptoCoins> getAll();

    @Insert("INSERT INTO cryptocoins (" +
            " `position`," +
            " `name`, " +
            " `price`," +
            " `hourlyPercentage`, " +
            " `dailyPercentage`, " +
            " `weeklyPercentage`, " +
            " `marketCap`," +
            " `dailyVolume`, " +
            " `circulatingSupply`," +
            " `timeOfExecution`" +
            ")" +
            " VALUES (" +
            " #{cryptoCoin.position}," +
            " #{cryptoCoin.name}," +
            " #{cryptoCoin.price}," +
            " #{cryptoCoin.hourlyPercentage}," +
            " #{cryptoCoin.dailyPercentage}," +
            " #{cryptoCoin.weeklyPercentage}," +
            " #{cryptoCoin.marketCap}," +
            " #{cryptoCoin.dailyVolume}," +
            " #{cryptoCoin.circulatingSupply}," +
            " #{cryptoCoin.timeOfExecution}" +
            ")"
    )
    boolean insertCryptoCoin(@Param("cryptoCoin") CryptoCoins cryptoCoin);
}
