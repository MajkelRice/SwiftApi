package github.ryz.swiftapi.configuration;

import github.ryz.swiftapi.exception.SwiftCodesCSVNotFoundException;
import github.ryz.swiftapi.model.Bank;
import github.ryz.swiftapi.model.Country;
import github.ryz.swiftapi.repository.BankRepository;
import github.ryz.swiftapi.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataLoaderTest {

    @Mock
    private BankRepository bankRepository;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private DataLoader dataLoader;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testRun_DataLoadedSuccessfully() throws SwiftCodesCSVNotFoundException {
        when(bankRepository.count()).thenReturn(0L);
        when(countryRepository.findByName("United States")).thenReturn(Optional.empty());
        when(countryRepository.findByName("ALBANIA")).thenReturn(Optional.empty());  // Mock for other country
        when(countryRepository.save(any(Country.class))).thenReturn(new Country("United States", "US"));
        when(bankRepository.save(any(Bank.class))).thenReturn(new Bank());  // Ensure bank save is mocked

        dataLoader.run();

        verify(bankRepository, times(1)).save(any(Bank.class));
        verify(countryRepository, times(1)).save(any(Country.class));
    }

    @Test
    public void testRun_DataAlreadyExists() throws SwiftCodesCSVNotFoundException {
        when(bankRepository.count()).thenReturn(1L);

        dataLoader.run();

        verify(bankRepository, never()).save(any(Bank.class));
        verify(countryRepository, never()).save(any(Country.class));
    }

    @Test
    public void testSwiftCodesCSVNotFoundException() {
        when(bankRepository.count()).thenReturn(0L);

        try {
            dataLoader.run();
        } catch (SwiftCodesCSVNotFoundException e) {
            // Verify the exception is thrown
            assert e.getMessage().equals("swift_codes.csv not found in resources.");
        }
    }
}
