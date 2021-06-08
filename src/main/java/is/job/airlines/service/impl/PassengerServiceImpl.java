package is.job.airlines.service.impl;

import com.google.gson.Gson;
import is.job.airlines.model.Airline;
import is.job.airlines.model.Flight;
import is.job.airlines.model.Person;
import is.job.airlines.model.dtos.PersonSeedDto;
import is.job.airlines.model.service.PersonServiceModel;
import is.job.airlines.model.view.PersonViewModel;
import is.job.airlines.repository.FlightRepository;
import is.job.airlines.repository.PersonRepository;
import is.job.airlines.service.PassengerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {
    private final static String PASSENGERS_PATH = "src/main/resources/static/json/passengers.json";
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final FlightRepository flightRepository;
    private final Gson gson;
    private final Random random;

    public PassengerServiceImpl(PersonRepository personRepository, ModelMapper modelMapper, FlightRepository flightRepository, Gson gson, Random random) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
        this.flightRepository = flightRepository;
        this.gson = gson;
        this.random = random;
    }

    @Override
    public boolean existingPassengerDetails(String email, String telephone) {
        return this.personRepository.existingDetails(email, telephone).isPresent();
    }

    @Override
    public boolean existingPassenger(String idNumber) {
        return this.personRepository.existingPersonById(idNumber).isPresent();
    }

    @Override
    public void savePassenger(PersonServiceModel personServiceModel) {
        Person person = this.modelMapper.map(personServiceModel, Person.class);
        this.personRepository.save(person);
    }

    @Override
    public List<PersonViewModel> getExistingMembers() {
        return this.personRepository.findAllPassengers().stream().map(passenger -> {
            PersonViewModel passengerViewModel = this.modelMapper.map(passenger, PersonViewModel.class);
            return passengerViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean isPassenger(String personId) {
        return this.personRepository.isPassengerById(personId).isPresent();
    }

    @Override
    public boolean isPersonValid(String personId) {
        return this.personRepository.findById(personId).isPresent();
    }

    @Override
    public void seedPassengers() throws IOException {
        if (this.personRepository.count() == 100){
            String content = String.join("", Files.readAllLines(Path.of(PASSENGERS_PATH)));
            PersonSeedDto[] personSeedDtos = this.gson.fromJson(content, PersonSeedDto[].class);
            List<Flight> flights = flightRepository.findAll();
            for (PersonSeedDto personSeedDto : personSeedDtos) {
                Person person = this.modelMapper.map(personSeedDto, Person.class);
                person = this.personRepository.save(person);
//                while (person.getFlights().size() < 10){
//                    int index = random.nextInt(flights.size());
//                    Flight flight = flights.get(index);
//                    if (!person.getFlights().contains(flight)){
//                        person.getFlights().add(flight);
//                    }
//                }
//                this.personRepository.save(person);
            }
        }

    }
}
