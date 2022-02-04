package fr.duchemin.sir.kanban.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "The street cannot be blank.")
    @Size(message = "The street must not contain more than 30 characters.", max = 30)
    @Column(name = "street", nullable = false)
    private String street;

    @NotBlank(message = "The zip code cannot be blank.")
    @Size(message = "The zip code must not contain more than 15 characters.", max = 15)
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @NotBlank(message = "The city cannot be blank.")
    @Size(message = "The city must not contain more than 20 characters.", max = 20)
    @Column(name = "city", nullable = false)
    private String city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
