package fr.duchemin.sir.kanban.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TagDTO {

    private Long id;

    @NotBlank(message = "The label cannot be blank.")
    @Size(message = "The label must contain between 3 and 16 characters.", min = 3, max = 16)
    private String label;

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
}
