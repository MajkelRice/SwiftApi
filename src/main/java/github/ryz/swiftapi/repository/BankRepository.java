package github.ryz.swiftapi.repository;

import github.ryz.swiftapi.model.Bank;
import github.ryz.swiftapi.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findBySwiftCode(String swiftCode);
    List<Bank> findByCountry(Country country);
    List<Bank> findBySwiftCodeStartingWith(String swiftCode);
    boolean existsBySwiftCode(String swiftCode);
}

