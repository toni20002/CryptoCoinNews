package com.cryptocoinnews.cryptocoins.data;


import com.cryptocoinnews.cryptocoins.data.entity.CryptoCoins;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CryptoCoinsMapper {

    @Select("SELECT * FROM cryptocoins")
    List<CryptoCoins> getAll();
}
