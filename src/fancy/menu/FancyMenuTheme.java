package fancy.menu;

import fancy.PartlyFancy;
import fancy.menu.themes.Rainbow;
import fancy.menu.themes.Static;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public interface FancyMenuTheme {

    List<FancyMenuTheme> themes = new ArrayList<>();

    /*
    Name for config.yml specification
     */
    String name();
    
    void apply();

    void clear();

    /*
    If the theme involves moving parts, or is it still
     */
    boolean isStatic();

    static void addTheme(FancyMenuTheme theme) {
        themes.add(theme);
    }

    /**
     * Parse a FancyMenuTheme from menu path to config.
     * @param path Path to menu. Ex: 'menu.main'
     * @return A FancyMenuTheme. 'STATIC' with Material.AIR if not found.
     */
    static FancyMenuTheme parseTheme(FancyMenuLoader.FancyMenu m, String path) {

        String theme = PartlyFancy.getValue(path + ".theme");

        switch (theme) {

            case "RAINBOW":

                List<String> matNames = (List<String>) PartlyFancy.getListValue(path + ".items");
                if (matNames != null) {
                    List<Material> materials = new ArrayList<>();

                    for (String matName : matNames) {
                        try {
                            materials.add(Material.valueOf(matName.toUpperCase()));
                        } catch (Exception ex) {
                            PartlyFancy.getInstance().getLogger().severe("Error in configuration. " + matName + " at " + path + ".items is not a valid material.");
                        }
                    }

                    return new Rainbow(m, materials, true);
                } else {
                    break;
                }

            case "STATIC":

                String matName = PartlyFancy.getValue(path + ".items");
                if (matName != null) {

                        try {
                            Static s = new Static(m, Material.valueOf(matName.toUpperCase()), true);
                            return s;
                        } catch (Exception ex) {
                            PartlyFancy.getInstance().getLogger().severe("Error in configuration. " + matName + " at " + path + ".items is not a valid material.");
                        }
                } else {
                    break;
                }

        }
        return new Static(m, Material.AIR, true);
    }

}
