package Model;

public class Cure {
    private final VirusType virusType;
    private String cureState;

    public Cure(VirusType virusType, String cureState) {
        this.virusType = virusType;
        this.cureState = cureState;
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
