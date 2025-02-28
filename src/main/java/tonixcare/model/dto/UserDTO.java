package tonixcare.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class UserDTO {

    private long userId;  // Přidání userId pro úpravy a mazání

    @NotBlank(message = "Vyplňte jméno")
    private String firstName;  // Přidání jména

    @NotBlank(message = "Vyplňte příjmení")
    private String lastName;  // Přidání příjmení

    @NotBlank(message = "Vyplňte telefonní číslo")
    @Pattern(regexp = "^[0-9]{9}$", message = "Telefonní číslo musí obsahovat 9 číslic")
    private String phoneNumber;

    @Email(message = "Vyplňte validní email")
    @NotBlank(message = "Vyplňte uživatelský email")
    private String email;

    private String password;  // Heslo není povinné při editaci

    private String confirmPassword;  // Potvrzení hesla není povinné při editaci

    // region: getters and setters

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    // endregion

}
