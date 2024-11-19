package ru.tbank.tflowers.bouquet.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.tbank.tflowers.bouquet.db.BouquetEntity;
import ru.tbank.tflowers.bouquet.db.BouquetRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.tbank.tflowers.config.RedisCacheConfig.BOUQUET_CACHE;

@Service
public class BouquetCacheService {

    private final BouquetRepository bouquetRepository;

    public BouquetCacheService(BouquetRepository bouquetRepository) {
        this.bouquetRepository = bouquetRepository;
    }

    @Cacheable(cacheManager = "redisCacheManager", cacheNames = BOUQUET_CACHE)
    public List<BouquetEntity> getBouquetsForToday() {
        return bouquetRepository.findAll().stream()
                .filter(it -> it.getLastUpdated().equals(LocalDate.now()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
