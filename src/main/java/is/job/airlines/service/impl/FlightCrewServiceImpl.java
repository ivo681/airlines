package is.job.airlines.service.impl;

import com.google.gson.Gson;
import is.job.airlines.model.Airline;
import is.job.airlines.model.Flight;
import is.job.airlines.model.Person;
import is.job.airlines.model.dtos.PersonSeedDto;
import is.job.airlines.model.service.PersonServiceModel;
import is.job.airlines.model.view.PersonViewModel;
import is.job.airlines.repository.AirlineRepository;
import is.job.airlines.repository.FlightRepository;
import is.job.airlines.repository.PersonRepository;
import is.job.airlines.service.FlightCrewService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class FlightCrewServiceImpl implements FlightCrewService {
    private final static String FLIGHT_CREW_PATH = "src/main/resources/static/json/cabin-crew.json";
    private final ModelMapper modelMapper;
    private final PersonRepository personRepository;
    private final AirlineRepository airlineRepository;
    private final FlightRepository flightRepository;
    private final Gson gson;
    private final Random random;

    public FlightCrewServiceImpl(ModelMapper modelMapper, PersonRepository personRepository, AirlineRepository airlineRepository, FlightRepository flightRepository, Gson gson, Random random) {
        this.modelMapper = modelMapper;
        this.personRepository = personRepository;
        this.airlineRepository = airlineRepository;
        this.flightRepository = flightRepository;
        this.gson = gson;
        this.random = random;
    }

    @Override
    public boolean existingCrewMemberDetails(String email, String telephone) {
        return this.personRepository.existingDetails(email, telephone).isPresent();
    }

    @Override
    public boolean existingCrewMember(String idNumber) {
        return this.personRepository.existingPersonById(idNumber).isPresent();
    }

    @Override
    public void saveCrewMember(PersonServiceModel personServiceModel) {
        Person person = this.modelMapper.map(personServiceModel, Person.class);
        person.setCrewIdNumber(generateCrewIdNumber());
        person.setEmployer(this.airlineRepository.findById(personServiceModel.getEmployerId()).get());
        this.personRepository.save(person);
    }

    @Override
    public boolean isCrewIdNumberTaken(Long idNumber) {
        return this.personRepository.findByCrewIdNumber(idNumber).isPresent();
    }

    @Override
    public List<PersonViewModel> getExistingMembers() {
        return this.personRepository.findAllCrewMembers().stream().map(crew -> {
            PersonViewModel crewViewModel = this.modelMapper.map(crew, PersonViewModel.class);
            crewViewModel.setEmployerName(crew.getEmployer().getAirlineName());
            return crewViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void seedCrewCabin() throws IOException {
        //int size = this.personRepository.findById("045ae741-cd6e-4803-8a1f-fd785e6ccb68").get().getFlights().size();
        if (this.personRepository.count() == 0){
            String content = String.join("", Files.readAllLines(Path.of(FLIGHT_CREW_PATH)));
            PersonSeedDto[] personSeedDtos = this.gson.fromJson(content, PersonSeedDto[].class);
            List<Flight> flights = flightRepository.findAll();
            List<Airline> airlines = airlineRepository.findAll();
            for (PersonSeedDto personSeedDto : personSeedDtos) {
                Person person = this.modelMapper.map(personSeedDto, Person.class);
                person.setCrewIdNumber(generateCrewIdNumber());
                int index = random.nextInt(airlines.size());
                person.setEmployer(airlines.get(index));
//                person.setFlights(new HashSet<Flight>());
                person= this.personRepository.save(person);
//                index = random.nextInt(flights.size());
//                Flight flight = flights.get(index);
//                person.getFlights().add(flight);
//                while (person.getFlights().size() < 10){
//                    index = random.nextInt(flights.size());
//                    flight = flights.get(index);
//                    if (!person.getFlights().contains(flight)){
//                        person.getFlights().add(flight);
//                    }
//                }
////                person.setFlights(addedFlights);
//                this.personRepository.save(person);

            }
        }
    }

    private Long generateCrewIdNumber() {
        long crewId = (10000000 + this.random.nextInt(9000000));
        while (isCrewIdNumberTaken(crewId)) {
            crewId = (10000000 + this.random.nextInt(9000000));
        }
        return crewId;
    }
}
