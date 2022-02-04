package fr.duchemin.sir.kanban.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "The label cannot be blank.")
    @Size(message = "The label must contain between 3 and 16 characters.", min = 3, max = 16)
    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "position", nullable = false)
    private int position;

    @OneToMany(targetEntity = Card.class, mappedBy = "section", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Card> cards;

    @ManyToOne(targetEntity = Board.class, optional = false)
    private Board board;

    public Section() {
        this.cards = new ArrayList<>();
    }

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        if (!this.cards.contains(card)) {
            card.setSection(this);
            this.cards.add(card);
        }
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return this.label + " " + this.position + " " + this.board.getLabel();
    }
}
