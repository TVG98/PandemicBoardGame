package Model;

public class Cure {
    private final VirusType virusType;
    private CureState cureState = CureState.ACTIVE;

    public Cure(VirusType virusType) {
        this.virusType = virusType;
    }

    public CureState getCureState() {
        return cureState;
    }

    public void setCureState(CureState cureState) {
        this.cureState = cureState;
    }

    public VirusType getType() {
        return virusType;
    }
}
