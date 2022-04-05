package fr.duchemin.sir.kanban.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "The first name cannot be blank.")
    @Size(message = "The first name must not contain more than 20 characters.", max = 20)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "The last name cannot be blank.")
    @Size(message = "The last name must not contain more than 20 characters.", max = 20)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToOne(targetEntity = Address.class, cascade = {CascadeType.ALL})
    private Address address;

    @OneToMany(mappedBy = "user")
    private List<Card> cards;

    public User() {
        this.cards = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }
}
