package fancy.menu;

import fancy.PartlyFancy;
import fancy.menu.themes.Rainbow;
import fancy.menu.themes.Solid;
import fancy.menu.themes.types.MultiColor;
import fancy.menu.themes.types.Static;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public interface FancyMenuTheme {
    /*
    Name for config.yml specification
     */
    String name();
    
    void apply();

    void clear();

    FancyMenuLoader.FancyMenu menu();

    FancyMenuTheme setMenu(FancyMenuLoader.FancyMenu menu);



    static FancyMenuTheme getTheme(FancyMenuLoader.FancyMenu m, String value) {
        for (FancyMenuTheme theme : FancyMenuLoader.themes) {
            if (theme.name().equalsIgnoreCase(value)) {
                return theme.setMenu(m);
            }
        }
        return null;
    }

    /**
     * Parse a FancyMenuTheme from menu path to config.
     * @param path Path to menu. Ex: 'menu.main'
     * @return A FancyMenuTheme. 'STATIC' with Material.AIR if not found.
     */
    static FancyMenuTheme parseTheme(FancyMenuLoader.FancyMenu m, String path) {

        FancyMenuTheme theme = getTheme(m, PartlyFancy.getValue(path + ".theme"));

        if (theme != null) {

            if (theme instanceof MultiColor) {

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

                    return new Rainbow(true).setItems(materials).setMenu(m);
                }
            } else if (theme instanceof Static) {

                String matName = PartlyFancy.getValue(path + ".items");

                if (matName != null) {

                    try {
                        return new Solid(true).setItem(Material.valueOf(matName.toUpperCase())).setMenu(m);
                    } catch (Exception ex) {
                        PartlyFancy.getInstance().getLogger().severe("Error in configuration. " + matName + " at " + path + ".items is not a valid material.");
                    }
                }
            }
        } else {
            Bukkit.getLogger().info("Null ");
        }
        return new Solid(true).setItem(Material.AIR).setMenu(m);
    }

}
