package com.example.turistguidewebfrontend.controller;

import com.example.turistguidewebfrontend.model.TouristAttraction;
import com.example.turistguidewebfrontend.service.CurrencyService;
import com.example.turistguidewebfrontend.service.TouristService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TouristController.class)
class TouristControllerTest {

    private TouristAttraction touristAttraction = new TouristAttraction();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristService touristService;

    @MockBean
    private CurrencyService currencyConverterService;


    @Test
    void getAllTouristAttraction() throws Exception {
        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    void getAllTouristAttractionOnly() throws Exception {
        mockMvc.perform(get("/attractions/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("all-tourist-attractions"));
    }

//    @Test
//    void convertCurrencies() throws Exception {
//        when(currencyConverterService.getConversionFactor("DKK")).thenReturn(1.0);
//        mockMvc.perform(get("/attractions/currency-conversion"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("all-tourist-attractions"));
//    }

    @Test
    void getTagsForAttractionUser_SMK() throws Exception {
        mockMvc.perform(get("/attractions/SMK/tags/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("tags-user"));
    }

    @Test
    void getTagsForAttractionAdmin_SMK() throws Exception {
        mockMvc.perform(get("/attractions/SMK/tags"))
                .andExpect(status().isOk())
                .andExpect(view().name("tags-admin"));
    }

    @Test
    void buildCreateForm() throws Exception {
        mockMvc.perform(get("/attractions/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-tourist-attraction"));
    }

    //TODO spørgs om det er korrekt test af post metode
    @Test
    void createAttraction() throws Exception {
        mockMvc.perform(post("/attractions/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));
    }

    @Test
    void buildUpdateForm() throws Exception{
        when(touristService.read("SMK"))
                .thenReturn(new TouristAttraction("SMK", "Statens Museum for Kunst", "København", List.of("Kunst", "Museum"), 99));
        mockMvc.perform(get("/attractions/SMK/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("update-tourist-attraction"));
    }

    @Test
    void updateAttraction() throws Exception{
        mockMvc.perform(post("/attractions/update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));
    }

    @Test
    void deleteAttraction() throws Exception {
        mockMvc.perform(get("/attractions/SMK/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));
    }
}