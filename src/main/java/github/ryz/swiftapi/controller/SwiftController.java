package github.ryz.swiftapi.controller;

import github.ryz.swiftapi.dto.BankDetailsDto;
import github.ryz.swiftapi.dto.CountrySwiftCodesResponseDto;
import github.ryz.swiftapi.dto.DefaultResponseDto;
import github.ryz.swiftapi.dto.SwiftCreateRequestDto;
import github.ryz.swiftapi.service.SwiftService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/v1/swift-codes")
public class SwiftController {

    private final SwiftService swiftService;

    public SwiftController(SwiftService swiftService) {
        this.swiftService = swiftService;
    }

    @GetMapping("/{swift-code}")
    public BankDetailsDto getDetails(@PathVariable("swift-code") String code) {
        return swiftService.getByCode(code);
    }

    @GetMapping("/country/{countryISO2code}")
    public CountrySwiftCodesResponseDto getSwiftCodesForCountry(@PathVariable("countryISO2code") String countryCode) {
        return swiftService.getByCountry(countryCode);
    }

    @PostMapping
    public DefaultResponseDto addSwiftCode(@RequestBody @Valid SwiftCreateRequestDto swiftCreateDto) {
        return swiftService.create(
                swiftCreateDto.getAddress(),
                swiftCreateDto.getBankName(),
                swiftCreateDto.getCountryISO2(),
                swiftCreateDto.getCountryName(),
                swiftCreateDto.isHeadquarter(),
                swiftCreateDto.getSwiftCode()
        );
    }

    @DeleteMapping("/{swift-code}")
    public DefaultResponseDto deleteSwiftCode(@PathVariable("swift-code") String code) {
        return swiftService.delete(code);
    }
}
