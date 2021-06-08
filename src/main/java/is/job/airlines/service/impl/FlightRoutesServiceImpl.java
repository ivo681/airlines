package is.job.airlines.service.impl;

import is.job.airlines.model.Airport;
import is.job.airlines.model.FlightRoute;
import is.job.airlines.model.service.FlightRouteServiceModel;
import is.job.airlines.model.view.FlightRouteViewModel;
import is.job.airlines.repository.AirportRepository;
import is.job.airlines.repository.FlightRouteRepository;
import is.job.airlines.service.FlightRoutesService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FlightRoutesServiceImpl implements FlightRoutesService {
    private final FlightRouteRepository flightRouteRepository;
    private final AirportRepository airportRepository;
    private final ModelMapper modelMapper;
    private final Random random;

    public FlightRoutesServiceImpl(FlightRouteRepository flightRouteRepository, AirportRepository airportRepository, ModelMapper modelMapper, Random random) {
        this.flightRouteRepository = flightRouteRepository;
        this.airportRepository = airportRepository;
        this.modelMapper = modelMapper;
        this.random = random;
    }

    @Override
    public boolean existingRoute(String departureAirportId, String arrivalAirportId) {
        return this.flightRouteRepository.existingRoute(departureAirportId, arrivalAirportId).isPresent();
    }

    @Override
    public boolean existingRouteById(String id) {
        return this.flightRouteRepository.findById(id).isPresent();
    }

    @Override
    public void saveRoute(FlightRouteServiceModel flightRouteServiceModel) {
        FlightRoute flightRoute = new FlightRoute();
        flightRoute.setDepartureAirport(this.airportRepository.findById(flightRouteServiceModel.getDeparture_airport_id()).get());
        flightRoute.setArrivalAirport(this.airportRepository.findById(flightRouteServiceModel.getArrival_airport_id()).get());
        this.flightRouteRepository.save(flightRoute);
    }

    @Override
    public List<FlightRouteViewModel> getExistingRoutes() {
        return this.flightRouteRepository.findAll().stream().map(fr -> {
            FlightRouteViewModel flightRouteViewModel = new FlightRouteViewModel();
            flightRouteViewModel.setId(fr.getId());
            flightRouteViewModel.setDeparture_description(String.format("%s (%s)",
                    fr.getDepartureAirport().getAirportName(), fr.getDepartureAirport().getCountry()));
            flightRouteViewModel.setArrival_description(String.format("%s (%s)",
                    fr.getArrivalAirport().getAirportName(), fr.getArrivalAirport().getCountry()));
            return flightRouteViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public void generateRoutes() {
        List<Airport> airports = this.airportRepository.findAll();
        while (this.flightRouteRepository.count() < 60){
            int index = random.nextInt(airports.size());
            Airport departureAirport = airports.get(index);
            index = random.nextInt(airports.size());
            Airport arrivalAirport = airports.get(index);
            if (!existingRoute(departureAirport.getId(), arrivalAirport.getId()) &&
            !departureAirport.getId().equals(arrivalAirport.getId())){
                FlightRoute flightRoute = new FlightRoute();
                flightRoute.setDepartureAirport(departureAirport);
                flightRoute.setArrivalAirport(arrivalAirport);
                this.flightRouteRepository.save(flightRoute);
            }
        }
    }
}
