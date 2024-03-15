package com.example.turistguidewebfrontend.controller;

import com.example.turistguidewebfrontend.model.TouristAttraction;
import com.example.turistguidewebfrontend.service.CurrencyService;
import com.example.turistguidewebfrontend.service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {

    private final TouristService touristService;
    private final CurrencyService currencyService;

    public TouristController(TouristService touristService, CurrencyService currencyService) {
        this.touristService = touristService;
        this.currencyService = currencyService;
    }

    @GetMapping("")
    public String getAllTouristAttraction(Model model) {
        List<TouristAttraction> allTouristAttractions = touristService.getAllAttractions();
        model.addAttribute("attractions", allTouristAttractions);
        return "admin";
    }

    @GetMapping("/all")
    public String getAllTouristAttractionOnly(Model model) throws IOException {
        List<TouristAttraction> allTouristAttractions = touristService.getAllAttractions();
        model.addAttribute("attractions", allTouristAttractions);

//        for (TouristAttraction attraction : allTouristAttractions) {
//           String formatedPrice = (new Formatter().format("%.2f", attraction.getPrice()).toString());
//           attraction.setPrice(Double.parseDouble(formatedPrice));
//        }

        List<String> currencyList = currencyService.getAllCurrencies();
        model.addAttribute("currencies", currencyList);
        model.addAttribute("currency", "DKK");
        model.addAttribute("conversionFactor", 1);
        return "all-tourist-attractions";
    }

    @GetMapping("/currency-conversion")
    public String convertCurrencies(@RequestParam String currency, Model model) {
        List<TouristAttraction> allTouristAttractions = touristService.getAllAttractions();
        model.addAttribute("attractions", allTouristAttractions);

        List<String> currencyList = currencyService.getAllCurrencies();
        model.addAttribute("currencies", currencyList);

        double conversionFactor = currencyService.getConversionFactor(currency);

        model.addAttribute("conversionFactor", conversionFactor);
        model.addAttribute("currency", currency);

        return "all-tourist-attractions";
    }

    @GetMapping("/{name}/tags/user")
    public String getTagsForAttractionUser(@PathVariable String name, Model model) {
        List<String> turristAttractionTags = touristService.getAttractionTagsByName(name);

        model.addAttribute("attraction", name);
        model.addAttribute("tags", turristAttractionTags);
        return "tags-user";
    }

    @GetMapping("/{name}/tags")
    public String getTagsForAttractionAdmin(@PathVariable String name, Model model) {
        List<String> turristAttractionTags = touristService.getAttractionTagsByName(name);

        model.addAttribute("attraction", name);
        model.addAttribute("tags", turristAttractionTags);

        return "tags-admin";
    }

    @GetMapping("/create")
    public String buildCreateForm(Model model) {
        model.addAttribute("touristAttraction", new TouristAttraction());

        List<String> citySelections = touristService.getCitySelections();
        model.addAttribute("cities", citySelections);

        List<String> tagsSelections = touristService.getTagSelections();
        model.addAttribute("tags", tagsSelections);
        return "create-tourist-attraction";
    }

    @PostMapping("/create")
    public String createAttraction(@ModelAttribute TouristAttraction attractionToAdd) {
        touristService.create(attractionToAdd);
        return "redirect:/attractions";
    }

    @GetMapping("/{name}/update")
    public String buildUpdateForm(@PathVariable String name, Model model) {
        TouristAttraction attractionToUpdate = touristService.getAttractionOnName(name);
        model.addAttribute("touristAttractionToUpdate", attractionToUpdate);

        List<String> citySelections = touristService.getCitySelections();
        model.addAttribute("cities", citySelections);

        List<String> tagsSelections = touristService.getTagSelections();
        model.addAttribute("tags", tagsSelections);

        return "update-tourist-attraction";
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction attraction) {
        touristService.update(attraction);

        return "redirect:/attractions";
    }

    @GetMapping("/{name}/delete")
    public String deleteAttraction(@PathVariable("name") String touristAttraction) {
        touristService.delete(touristAttraction);

        return "redirect:/attractions";
    }
}
