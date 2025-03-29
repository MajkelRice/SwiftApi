package github.ryz.swiftapi.service;

import github.ryz.swiftapi.exception.CountryNotFoundException;
import github.ryz.swiftapi.model.Country;
import github.ryz.swiftapi.repository.CountryRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country getCountryByCode(String code) {
        return countryRepository.findByCode(code)
                .orElseThrow(() -> new CountryNotFoundException(
                        "Country with code=[" + code + "] is not found."
                ));
    }
}
