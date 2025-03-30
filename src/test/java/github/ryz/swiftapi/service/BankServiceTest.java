package github.ryz.swiftapi.service;

import github.ryz.swiftapi.dto.BankDetailsDto;
import github.ryz.swiftapi.dto.CountrySwiftCodesResponseDto;
import github.ryz.swiftapi.exception.BankNotFoundException;
import github.ryz.swiftapi.model.Bank;
import github.ryz.swiftapi.model.Country;
import github.ryz.swiftapi.repository.BankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @Mock
    private BankRepository bankRepository;

    @Mock
    private CountryService countryService;

    @InjectMocks
    private BankService bankService;

    private final String swiftCode = "ABCDEFGH123";
    private final String countryCode = "PL";
    private final String countryName = "Poland";
    private final String bankName = "Test";
    private final String address = "Random street";
    private final boolean isHeadquarter = true;

    private Bank bank;
    private Country country;

    @BeforeEach
    public void setUp() {
        country = new Country(countryName, countryCode);
        bank = new Bank(
                address,
                bankName,
                country,
                isHeadquarter,
                swiftCode
        );
    }

    @Test
    public void getByCode_shouldThrowErrorIfSwiftCodeIsMissing() {
        // Arrange
        when(bankRepository.findBySwiftCode(swiftCode)).thenReturn(Optional.empty());

        // Act & Assert
        BankNotFoundException exception = assertThrows(BankNotFoundException.class, () -> {
            bankService.getByCode(swiftCode);
        });
        assertEquals("Bank with swiftCode=[" + swiftCode + "] not found.", exception.getMessage());
    }

    @Test
    public void getByCode_shouldWorkProperlyIfCodeIsPresentInDb() {
        // Arrange
        when(bankRepository.findBySwiftCode(swiftCode)).thenReturn(Optional.of(bank));

        // Act
        BankDetailsDto result = bankService.getByCode(swiftCode);

        // Assert
        assertNotNull(result);
        assertEquals(bankName, result.getBankName());
        assertEquals(address, result.getAddress());
        assertEquals(swiftCode, result.getSwiftCode());
    }

    @Test
    public void getAllForCountry_shouldWorkProperly() {
        // Arrange
        when(countryService.getCountryByCode(countryCode)).thenReturn(country);
        when(bankRepository.findByCountry(country)).thenReturn(List.of(bank));

        // Act
        CountrySwiftCodesResponseDto result = bankService.getAllForCountry(countryCode);

        // Assert
        assertEquals(countryCode, result.getCountryISO2());
        assertEquals(countryName, result.getCountryName());
        assertEquals(1, result.getSwiftCodes().size());
        assertEquals(swiftCode, result.getSwiftCodes().getFirst().getSwiftCode());
    }

    @Test
    public void create_shouldWorkProperly() {
        // Arrange
        when(countryService.getCountryByCode(countryCode)).thenReturn(country);

        // Act
        bankService.create(address, bankName, countryCode, countryName, isHeadquarter, swiftCode);

        // Assert
        verify(bankRepository).save(any(Bank.class));
    }

    @Test
    public void delete_shouldThrowErrorIfSwiftCodeNotMatches() {
        // Arrange
        when(bankRepository.findBySwiftCode(swiftCode)).thenReturn(Optional.empty());

        // Act & Assert
        BankNotFoundException exception = assertThrows(BankNotFoundException.class, () -> {
            bankService.delete(swiftCode);
        });
        assertEquals("Bank with swiftCode=[" + swiftCode + "] not found.", exception.getMessage());
    }

    @Test
    public void delete_shouldWorkProperlyWhenSwiftcodeIsPresentInDb() {
        // Arrange
        when(bankRepository.findBySwiftCode(swiftCode)).thenReturn(Optional.of(bank));

        // Act
        bankService.delete(swiftCode);

        // Assert
        verify(bankRepository).delete(bank);
    }
}