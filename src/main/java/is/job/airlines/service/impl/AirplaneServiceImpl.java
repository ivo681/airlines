package is.job.airlines.service.impl;

import com.google.gson.Gson;
import is.job.airlines.model.Airline;
import is.job.airlines.model.Airplane;
import is.job.airlines.model.dtos.AirplaneSeedDto;
import is.job.airlines.model.dtos.AirportSeedDto;
import is.job.airlines.model.service.AirplaneServiceModel;
import is.job.airlines.model.view.AirplaneViewModel;
import is.job.airlines.repository.AirlineRepository;
import is.job.airlines.repository.AirplaneRepository;
import is.job.airlines.service.AirplaneService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AirplaneServiceImpl implements AirplaneService {
    private final static String AIRPLANES_PATH = "src/main/resources/static/json/airplanes.json";
    private final AirplaneRepository airplaneRepository;
    private final AirlineRepository airlineRepository;
    private final Gson gson;
    private final Random random;
    private final ModelMapper modelMapper;

    public AirplaneServiceImpl(AirplaneRepository airplaneRepository, AirlineRepository airlineRepository, Gson gson, Random random, ModelMapper modelMapper) {
        this.airplaneRepository = airplaneRepository;
        this.airlineRepository = airlineRepository;
        this.gson = gson;
        this.random = random;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean findAirplaneByRegistrationNumber(Long registrationNumber) {
        return this.airplaneRepository.findByRegistrationNumber(registrationNumber).isPresent();
    }

    @Override
    public boolean existingAirplaneById(String id) {
        return this.airplaneRepository.findById(id).isPresent();
    }

    @Override
    public void saveAirplane(AirplaneServiceModel airplaneServiceModel) {
        Airplane airplane = this.modelMapper.map(airplaneServiceModel, Airplane.class);
        airplane.setAirlineOwner(this.airlineRepository.getById(airplaneServiceModel.getAirlineOwnerIdNumber()));
        airplane.setAirlineUser(this.airlineRepository.getById(airplaneServiceModel.getAirlineUserIdNumber()));
        this.airplaneRepository.save(airplane);
    }

    @Override
    public List<AirplaneViewModel> getExistingAirplanes() {
        return this.airplaneRepository.findAll().stream().map(airplane -> {
            AirplaneViewModel airplaneViewModel = this.modelMapper.map(airplane, AirplaneViewModel.class);
            airplaneViewModel.setAirlineOwner(airplane.getAirlineOwner().getAirlineName());
            airplaneViewModel.setAirlineUser(airplane.getAirlineUser().getAirlineName());
            return airplaneViewModel;
        }).collect(Collectors.toList());
    }

    @Override
    public void seedAirplanes() throws IOException {
        if (this.airplaneRepository.count() == 0){
            String content = String.join("", Files.readAllLines(Path.of(AIRPLANES_PATH)));
            AirplaneSeedDto[] airplaneSeedDtos = this.gson.fromJson(content, AirplaneSeedDto[].class);
            List<Airline> airlines = this.airlineRepository.findAll();
            for (AirplaneSeedDto airplaneSeedDto : airplaneSeedDtos) {
                Airplane airplane = this.modelMapper.map(airplaneSeedDto, Airplane.class);
                int index = random.nextInt(airlines.size());
                airplane.setAirlineOwner(airlines.get(index));
                index = random.nextInt(airlines.size());
                airplane.setAirlineUser(airlines.get(index));
                this.airplaneRepository.save(airplane);
            }
        }
    }
}
