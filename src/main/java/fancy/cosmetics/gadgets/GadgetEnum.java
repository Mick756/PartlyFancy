package fancy.cosmetics.gadgets;

import fancy.cosmetics.Gadget;

public enum GadgetEnum {
    ENDERBOW(new EnderBow());

    private Gadget gadget;
    GadgetEnum(Gadget g) {
        this.gadget = g;
    }

    public Gadget getGadget() {
        return this.gadget.newInstance();
    }

}
