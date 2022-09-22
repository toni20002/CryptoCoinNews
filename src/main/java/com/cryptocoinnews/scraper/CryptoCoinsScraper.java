package com.cryptocoinnews.scraper;

import com.cryptocoinnews.cryptocoins.data.CryptoCoinsMapper;
import com.cryptocoinnews.cryptocoins.data.entity.CryptoCoins;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Service
@Slf4j
@RequiredArgsConstructor
public class CryptoCoinsScraper {

   private final String url= "https://coinmarketcap.com/";
   private final Connection connection = Jsoup.connect("https://coinmarketcap.com/");
   private final CryptoCoinsMapper cryptoCoinsMapper;

    public void getAndInsertTopTenCoins(LocalDateTime timeOfExecution) throws IOException {
        Document pageToScrape = connection.get();
        Elements elements = pageToScrape.getElementsByTag("tr");
        CryptoCoins coin = new CryptoCoins();
        coin.setTimeOfExecution(timeOfExecution);
        for (int i = 1; i < 11; i++) {
            String position = elements.get(i).childNode(1).childNode(0).childNode(0).toString();
            String name = elements.get(i).childNode(2).childNode(0).childNode(0).childNode(0).childNode(1).childNode(0).childNode(0).toString();
            String price = elements.get(i).childNode(3).childNode(0).childNode(0).childNode(0).childNode(0).toString();
            String hourlyPercentage = elements.get(i).childNode(4).childNode(0).childNode(1).toString();
            String dailyPercentage = elements.get(i).childNode(5).childNode(0).childNode(1).toString();
            String weeklyPercentage = elements.get(i).childNode(6).childNode(0).childNode(1).toString();
            String marketCap = elements.get(i).childNode(7).childNode(0).childNode(1).childNode(0).toString();
            String dailyVolume = elements.get(i).childNode(8).childNode(0).childNode(0).childNode(0).childNode(0).toString();
            String circulatingSupply = elements.get(i).childNode(9).childNode(0).childNode(0).childNode(0).childNode(0).toString();

            coin.setPosition(position);
            coin.setName(name);
            coin.setPrice(price);
            coin.setHourlyPercentage(hourlyPercentage);
            coin.setDailyPercentage(dailyPercentage);
            coin.setWeeklyPercentage(weeklyPercentage);
            coin.setMarketCap(marketCap);
            coin.setDailyVolume(dailyVolume);
            coin.setCirculatingSupply(circulatingSupply);

            insertCoinToDatabase(coin);
        }
    }

    private boolean insertCoinToDatabase(CryptoCoins coin) {
        try {
            this.cryptoCoinsMapper.insertCryptoCoin(coin);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public String writeTopTenCoinsToCsv(List<CryptoCoins> topTenCryptoCoins) throws IOException {
        File file = createCsvFile();
        FileWriter fileWriter = new FileWriter(file);
        try {
            for (CryptoCoins coin : topTenCryptoCoins ) {
                fileWriter.append(coin.toString());
                fileWriter.append("\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileWriter.close();
        }
        fileWriter.close();
        return file.getName();
    }


    private static File createCsvFile() throws IOException {
        File file = new File("cryptoData.csv");
        FileWriter fileWriter = new FileWriter(file);
        //Adding headers to csv file
        StringBuffer sb = new StringBuffer();
        sb.append("Position");
        sb.append(";");
        sb.append("Name");
        sb.append(";");
        sb.append("Price");
        sb.append(";");
        sb.append("Hourly Percentage");
        sb.append(";");
        sb.append("Daily Percentage");
        sb.append(";");
        sb.append("Weekly Percentage");
        sb.append(";");
        sb.append("Market Cap");
        sb.append(";");
        sb.append("Daily Volume");
        sb.append(";");
        sb.append("Circulating Supply");
        sb.append(";");
        sb.append("Time Of Execution");
        sb.append(";");
        sb.append("\r\n");
        fileWriter.append(sb);
        fileWriter.close();
        return file;
    }
}
