package com.cryptocoinnews.cryptoschedule.data;

import com.cryptocoinnews.cryptoschedule.data.entity.CryptoSchedule;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CryptoScheduleMapper {
    @Select("SELECT * FROM cryptoschedule")
    List<CryptoSchedule> getAll();

    @Insert("INSERT INTO cryptoschedule (" +
            "`cron`," +
            "`firstName`," +
            "`lastName`," +
            "`email`," +
            "`executed`" +
            ")" +
            " VALUES ( " +
            "#{cryptoschedule.cron}," +
            "#{cryptoschedule.firstName}," +
            "#{cryptoschedule.lastName}," +
            "#{cryptoschedule.email}," +
            "0" +
            ")")
    boolean insertIntoCryptoSchedule(@Param("cryptoschedule") CryptoSchedule cryptoSchedule);


    @Update("Update cryptoschedule set executed = #{cryptoschedule.executed} where email = #{cryptoschedule.email}")
    void updateExecutedStatus(@Param("cryptoschedule") CryptoSchedule cryptoSchedule);
}
