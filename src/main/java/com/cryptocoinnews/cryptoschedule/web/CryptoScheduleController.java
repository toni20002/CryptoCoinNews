package com.cryptocoinnews.cryptoschedule.web;

import com.cryptocoinnews.cryptoschedule.data.CryptoScheduleMapper;
import com.cryptocoinnews.cryptoschedule.data.entity.CryptoSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class CryptoScheduleController {
    private final CryptoScheduleMapper cryptoScheduleMapper;

    @PostMapping()
    @CrossOrigin(origins = "http://localhost:3000/")
    public ResponseEntity<?> createCryptoSchedule(@RequestBody CryptoSchedule cryptoSchedule) {
        cryptoScheduleMapper.insertIntoCryptoSchedule(cryptoSchedule);
        return new ResponseEntity(HttpStatus.OK);
    }
}
