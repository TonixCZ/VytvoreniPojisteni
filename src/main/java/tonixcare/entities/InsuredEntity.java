package tonixcare.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class InsuredEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long insuredId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    // Vztah mezi pojištěncem a pojištěním
    @OneToMany(mappedBy = "insured", fetch = FetchType.LAZY)
    private List<InsuranceEntity> insurances;

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
}
