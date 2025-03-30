package github.ryz.swiftapi.service;

import github.ryz.swiftapi.exception.CountryNotFoundException;
import github.ryz.swiftapi.model.Country;
import github.ryz.swiftapi.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryService countryService;

    @Test
    void getCountryByCode_shouldReturnCountry_whenCountryExists() {
        // Arrange
        String countryCode = "US";
        Country mockCountry = new Country("United States", countryCode);
        when(countryRepository.findByCode(countryCode)).thenReturn(Optional.of(mockCountry));

        // Act
        Country result = countryService.getCountryByCode(countryCode);

        // Assert
        assertNotNull(result);
        assertEquals(countryCode, result.getCode());
        assertEquals("United States", result.getName());
        verify(countryRepository, times(1)).findByCode(countryCode);
    }

    @Test
    void getCountryByCode_shouldThrowException_whenCountryNotFound() {
        // Arrange
        String countryCode = "XX";
        when(countryRepository.findByCode(countryCode)).thenReturn(Optional.empty());

        // Act & Assert
        CountryNotFoundException exception = assertThrows(CountryNotFoundException.class,
                () -> countryService.getCountryByCode(countryCode));

        assertEquals("Country with code=[XX] is not found.", exception.getMessage());
        verify(countryRepository, times(1)).findByCode(countryCode);
    }

    @Test
    void getCountryByCode_shouldThrowException_whenCodeIsNull() {
        // Arrange
        when(countryRepository.findByCode(null)).thenReturn(Optional.empty());

        // Act & Assert
        CountryNotFoundException exception = assertThrows(CountryNotFoundException.class,
                () -> countryService.getCountryByCode(null));

        assertEquals("Country with code=[null] is not found.", exception.getMessage());
        verify(countryRepository, times(1)).findByCode(null);
    }

    @Test
    void getCountryByCode_shouldThrowException_whenCodeIsEmpty() {
        // Arrange
        String emptyCode = "";
        when(countryRepository.findByCode(emptyCode)).thenReturn(Optional.empty());

        // Act & Assert
        CountryNotFoundException exception = assertThrows(CountryNotFoundException.class,
                () -> countryService.getCountryByCode(emptyCode));

        assertEquals("Country with code=[] is not found.", exception.getMessage());
        verify(countryRepository, times(1)).findByCode(emptyCode);
    }
}