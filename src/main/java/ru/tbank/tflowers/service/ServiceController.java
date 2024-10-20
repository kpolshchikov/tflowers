package ru.tbank.tflowers.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tbank.tflowers.bouquet.db.BouquetEntity;
import ru.tbank.tflowers.bouquet.db.BouquetRepository;

import java.util.List;

import static ru.tbank.tflowers.config.RedisCacheConfig.BOUQUET_CACHE;

@RestController
@RequestMapping("/serve")
public class ServiceController {

    private final BouquetRepository bouquetRepository;

    public ServiceController(BouquetRepository bouquetRepository) {
        this.bouquetRepository = bouquetRepository;
    }

    @PostMapping("/db/fill")
    public void fillDb() {
        bouquetRepository.saveAll(
                List.of(
                        new BouquetEntity()
                                .setId(1L)
                                .setName("Голубая лагуна")
                                .setDescription("Этот красивый букет состоит из голубых цветочков")
                                .setPrice(3500),
                        new BouquetEntity()
                                .setId(2L)
                                .setName("Красная лагуна")
                                .setDescription("Этот красивый букет состоит из красных цветочков")
                                .setPrice(3550)
                )
        );
    }

    @DeleteMapping("/db/clear")
    public void clearDb() {
        bouquetRepository.deleteAll();
    }

    @DeleteMapping("/cache/clear")
    @CacheEvict(cacheNames = BOUQUET_CACHE, allEntries = true)
    public void clearCache() {
    }
}
