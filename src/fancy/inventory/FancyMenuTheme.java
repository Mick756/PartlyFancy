package fancy.inventory;

public interface FancyMenuTheme {

    /*
    Name for config.yml specification
     */
    String name();
    
    void apply();

    /*
    If the theme involves moving parts, or is it still
     */
    boolean isStatic();

}
