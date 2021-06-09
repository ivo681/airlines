package is.job.airlines.web;

import is.job.airlines.model.binding.FlightBindingModel;
import is.job.airlines.model.service.FlightServiceModel;
import is.job.airlines.service.AirplaneService;
import is.job.airlines.service.FlightRoutesService;
import is.job.airlines.service.FlightsService;
import is.job.airlines.service.PassengerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/flights")
public class FlightsController {
    private final ModelMapper modelMapper;
    private final FlightsService flightsService;
    private final FlightRoutesService flightRoutesService;
    private final PassengerService passengerService;
    private final AirplaneService airplaneService;

    public FlightsController(ModelMapper modelMapper, FlightsService flightsService, FlightRoutesService flightRoutesService, PassengerService passengerService, AirplaneService airplaneService) {
        this.modelMapper = modelMapper;
        this.flightsService = flightsService;
        this.flightRoutesService = flightRoutesService;
        this.passengerService = passengerService;
        this.airplaneService = airplaneService;
    }

    @GetMapping("/all")
    public String flightsViewPage(Model model){
        if (!model.containsAttribute("flightBindingModel")) {
            model.addAttribute("flightBindingModel", new FlightBindingModel());
            model.addAttribute("existingFlight", false);
            model.addAttribute("invalidDateAndTimes", false);
        }
        model.addAttribute("existingFlights", this.flightsService.getExistingFlights());
        model.addAttribute("existingPlanes", this.airplaneService.getExistingAirplanes());
        model.addAttribute("existingRoutes", this.flightRoutesService.getExistingRoutes());
        return "flight-page";
    }

    @PostMapping("/add")
    public String airportAdd(@Valid @ModelAttribute("flightBindingModel") FlightBindingModel flightBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes){
        boolean occupiedPlane = false;
        boolean existingFlight = false;
        boolean validDateAndTimes = !flightBindingModel.getArrivalDateAndTime().equals(flightBindingModel.getDepartureDateAndTime());
        if (bindingResult.hasErrors() || !this.flightRoutesService.existingRouteById(flightBindingModel.getFlightRouteId())
                || !this.airplaneService.existingAirplaneById(flightBindingModel.getAirplaneId()) || !validDateAndTimes) {
            if (!validDateAndTimes){
                redirectAttributes.addFlashAttribute( "invalidDateAndTimes", true);
            }
            redirectAttributes.addFlashAttribute("flightBindingModel", flightBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.flightBindingModel",
                    bindingResult);
            return "redirect:/flights/all";
        }

        if (this.flightsService.existingFlightForRoute(flightBindingModel.getAirplaneId(), flightBindingModel.getFlightRouteId()
        , flightBindingModel.getDepartureDateAndTime())){
            existingFlight = true;
            redirectAttributes.addFlashAttribute( "existingFlight", true);
        }
        if (this.flightsService.occupiedAirplaneForDateTime(flightBindingModel.getAirplaneId(), flightBindingModel.getFlightRouteId()
                , flightBindingModel.getDepartureDateAndTime())){
            occupiedPlane = true;
            redirectAttributes.addFlashAttribute( "occupiedPlane", true);
        }

        if (existingFlight || occupiedPlane) {
            redirectAttributes.addFlashAttribute("flightBindingModel", flightBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.flightBindingModel",
                    bindingResult);
            return "redirect:/flights/all";
        }
        this.flightsService.saveFlight(this.modelMapper.map(flightBindingModel, FlightServiceModel.class));
        return "redirect:/flights/all";
    }

    @GetMapping("/add/{id}")
    public String addPersonToFlightPage(@PathVariable("id") String personId,
                                               Model model){
        model.addAttribute("upcomingFlights", this.flightsService.getUpcomingFlights());
        model.addAttribute("personId", personId);
        if (!model.containsAttribute("noCapacity")){
        model.addAttribute("noCapacity", false);
        }
        if (!model.containsAttribute("alreadyOnFlight")){
        model.addAttribute("alreadyOnFlight", false);
        }
        model.addAttribute("crew", !this.passengerService.isPassenger(personId));
        return "add-to-flight-page";
    }

    @GetMapping("/{id}")
    public String getPersonsFlightHistory(@PathVariable("id") String personId,
                                        Model model){
        model.addAttribute("flightList", this.flightsService.getPersonFlightHistory(personId));
        return "person-flight-list";
    }

    @GetMapping("/add/{personId}/{flightId}")
    public String addPersonToFlight(@PathVariable("personId") String personId,
                                    @PathVariable("flightId") String flightId, RedirectAttributes redirectAttributes,
                                    HttpServletRequest request){
        if (!this.flightsService.isFlightValid(flightId) || !this.passengerService.isPersonValid(personId)){
            return "redirect:/flights/add/" + personId;
        }
        if(!this.flightsService.availableCapacity(flightId)){
            redirectAttributes.addAttribute("noCapacity", true);
            return "redirect:/flights/add/" + personId;
        }
        if (this.flightsService.personIsOnFlight(personId, flightId)){
            redirectAttributes.addAttribute("alreadyOnFlight", true);
            return "redirect:/flights/add/" + personId;
        }
        this.flightsService.getPersonOnFlight(personId, flightId);
        return "redirect:/flights/all";
    }

    @GetMapping("/flight-list/{id}")
    public String flightListingViewPage(@PathVariable("id") String flightId,Model model){
        model.addAttribute("flightList", this.flightsService.getFlightList(flightId));
        return "attendees-list-page";
    }
}
