package is.job.airlines.service.impl;

import com.google.gson.Gson;
import is.job.airlines.model.Airport;
import is.job.airlines.model.dtos.AirportSeedDto;
import is.job.airlines.model.enums.CountriesEnum;
import is.job.airlines.model.service.AirportServiceModel;
import is.job.airlines.model.view.AirportViewModel;
import is.job.airlines.repository.AirportRepository;
import is.job.airlines.service.AirportService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AirportServiceImpl implements AirportService {
    private final static String AIRPORTS_PATH = "src/main/resources/static/json/airports.json";
    private final AirportRepository airportRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final Random random;

    public AirportServiceImpl(AirportRepository airportRepository, ModelMapper modelMapper, Gson gson, Random random) {
        this.airportRepository = airportRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.random = random;
    }

    @Override
    public boolean existingAirport(String airportName, String country) {
        return this.airportRepository.findByAirportNameAndCountry(airportName, country).isPresent();
    }

    @Override
    public boolean findAirportById(String id) {
        return this.airportRepository.findById(id).isPresent();
    }

    @Override
    public void saveAirport(AirportServiceModel airportServiceModel) {
        this.airportRepository.save(this.modelMapper.map(airportServiceModel, Airport.class));
    }

    @Override
    public List<AirportViewModel> getExistingAirports() {
        return this.airportRepository.findAll().stream().map(
                a -> this.modelMapper.map(a, AirportViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public void seedAirports() throws IOException {
        if (this.airportRepository.count() == 0){
            String content = String.join("", Files.readAllLines(Path.of(AIRPORTS_PATH)));
            AirportSeedDto[] airportSeedDtos = this.gson.fromJson(content, AirportSeedDto[].class);
            CountriesEnum[] countries = CountriesEnum.values();
            for (AirportSeedDto airportSeedDto : airportSeedDtos) {
                Airport airport = new Airport();
                airport.setAirportName(airportSeedDto.getAirportName() + " Airport");
                int index = random.nextInt(countries.length);
                airport.setCountry(countries[index].name());
                this.airportRepository.save(airport);
            }

        }
    }
}
