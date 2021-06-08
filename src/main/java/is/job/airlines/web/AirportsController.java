package is.job.airlines.web;

import is.job.airlines.model.binding.AirportBindingModel;
import is.job.airlines.model.service.AirportServiceModel;
import is.job.airlines.service.AirportService;
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
@RequestMapping("/airports")
public class AirportsController {
    private final AirportService airportService;
    private final ModelMapper modelMapper;

    public AirportsController(AirportService airportService, ModelMapper modelMapper) {
        this.airportService = airportService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    public String airportViewPage(Model model){
        if (!model.containsAttribute("airportBindingModel")) {
            model.addAttribute("airportBindingModel", new AirportBindingModel());
        }
        model.addAttribute("existingAirports", this.airportService.getExistingAirports());
        return "airport-page";
    }

    @PostMapping("/add")
    public String airportAdd(@Valid @ModelAttribute("airportBindingModel") AirportBindingModel airportBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("airportBindingModel", airportBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.airportBindingModel",
                    bindingResult);
//            redirectAttributes.addAttribute("existingAirports", this.airportService.getExistingAirlines());
            return "redirect:/airports/all";
        }

        if (this.airportService.existingAirport(airportBindingModel.getAirportName().trim(), airportBindingModel.getCountry())) {
            redirectAttributes.addFlashAttribute("airportBindingModel", airportBindingModel);
            bindingResult.rejectValue("airportName", "airportExistsError", "This airport is already registered, please try again");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.airportBindingModel",
                    bindingResult);
//            redirectAttributes.addAttribute("existingAirports", this.airportService.getExistingAirlines());
            return "redirect:/airports/all";
        }
        this.airportService.saveAirport(this.modelMapper.map(airportBindingModel, AirportServiceModel.class));
        return "redirect:/airports/all";
    }
}
