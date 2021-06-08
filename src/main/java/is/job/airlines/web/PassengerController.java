package is.job.airlines.web;

import is.job.airlines.model.binding.PersonBindingModel;
import is.job.airlines.model.service.PersonServiceModel;
import is.job.airlines.service.PassengerService;
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
import java.time.LocalDate;
import java.time.Period;

@Controller
@RequestMapping("/passengers")
public class PassengerController {
    private final ModelMapper modelMapper;
    private final PassengerService passengerService;

    public PassengerController(ModelMapper modelMapper, PassengerService passengerService) {
        this.modelMapper = modelMapper;
        this.passengerService = passengerService;
    }

    @GetMapping("/all")
    public String flightCrewViewPage(Model model){
        if (!model.containsAttribute("personBindingModel")) {
            model.addAttribute("personBindingModel", new PersonBindingModel());
            model.addAttribute("existingPerson", false);
            model.addAttribute("existingDetails", false);
        }
        model.addAttribute("crew", false);
        model.addAttribute("action", "/passengers/add");
        model.addAttribute("people", this.passengerService.getExistingMembers());
        return "person-page";
    }

    @PostMapping("/add")
    public String airportAdd(@Valid @ModelAttribute("personBindingModel") PersonBindingModel personBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){
        boolean existingPassenger = false;
        boolean existingDetails = false;
        if (bindingResult.hasErrors() ||
                (Period.between(personBindingModel.getDateOfBirth(), LocalDate.now()).getYears() < 16)) {
            if (personBindingModel.getDateOfBirth() != null &&
                    Period.between(personBindingModel.getDateOfBirth(), LocalDate.now()).getYears() < 16) {
                bindingResult.rejectValue("dateOfBirth", "error.personBindingModel", "You must be at least 16 years old to travel by plane");
            }
            redirectAttributes.addFlashAttribute("personBindingModel", personBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.personBindingModel",
                    bindingResult);
            return "redirect:/passengers/all";
        }

        if (this.passengerService.existingPassengerDetails(personBindingModel.getEmail(), personBindingModel.getTelephone())){
            existingDetails = true;
            redirectAttributes.addFlashAttribute( "existingDetails", true);
        }
        if (this.passengerService.existingPassenger(personBindingModel.getIdNumber())){
            existingPassenger = true;
            redirectAttributes.addFlashAttribute( "existingPerson", true);
        }


        if (existingDetails || existingPassenger) {
            redirectAttributes.addFlashAttribute("personBindingModel", personBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.personBindingModel",
                    bindingResult);
            return "redirect:/passengers/all";
        }
        this.passengerService.savePassenger(this.modelMapper.map(personBindingModel, PersonServiceModel.class));
        return "redirect:/passengers/all";
    }
}
