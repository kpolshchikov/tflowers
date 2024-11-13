package ru.tbank.tflowers.bouquet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tbank.tflowers.bouquet.Bouquet;
import ru.tbank.tflowers.bouquet.service.BouquetService;

import java.util.List;

@RestController
@RequestMapping("/bouquets")
public class BouquetController {

    private final BouquetService bouquetService;

    public BouquetController(BouquetService bouquetService) {
        this.bouquetService = bouquetService;
    }

    @GetMapping
    public List<Bouquet> getBouquets() {
        return bouquetService.getBouquets();
    }

    @GetMapping("/stores/{bouquet_id}")
    public List<String> getStores(@PathVariable("bouquet_id") Long bouquetId) {
        return bouquetService.getStores(bouquetId);
    }
}
