package is.job.airlines.web;


import is.job.airlines.model.binding.AirplaneBindingModel;
import is.job.airlines.model.service.AirplaneServiceModel;
import is.job.airlines.service.AirlineService;
import is.job.airlines.service.AirplaneService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/airplanes")
public class AirplaneController {
    private final ModelMapper modelMapper;
    private final AirplaneService airplaneService;
    private final AirlineService airlineService;

    public AirplaneController(ModelMapper modelMapper, AirplaneService airplaneService, AirlineService airlineService) {
        this.modelMapper = modelMapper;
        this.airplaneService = airplaneService;
        this.airlineService = airlineService;
    }

    @GetMapping("/all")
    public String airplaneViewPage(Model model){
        if (!model.containsAttribute("airplaneBindingModel")) {
            model.addAttribute("airplaneBindingModel", new AirplaneBindingModel());
            model.addAttribute("existingAirplane", false);
        }
        model.addAttribute("existingAirlines", this.airlineService.getExistingAirlines());
        model.addAttribute("existingAirplanes", this.airplaneService.getExistingAirplanes());
        return "airplane-page";
    }

    @PostMapping("/add")
    public String airportAdd(@Valid @ModelAttribute("airplaneBindingModel") AirplaneBindingModel airplaneBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors() || !this.airlineService.findAirlineById(airplaneBindingModel.getAirlineOwnerIdNumber())
                || !this.airlineService.findAirlineById(airplaneBindingModel.getAirlineUserIdNumber())) {
            redirectAttributes.addFlashAttribute("airplaneBindingModel", airplaneBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.airplaneBindingModel",
                    bindingResult);
            return "redirect:/airplanes/all";
        }

        if (this.airplaneService.findAirplaneByRegistrationNumber(airplaneBindingModel.getRegistrationNumber())){
            redirectAttributes.addFlashAttribute( "existingAirplane", true);
            redirectAttributes.addFlashAttribute("airplaneBindingModel", airplaneBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.airplaneBindingModel",
                    bindingResult);
            return "redirect:/airplanes/all";
        }

        this.airplaneService.saveAirplane(this.modelMapper.map(airplaneBindingModel, AirplaneServiceModel.class));
        return "redirect:/airplanes/all";
    }
}
