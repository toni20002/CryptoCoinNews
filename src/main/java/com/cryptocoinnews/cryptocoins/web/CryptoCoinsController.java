package com.cryptocoinnews.cryptocoins.web;

import com.cryptocoinnews.cryptocoins.data.entity.CryptoCoins;
import com.cryptocoinnews.cryptocoins.service.CryptoCoinsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cryptocoins")
public class CryptoCoinsController {

    CryptoCoinsService cryptoCoinsService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCryptoCoinEntries() {
        List<CryptoCoins> cryptoCoinsList = this.cryptoCoinsService.getAllCryptoCoins();
        return new ResponseEntity<>(cryptoCoinsList, HttpStatus.OK);
    }

}
