package Model;

public class Cure {
    private VirusType virusType;
    private CureState cureState = CureState.ACTIVE;

    public Cure() {}

    public Cure(VirusType virusType) {
        this.virusType = virusType;
    }

    public VirusType getVirusType() {
        return virusType;
    }

    public void setVirusType(VirusType virusType) {
        this.virusType = virusType;
    }

    public CureState getCureState() {
        return cureState;
    }

    public void setCureState(CureState cureState) {
        this.cureState = cureState;
    }
}
