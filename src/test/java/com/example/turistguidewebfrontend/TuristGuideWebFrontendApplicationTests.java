package com.example.turistguidewebfrontend;

import com.example.turistguidewebfrontend.controller.TouristController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TuristGuideWebFrontendApplicationTests {

    @Autowired
    private TouristController controller;

    @Test //denne metode kaldes også en smoke test
    void contextLoads() { // denne tester om spring boot kan finde ud af instantierer objekterne, fra dependency injection. AKA, dependency injection bliver testet.
        assertThat(controller).isNotNull();
    }
}
