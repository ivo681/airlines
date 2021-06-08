package is.job.airlines.web;

import is.job.airlines.model.binding.FlightBindingModel;
import is.job.airlines.model.binding.PersonBindingModel;
import is.job.airlines.model.service.FlightServiceModel;
import is.job.airlines.model.service.PersonServiceModel;
import is.job.airlines.service.AirlineService;
import is.job.airlines.service.FlightCrewService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Period;

@Controller
@RequestMapping("/flight-crew")
public class FlightCrewController {
    private final FlightCrewService flightCrewService;
    private final ModelMapper modelMapper;
    private final AirlineService airlineService;

    public FlightCrewController(FlightCrewService flightCrewService, ModelMapper modelMapper, AirlineService airlineService) {
        this.flightCrewService = flightCrewService;
        this.modelMapper = modelMapper;
        this.airlineService = airlineService;
    }

    @GetMapping("/all")
    public String flightCrewViewPage(Model model){
        if (!model.containsAttribute("personBindingModel")) {
            model.addAttribute("personBindingModel", new PersonBindingModel());
            model.addAttribute("existingPerson", false);
            model.addAttribute("existingDetails", false);
        }
        model.addAttribute("crew", true);
        model.addAttribute("action", "/flight-crew/add");
        model.addAttribute("airlines", this.airlineService.getExistingAirlines());
        model.addAttribute("people", this.flightCrewService.getExistingMembers());
        return "person-page";
    }

    @PostMapping("/add")
    public String flightCrewAdd(@Valid @ModelAttribute("personBindingModel") PersonBindingModel personBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){
        boolean existingCrewMember = false;
        boolean existingDetails = false;
        if (bindingResult.hasErrors() || !this.airlineService.findAirlineById(personBindingModel.getEmployerId()) ||
                (Period.between(personBindingModel.getDateOfBirth(), LocalDate.now()).getYears() < 18)) {
            if (personBindingModel.getDateOfBirth() != null &&
                    Period.between(personBindingModel.getDateOfBirth(), LocalDate.now()).getYears() < 18) {
                bindingResult.rejectValue("dateOfBirth", "error.personBindingModel", "You must be at least 18 years old to become a cabin crew member");
            }
            redirectAttributes.addFlashAttribute("personBindingModel", personBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.personBindingModel",
                    bindingResult);
            return "redirect:/flight-crew/all";
        }

        if (this.flightCrewService.existingCrewMemberDetails(personBindingModel.getEmail(), personBindingModel.getTelephone())){
            existingDetails = true;
            redirectAttributes.addFlashAttribute( "existingDetails", true);
        }
        if (this.flightCrewService.existingCrewMember(personBindingModel.getIdNumber())){
            existingCrewMember = true;
            redirectAttributes.addFlashAttribute( "existingPerson", true);
        }


        if (existingDetails || existingCrewMember) {
            redirectAttributes.addFlashAttribute("personBindingModel", personBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.personBindingModel",
                    bindingResult);
            return "redirect:/flight-crew/all";
        }
        this.flightCrewService.saveCrewMember(this.modelMapper.map(personBindingModel, PersonServiceModel.class));
        return "redirect:/flight-crew/all";
    }


}
