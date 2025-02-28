package tonixcare.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
public class InsuranceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long insuranceId;

    @Column
    @NotNull(message = "Typ pojištění je povinný")
    private String insuranceType; // Typ pojištění (např. pojištění bytu, automobilu)


    @ManyToOne
    @JoinColumn(name = "insured_id", nullable = false)
    private InsuredEntity insured;

    @Column
    private double insuredAmount; // Pojištěná částka

    @Column(nullable = false)
    private String insuredAddress; // Adresa pojištěného



    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    //  metoda pro zobrazení celého jména pojištěnce
    public String getFullName() {
        if (insured != null) {
            return insured.getFirstName() + " " + insured.getLastName();
        }
        return "Neznámý pojištěnec"; // Případně jiná výchozí hodnota
    }

    // Gettery a settery
    public long getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(long insuranceId) {
        this.insuranceId = insuranceId;
    }



    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public InsuredEntity getInsured() {
        return insured;
    }

    public void setInsured(InsuredEntity insured) {
        this.insured = insured;
    }

    public double getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(double insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

    public String getInsuredAddress() {
        return insuredAddress;
    }

    public void setInsuredAddress(String insuredAddress) {
        this.insuredAddress = insuredAddress;
    }

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

}
