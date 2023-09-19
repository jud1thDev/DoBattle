package DoBattle.domain;
public class PartnerDTO {
    private String partnerUsername;
    private Double partnerPercent;

    public PartnerDTO() {
    }

    public PartnerDTO(String partnerUsername, Double partnerPercent) {
        this.partnerUsername = partnerUsername;
        this.partnerPercent = partnerPercent;
    }

    public String getPartnerUsername() {
        return partnerUsername;
    }

    public void setPartnerUsername(String partnerUsername) {
        this.partnerUsername = partnerUsername;
    }

    public Double getPartnerPercent() {
        return partnerPercent;
    }

    public void setPartnerPercent(Double partnerPercent) {
        this.partnerPercent = partnerPercent;
    }
}

