package fr.duchemin.sir.kanban.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserInsertDTO {

    private Long id;

    @NotBlank(message = "The first name cannot be blank.")
    @Size(message = "The first name must not contain more than 20 characters.", max = 20)
    private String firstName;

    @NotBlank(message = "The last name cannot be blank.")
    @Size(message = "The last name must not contain more than 20 characters.", max = 20)
    private String lastName;

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
}
