package Model;

public class Cure {
    private final VirusType virusType;
    private String cureState = "active";

    public Cure(VirusType virusType) {
        this.virusType = virusType;
    }

    public String getCureState() {
        return cureState;
    }

    public void setCureState(String cureState) {
        this.cureState = cureState;
    }

    public VirusType getType() {
        return virusType;
    }
}
