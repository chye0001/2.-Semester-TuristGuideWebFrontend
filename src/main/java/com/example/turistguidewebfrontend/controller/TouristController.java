package com.example.turistguidewebfrontend.controller;

import com.example.turistguidewebfrontend.model.TouristAttraction;
import com.example.turistguidewebfrontend.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService){
        this.touristService = touristService;
    }

    @GetMapping("")
    public String getAllTouristAttraction(Model model){
        List<TouristAttraction> allTouristAttractions = touristService.viewAll();
        model.addAttribute("attractions", allTouristAttractions);

        return "admin";
    }

    @GetMapping("/{name}/tags")
    public String getTagsForAttraction(@PathVariable String name, Model model){
        List<String> turristAttractionTags = touristService.getAttractionTags(name);

        model.addAttribute("attraction", name);
        model.addAttribute("tags", turristAttractionTags);

        return "tags";
    }

    @GetMapping("/create")
    public String createSetup(Model model){
        model.addAttribute("touristAttraction", new TouristAttraction());

        List<String> citySelections = touristService.getCitySelections();
        model.addAttribute("cities", citySelections);

        List<String> tagsSelections = touristService.getTagSelections();
        model.addAttribute("tags", tagsSelections);

        return "create-tourist-attraction";
    }

    @PostMapping("/create")
    public String createAttraction(@ModelAttribute TouristAttraction attractionToAdd){
        touristService.create(attractionToAdd);
        return "redirect:/attractions";
    }

    @GetMapping("/{name}/update")
    public String updateSetup(@PathVariable String name, Model model){
        TouristAttraction attractionToUpdate = touristService.read(name);
        model.addAttribute("touristAttractionToUpdate", attractionToUpdate);

        List<String> citySelections = touristService.getCitySelections();
        model.addAttribute("cities", citySelections);

        List<String> tagsSelections = touristService.getTagSelections();
        model.addAttribute("tags", tagsSelections);

        return "update-tourist-attraction";
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction attraction){
        touristService.update(attraction);

        return "redirect:/attractions";
    }

    @GetMapping("/{name}/delete")
    public String deleteAttraction(@PathVariable("name") String touristAttraction){
        touristService.delete(touristAttraction);

        return "redirect:/attractions";
    }
}
