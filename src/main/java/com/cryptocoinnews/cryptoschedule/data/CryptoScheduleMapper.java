package com.cryptocoinnews.cryptoschedule.data;

import com.cryptocoinnews.cryptoschedule.data.entity.CryptoSchedule;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CryptoScheduleMapper {
    @Select("SELECT * FROM cryptoschedule")
    List<CryptoSchedule> getAll();

    @Insert("INSERT INTO cryptoschedule (`cron`)" +
            " VALUES (#{cryptoschedule.cron})")
    boolean insertIntoCryptoSchedule(@Param("cryptoschedule") CryptoSchedule cryptoSchedule);
}
