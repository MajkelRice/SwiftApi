package github.ryz.swiftapi.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String bankName;

    @ManyToOne
    private Country country;

    private boolean isHeadquarter;
    private String swiftCode;

    public Bank() {}

    public Bank(String address, String bankName, Country country, boolean isHeadquarter, String swiftCode) {
        this.address = address;
        this.bankName = bankName;
        this.country = country;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
    }
}
