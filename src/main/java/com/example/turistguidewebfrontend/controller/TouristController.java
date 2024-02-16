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

    //New code

    @GetMapping("/{name}/tags")
    public String getTagsForAttraction(@PathVariable String name, Model model){
        List<String> turristAttractionTags = touristService.getAttractionTags(name);

        model.addAttribute("attraction", name);
        model.addAttribute("tags", turristAttractionTags);

        return "tags";
    }

    @GetMapping("/create")
    public String create(Model model){
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























    //Old code
    @GetMapping("/view/{name}")
    public ResponseEntity<TouristAttraction> getTouristAttraction(@PathVariable String name){
        TouristAttraction viewTouristAttraction = touristService.view(name);

        if (viewTouristAttraction == null){
            return new ResponseEntity<>(viewTouristAttraction, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(viewTouristAttraction, HttpStatus.OK);
    }



    @PutMapping("/update")
    public ResponseEntity<TouristAttraction> update(@RequestBody TouristAttraction touristAttraction){
        TouristAttraction updatedTouristAttraction = touristService.update(touristAttraction);

        if (updatedTouristAttraction == null){
            return new ResponseEntity<>(updatedTouristAttraction, HttpStatus.NOT_FOUND);

        }else
            return new ResponseEntity<>(touristAttraction, HttpStatus.OK);
    }

    @GetMapping("/delete/{name}")
    public ResponseEntity<TouristAttraction> delete(@PathVariable String name){
        TouristAttraction deletedTouristAttraction = touristService.delete(name);

        if(deletedTouristAttraction == null){
            return new ResponseEntity<>(deletedTouristAttraction, HttpStatus.NOT_FOUND);

        } else
            return new ResponseEntity<>(deletedTouristAttraction, HttpStatus.OK);
    }
}
