package fr.duchemin.sir.kanban.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "The label cannot be blank.")
    @Size(message = "The label must contain between 3 and 16 characters.", min = 3, max = 16)
    @Column(name = "label", nullable = false)
    private String label;

    @OneToMany(targetEntity = Section.class, mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Section> sections;

    public Board() {
        this.sections = new ArrayList<>();
        Section waiting = new Section();
        waiting.setLabel("Waiting");
        waiting.setPosition(0);
        this.addSection(waiting);
        Section doing = new Section();
        doing.setLabel("Doing");
        doing.setPosition(1);
        this.addSection(doing);
        Section done = new Section();
        done.setLabel("Done");
        done.setPosition(2);
        this.addSection(done);
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

    public List<Section> getSections() {
        sections.sort(new Comparator<Section>() {
            @Override
            public int compare(Section sectionA, Section sectionB) {
                return sectionA.getPosition() - sectionB.getPosition();
            }
        });
        return this.sections;
    }

    public void addSection(Section section) {
        if (!this.sections.contains(section)) {
            section.setBoard(this);
            this.sections.add(section);
        }
    }

    public void removeSection(Section section) {
        this.sections.remove(section);
    }
}
