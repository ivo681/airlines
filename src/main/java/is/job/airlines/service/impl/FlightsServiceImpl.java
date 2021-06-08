package is.job.airlines.service.impl;

import com.google.gson.Gson;
import is.job.airlines.model.Airplane;
import is.job.airlines.model.Flight;
import is.job.airlines.model.FlightRoute;
import is.job.airlines.model.Person;
import is.job.airlines.model.dtos.AirlineSeedDto;
import is.job.airlines.model.dtos.FlightSeedDto;
import is.job.airlines.model.service.FlightServiceModel;
import is.job.airlines.model.view.FlightViewModel;
import is.job.airlines.model.view.PersonViewModel;
import is.job.airlines.repository.AirplaneRepository;
import is.job.airlines.repository.FlightRepository;
import is.job.airlines.repository.FlightRouteRepository;
import is.job.airlines.repository.PersonRepository;
import is.job.airlines.service.FlightsService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FlightsServiceImpl implements FlightsService {
    private final static String FLIGHTS_PATH = "src/main/resources/static/json/flights.json";
    private final ModelMapper modelMapper;
    private final FlightRepository flightRepository;
    private final AirplaneRepository airplaneRepository;
    private final FlightRouteRepository flightRouteRepository;
    private final PersonRepository personRepository;
    private final Gson gson;
    private final Random random;

    public FlightsServiceImpl(ModelMapper modelMapper, FlightRepository flightRepository, AirplaneRepository airplaneRepository, FlightRouteRepository flightRouteRepository, PersonRepository personRepository, Gson gson, Random random) {
        this.modelMapper = modelMapper;
        this.flightRepository = flightRepository;
        this.airplaneRepository = airplaneRepository;
        this.flightRouteRepository = flightRouteRepository;
        this.personRepository = personRepository;
        this.gson = gson;
        this.random = random;
    }

    @Override
    public boolean existingFlightForRoute(String airplaneId, String flightRouteId, LocalDateTime departureDateAndTime) {
        return this.flightRepository.existingFlightForRoute(airplaneId, flightRouteId ,departureDateAndTime).isPresent();
    }

    @Override
    public boolean occupiedAirplaneForDateTime(String airplaneId, String flightRouteId, LocalDateTime departureDateAndTime) {
        return this.flightRepository.occupiedAirplaneForDateTime(airplaneId, flightRouteId ,departureDateAndTime).isPresent();
    }

    @Override
    public void saveFlight(FlightServiceModel flightServiceModel) {
        Flight flight = this.modelMapper.map(flightServiceModel, Flight.class);
        flight.setAirplane(this.airplaneRepository.findById(flightServiceModel.getAirplaneId()).get());
        flight.setFlightRoute(this.flightRouteRepository.findById(flightServiceModel.getFlightRouteId()).get());
        this.flightRepository.save(flight);
    }

    @Override
    public List<FlightViewModel> getExistingFlights() {
        return this.flightRepository.findAll().stream().map(flight -> {
            FlightViewModel flightViewModel = this.modelMapper.map(flight, FlightViewModel.class);
            flightViewModel.setFlightRoute(String.format("%s (%s) - %s (%s)",
                    flight.getFlightRoute().getDepartureAirport().getAirportName(), flight.getFlightRoute().getDepartureAirport().getCountry(),
                    flight.getFlightRoute().getArrivalAirport().getAirportName(), flight.getFlightRoute().getArrivalAirport().getCountry()));
            flightViewModel.setAirplane(String.format("%s %s %s", flight.getAirplane().getAirlineUser().getAirlineName(),
                    flight.getAirplane().getModel(), flight.getAirplane().getRegistrationNumber()));
            return flightViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public List<FlightViewModel> getUpcomingFlights() {
        return this.flightRepository.findUpcomingFlights(LocalDateTime.now()).stream().map(flight -> {
            FlightViewModel flightViewModel = this.modelMapper.map(flight, FlightViewModel.class);
            flightViewModel.setFlightRoute(String.format("%s (%s) - %s (%s)",
                    flight.getFlightRoute().getDepartureAirport().getAirportName(), flight.getFlightRoute().getDepartureAirport().getCountry(),
                    flight.getFlightRoute().getArrivalAirport().getAirportName(), flight.getFlightRoute().getArrivalAirport().getCountry()));
            flightViewModel.setAirplane(String.format("%s %s %s", flight.getAirplane().getAirlineUser().getAirlineName(),
                    flight.getAirplane().getModel(), flight.getAirplane().getRegistrationNumber()));
            return flightViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean availableCapacity(String flightId) {
         return this.flightRepository.findById(flightId).get().getAirplane().getCapacity() >
                 this.flightRepository.findById(flightId).get().getPeople().size();
    }

    @Override
    public boolean personIsOnFlight(String personId, String flightId) {
        return this.flightRepository.findById(flightId).get().getPeople().stream().anyMatch(person ->
            person.getId().equals(personId)
        );
    }

    @Override
    public void getPersonOnFlight(String personId, String flightId) {
        Flight flight = this.flightRepository.findById(flightId).get();
        Set<Person> flightPeople = flight.getPeople();
        flightPeople.add(this.personRepository.findById(personId).get());
        flight.setPeople(flightPeople);
        this.flightRepository.save(flight);
    }

    @Override
    public boolean isFlightValid(String flightId) {
        return this.flightRepository.findById(flightId).isPresent();
    }

    @Override
    public List<PersonViewModel> getFlightList(String flightId) {
        return this.flightRepository.findById(flightId).get().getPeople().stream().map(person -> {
            PersonViewModel personViewModel = this.modelMapper.map(person, PersonViewModel.class);
            if (person.getEmployer() !=null) {
                personViewModel.setEmployerName(person.getEmployer().getAirlineName());
            }
            return personViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public void seedFlights() throws IOException {
        if (this.flightRepository.count() == 0){
            String content = String.join("", Files.readAllLines(Path.of(FLIGHTS_PATH)));
            FlightSeedDto[] flightSeedDtos = this.gson.fromJson(content, FlightSeedDto[].class);
            List<Airplane> airplanes = this.airplaneRepository.findAll();
            List<Person> crewAndPassengers = this.personRepository.findAll();
            List<FlightRoute> flightRoutes = this.flightRouteRepository.findAll();
            int index = random.nextInt(5) + 1;
            for (FlightSeedDto flightSeedDto : flightSeedDtos) {
                Flight flight = new Flight();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime departureDateAndTime = LocalDateTime.parse(flightSeedDto.getDepartureDateAndTime(), formatter);
                flight.setDepartureDateAndTime(departureDateAndTime);
                LocalDateTime arrivalDateAndTime = departureDateAndTime.plusHours(index);
                flight.setArrivalDateAndTime(arrivalDateAndTime);
                index = random.nextInt(airplanes.size());
                Airplane airplane = airplanes.get(index);
                index = random.nextInt(flightRoutes.size());
                FlightRoute flightRoute = flightRoutes.get(index);
                while (occupiedAirplaneForDateTime(airplane.getId(), flightRoute.getId(), departureDateAndTime)){
                    index = random.nextInt(airplanes.size());
                    airplane = airplanes.get(index);
                    index = random.nextInt(flightRoutes.size());
                    flightRoute = flightRoutes.get(index);
                }
                flight.setFlightRoute(flightRoute);
                flight.setAirplane(airplane);
                flight = this.flightRepository.save(flight);
                while (flight.getPeople().size() < 10){
                    index = random.nextInt(crewAndPassengers.size());
                    Person person = crewAndPassengers.get(index);
                    if (!flight.getPeople().contains(person)){
                        flight.getPeople().add(person);
                    }
                }
                this.flightRepository.save(flight);
            }
        }
    }

}
