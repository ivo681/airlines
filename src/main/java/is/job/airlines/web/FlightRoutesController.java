package is.job.airlines.web;

import is.job.airlines.model.binding.AirportBindingModel;
import is.job.airlines.model.binding.FlightRouteBindingModel;
import is.job.airlines.model.service.AirportServiceModel;
import is.job.airlines.model.service.FlightRouteServiceModel;
import is.job.airlines.service.AirportService;
import is.job.airlines.service.FlightRoutesService;
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
@RequestMapping("/flight-routes")
public class FlightRoutesController {
    private final ModelMapper modelMapper;
    private final FlightRoutesService flightRoutesService;
    private final AirportService airportService;

    public FlightRoutesController(ModelMapper modelMapper, FlightRoutesService flightRoutesService, AirportService airportService) {
        this.modelMapper = modelMapper;
        this.flightRoutesService = flightRoutesService;
        this.airportService = airportService;
    }

    @GetMapping("/all")
    public String flightRoutesViewPage(Model model){
        if (!model.containsAttribute("flightRouteBindingModel")) {
            model.addAttribute("flightRouteBindingModel", new FlightRouteBindingModel());
            model.addAttribute("existingRoute", false);
            model.addAttribute("sameSelection", false);
        }
        model.addAttribute("airports", this.airportService.getExistingAirports());
        model.addAttribute("existingRoutes", this.flightRoutesService.getExistingRoutes());
        return "flight-route-page";
    }

    @PostMapping("/add")
    public String airportAdd(@Valid @ModelAttribute("flightRouteBindingModel") FlightRouteBindingModel flightRouteBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){
        boolean sameSelection = false;
        boolean existingRoute = false;
        if (bindingResult.hasErrors() || !this.airportService.findAirportById(flightRouteBindingModel.getDeparture_airport_id())
        || !this.airportService.findAirportById(flightRouteBindingModel.getArrival_airport_id())) {
            redirectAttributes.addFlashAttribute("flightRouteBindingModel", flightRouteBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.flightRouteBindingModel",
                    bindingResult);
            return "redirect:/flight-routes/all";
        }

        if (flightRouteBindingModel.getArrival_airport_id().trim().equals(flightRouteBindingModel.getDeparture_airport_id().trim())){
            sameSelection = true;
            redirectAttributes.addFlashAttribute( "sameSelection", true);
        }
        if (this.flightRoutesService.existingRoute(flightRouteBindingModel.getDeparture_airport_id().trim(),
                flightRouteBindingModel.getArrival_airport_id().trim())){
            existingRoute = true;
            redirectAttributes.addFlashAttribute( "existingRoute", true);
        }

        if (sameSelection || existingRoute) {
            redirectAttributes.addFlashAttribute("flightRouteBindingModel", flightRouteBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.flightRouteBindingModel",
                    bindingResult);
            return "redirect:/flight-routes/all";
        }
        this.flightRoutesService.saveRoute(this.modelMapper.map(flightRouteBindingModel, FlightRouteServiceModel.class));
        return "redirect:/flight-routes/all";
    }
}
