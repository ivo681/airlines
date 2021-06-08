package is.job.airlines.service.impl;

import com.google.gson.Gson;
import is.job.airlines.model.Airline;
import is.job.airlines.model.dtos.AirlineSeedDto;
import is.job.airlines.model.service.AirlineServiceModel;
import is.job.airlines.model.view.AirlineViewModel;
import is.job.airlines.repository.AirlineRepository;
import is.job.airlines.service.AirlineService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AirlineServiceImpl implements AirlineService {
    private final static String AIRLINES_PATH = "src/main/resources/static/json/airlines.json";
    private final AirlineRepository airlineRepository;
    private final Gson gson;
    private final Random random;
    private final ModelMapper modelMapper;

    public AirlineServiceImpl(AirlineRepository airlineRepository, Gson gson, Random random, ModelMapper modelMapper) {
        this.airlineRepository = airlineRepository;
        this.gson = gson;
        this.random = random;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AirlineViewModel> getExistingAirlines() {
        return this.airlineRepository.findAll().stream().map(
                a -> this.modelMapper.map(a, AirlineViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public boolean existingAirline(String airlineName, Long registrationNumber) {
        return this.airlineRepository.findByAirlineNameAndRegistrationNumber(airlineName, registrationNumber).isPresent();
    }

    @Override
    public void saveAirline(AirlineServiceModel airlineServiceModel) {
        this.airlineRepository.save(this.modelMapper.map(airlineServiceModel, Airline.class));
    }

    @Override
    public boolean findAirlineById(String id) {
        return this.airlineRepository.findById(id).isPresent();
    }

    @Override
    public boolean findAirlineByRegistrationNumber(Long registrationNumber) {
        return this.airlineRepository.findByRegistrationNumber(registrationNumber).isPresent();
    }

    @Override
    public void seedAirlines() throws IOException {
        if (this.airlineRepository.count() == 0){
            String content = String.join("", Files.readAllLines(Path.of(AIRLINES_PATH)));
            AirlineSeedDto[] airlineSeedDtos = this.gson.fromJson(content, AirlineSeedDto[].class);
            for (AirlineSeedDto airlineSeedDto : airlineSeedDtos) {
                Airline airline = new Airline();
                airline.setAirlineName(airlineSeedDto.getAirlineName() + " Airlines");
                airline.setRegistrationNumber(generateRegistrationNumber());
                this.airlineRepository.save(airline);
            }
        }
    }

    private Long generateRegistrationNumber() {
        long registrationNumber = (10000000 + this.random.nextInt(89999999));
        while (findAirlineByRegistrationNumber(registrationNumber)) {
            registrationNumber = (10000000 + this.random.nextInt(89999999));
        }
        return registrationNumber;
    }
}
