package fr.duchemin.sir.kanban.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "The label cannot be blank.")
    @Size(message = "The label must contain between 3 and 16 characters.", min = 3, max = 16)
    @Column(name = "label", nullable = false)
    private String label;

    @ManyToMany(targetEntity = Card.class)
    private List<Card> cards;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public void addCard(Card card) {
        if (!this.cards.contains(card)) {
            this.cards.add(card);
        }
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }
}
