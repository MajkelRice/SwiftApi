package github.ryz.swiftapi.service;

import github.ryz.swiftapi.dto.BankDetailsDto;
import github.ryz.swiftapi.dto.BranchResponseDto;
import github.ryz.swiftapi.dto.CountrySwiftCodesResponseDto;
import github.ryz.swiftapi.dto.HeadquarterResponseDto;
import github.ryz.swiftapi.exception.BankNotFoundException;
import github.ryz.swiftapi.model.Bank;
import github.ryz.swiftapi.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankService {

    private final BankRepository bankRepository;
    private final CountryService countryService;

    public BankService(BankRepository bankRepository, CountryService countryService) {
        this.bankRepository = bankRepository;
        this.countryService = countryService;
    }

    public BankDetailsDto getByCode(String code) {
        Bank bank = bankRepository.findBySwiftCode(code)
                .orElseThrow(() -> new BankNotFoundException("Bank with swiftCode=[" + code + "] not found."));
        return toDto(bank);
    }

    public CountrySwiftCodesResponseDto getAllForCountry(String countryCode) {
        var country = countryService.getCountryByCode(countryCode);
        List<BankDetailsDto> swiftCodes = bankRepository.findByCountry(country)
                .stream().map(this::toDto).collect(Collectors.toList());

        return new CountrySwiftCodesResponseDto(
                country.getCode(), country.getName(), swiftCodes
        );
    }

    public void create(String address, String bankName, String countryISO2, String countryName, boolean isHeadquarter, String swiftCode) {
        var country = countryService.getCountryByCode(countryISO2);

        Bank bank = new Bank(address, bankName, country, isHeadquarter, swiftCode);
        bankRepository.save(bank);
    }

    public void delete(String code) {
        Bank bank = bankRepository.findBySwiftCode(code)
                .orElseThrow(() -> new BankNotFoundException("Bank with swiftCode=[" + code + "] not found."));
        bankRepository.delete(bank);
    }

    private List<Bank> findBranches(Bank headquarter) {
        return bankRepository.findBySwiftCodeStartingWith(headquarter.getSwiftCode().substring(0, 8))
                .stream().filter(bank -> !bank.isHeadquarter()).collect(Collectors.toList());
    }

    private BankDetailsDto toDto(Bank bank) {
        if (!bank.isHeadquarter()) {
            return BranchResponseDto.from(bank);
        } else {
            return HeadquarterResponseDto.from(bank, findBranches(bank));
        }
    }
}
