package tonixcare.model.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import tonixcare.entities.InsuranceEntity;
import tonixcare.entities.InsuredEntity;
import tonixcare.entities.UserEntity;

public class InsuredDTO extends InsuredEntity {

    private long insuredId;

    @NotBlank(message = "Vyplňte jméno")
    private String firstName;

    @NotBlank(message = "Vyplňte příjmení")
    private String lastName;

    @NotBlank(message = "Vyplňte telefonní číslo")
    @Pattern(regexp = "^[0-9]{9}$", message = "Telefonní číslo musí obsahovat 9 číslic")
    private String phoneNumber;

    private String email;

    private List<InsuranceEntity> insurances;


    private UserEntity user;

    // Gettery a settery
    public long getInsuredId() {
        return insuredId;
    }

    public void setInsuredId(long insuredId) {
        this.insuredId = insuredId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<InsuranceEntity> getInsurances() {
        return insurances;
    }

    public void setInsurances(List<InsuranceEntity> insurances) {
        this.insurances = insurances;
    }

    // Getter pro userId (z uživatele)
    public long getUserId() {
        if (user != null) {
            return user.getUserId();
        }
        return 0; // Pokud uživatel není nastaven, vrátíme defaultní hodnotu
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
