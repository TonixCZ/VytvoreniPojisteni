package tonixcare.model.dto;

import jakarta.validation.constraints.Pattern;
import tonixcare.entities.InsuredEntity;

import java.time.LocalDateTime;

public class InsuranceDTO {

    private long insuranceId;
    private String insuranceType;
    private double insuredAmount;

    private InsuredEntity insured;  // Pojištěnec

    @Pattern(regexp = "\\+?[0-9]{3}-?[0-9]{3}-?[0-9]{3}", message = "Neplatný formát telefonního čísla")
    private String insuredPhone;

    private String insuredAddress;

    private LocalDateTime createdAt;  // Změna na LocalDateTime pro lepší manipulaci s daty a časy

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

    public double getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(double insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

    public InsuredEntity getInsured() {
        return insured;
    }

    public void setInsured(InsuredEntity insured) {
        this.insured = insured;
    }

    public String getInsuredPhone() {
        return insuredPhone;
    }

    public void setInsuredPhone(String insuredPhone) {
        this.insuredPhone = insuredPhone;
    }

    public String getInsuredAddress() {
        return insuredAddress;
    }

    public void setInsuredAddress(String insuredAddress) {
        this.insuredAddress = insuredAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
