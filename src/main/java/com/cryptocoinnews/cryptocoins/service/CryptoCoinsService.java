package com.cryptocoinnews.cryptocoins.service;

import com.cryptocoinnews.cryptocoins.data.CryptoCoinsMapper;
import com.cryptocoinnews.cryptocoins.data.entity.CryptoCoins;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoCoinsService {

    private final CryptoCoinsMapper cryptoCoinsMapper;

    public List<CryptoCoins> getAllCryptoCoins() {
        return this.cryptoCoinsMapper.getAll();
    }
}
