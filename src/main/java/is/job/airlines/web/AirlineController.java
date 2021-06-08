package is.job.airlines.web;

import is.job.airlines.model.binding.AirlineBindingModel;
import is.job.airlines.model.service.AirlineServiceModel;
import is.job.airlines.service.AirlineService;
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
@RequestMapping("/airlines")
public class AirlineController {
    private final AirlineService airlineService;
    private final ModelMapper modelMapper;

    public AirlineController(AirlineService airlineService, ModelMapper modelMapper) {
        this.airlineService = airlineService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public String airportViewPage(Model model){
        if (!model.containsAttribute("airlineBindingModel")) {
            model.addAttribute("airlineBindingModel", new AirlineBindingModel());
            model.addAttribute("existingAirline", false);
        }
        model.addAttribute("existingAirlines", this.airlineService.getExistingAirlines());
        return "airline-page";
    }

    @PostMapping("/add")
    public String airportAdd(@Valid @ModelAttribute("airlineBindingModel") AirlineBindingModel airlineBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("airlineBindingModel", airlineBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.airlineBindingModel",
                    bindingResult);
//            redirectAttributes.addAttribute("existingAirports", this.airportService.getExistingAirlines());
            return "redirect:/airlines/all";
        }

        if (this.airlineService.existingAirline(airlineBindingModel.getAirlineName().trim(), airlineBindingModel.getRegistrationNumber())) {
            redirectAttributes.addFlashAttribute("airlineBindingModel", airlineBindingModel);
            redirectAttributes.addFlashAttribute( "existingAirline", true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.airlineBindingModel",
                    bindingResult);
//            redirectAttributes.addAttribute("existingAirports", this.airportService.getExistingAirlines());
            return "redirect:/airlines/all";
        }
        this.airlineService.saveAirline(this.modelMapper.map(airlineBindingModel, AirlineServiceModel.class));
        return "redirect:/airlines/all";
    }
}
