package github.ryz.swiftapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;

    public Country() {}

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
