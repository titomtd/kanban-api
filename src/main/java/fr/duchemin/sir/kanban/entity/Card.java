package fr.duchemin.sir.kanban.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "The label cannot be blank.")
    @Size(message = "The label must contain between 3 and 16 characters.", min = 3, max = 16)
    @Column(name = "label", nullable = false)
    private String label;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;

    @Column(name = "estimated_time")
    private int estimatedTime;

    @ManyToOne(targetEntity = Address.class, cascade = {CascadeType.ALL})
    private Address address;

    @Column(name = "url")
    private String url;

    @Column(name = "note")
    private String note;

    @ManyToOne(targetEntity = Section.class, optional = false)
    private Section section;

    @ManyToMany(targetEntity = Tag.class, mappedBy = "cards", cascade = {CascadeType.PERSIST})
    private List<Tag> tags;

    public Card() {
        this.tags = new ArrayList<>();
        this.address = new Address();
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if (!this.tags.contains(tag)) {
            tag.addCard(this);
            this.tags.add(tag);
        }
    }

    public void removeTag(Tag tag) {
        if (this.tags.contains(tag)) {
            tag.removeCard(this);
            this.tags.remove(tag);
        }
    }

    public void removeAllTags() {
        List<Tag> toRemove = new ArrayList<>(this.tags);
        for (Tag tag : toRemove) {
            this.removeTag(tag);
        }
    }
}
