package github.ryz.swiftapi.service;

import github.ryz.swiftapi.dto.BankDetailsDto;
import github.ryz.swiftapi.dto.CountrySwiftCodesResponseDto;
import github.ryz.swiftapi.dto.DefaultResponseDto;
import org.springframework.stereotype.Service;

@Service
public class SwiftService {

    private final BankService bankService;

    public SwiftService(BankService bankService) {
        this.bankService = bankService;
    }

    public BankDetailsDto getByCode(String code) {
        return bankService.getByCode(code);
    }

    public CountrySwiftCodesResponseDto getByCountry(String countryCode) {
        return bankService.getAllForCountry(countryCode);
    }

    public DefaultResponseDto create(
            String address,
            String bankName,
            String countryISO2,
            String countryName,
            boolean isHeadquarter,
            String swiftCode
    ) {
        bankService.create(
                address,
                bankName,
                countryISO2,
                countryName,
                isHeadquarter,
                swiftCode
        );

        return new DefaultResponseDto("All went ok.");
    }

    public DefaultResponseDto delete(String swiftCode) {
        bankService.delete(swiftCode);

        return new DefaultResponseDto("All went ok.");
    }
}
