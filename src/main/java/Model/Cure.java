package Model;

public class Cure {
    private String virusType;
    private String cureState;

    public Cure(String virusType, String cureState) {
        this.virusType = virusType;
        this.cureState = cureState;
    }

    public String getCureState() {
        return cureState;
    }

    public String getType() {
        return virusType;
    }
}
