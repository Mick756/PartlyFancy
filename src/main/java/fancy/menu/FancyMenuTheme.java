package fancy.menu;

import api.builders.ItemStackBuilder;
import de.tr7zw.changeme.nbtapi.NBTItem;
import fancy.PartlyFancy;
import fancy.menu.themes.Solid;
import fancy.menu.themes.types.MultiColor;
import fancy.menu.themes.types.Static;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface FancyMenuTheme {

    String name();
    
    void apply();

    void clear();

    FancyMenuLoader.FancyMenu menu();

    FancyMenuTheme setMenu(FancyMenuLoader.FancyMenu menu);

    FancyMenuTheme newInstance();

    static FancyMenuTheme getTheme(String value) {
        
        for (FancyMenuTheme theme : FancyMenuLoader.themes) {
            if (theme.name().equalsIgnoreCase(value)) {
                return theme.newInstance();
            }
        }
        return null;
    }

    static FancyMenuTheme parseTheme(FancyMenuLoader.FancyMenu m, String path) {
        FancyMenuTheme theme = getTheme(PartlyFancy.getStringValue(path + ".theme"));
        
        if (theme != null) {
            if (theme instanceof MultiColor) {
                List<String> matNames = (List<String>) PartlyFancy.getListValue(path + ".items");
                if (matNames != null) {
                    List<ItemStack> stacks = new ArrayList<>();
                    for (String matName : matNames) {
                        Material mat;
                        
                        try {
                            mat = Material.valueOf(matName.toUpperCase());
                        } catch (IllegalArgumentException ex) {
                            PartlyFancy.getInstance().getLogger().severe("Error in configuration. " + path + ".items contains an invalid material.");
                            return null;
                        }
                        
                        NBTItem item = new NBTItem(new ItemStackBuilder(mat).setDisplayName(" ").build());
                        item.setString("action", "cancel");
                        
                        stacks.add(item.getItem());
                    }
                    
                    return ((MultiColor) theme).setItems(stacks).setMenu(m);
                }
            } else if (theme instanceof Static) {
                String matName = PartlyFancy.getStringValue(path + ".items");
                Material mat;
    
                try {
                    mat = Material.valueOf(matName.toUpperCase());
                } catch (IllegalArgumentException ex) {
                    PartlyFancy.getInstance().getLogger().severe("Error in configuration." + path + ".item contains an invalid material.");
                    return null;
                }
    
                NBTItem item = new NBTItem(new ItemStackBuilder(mat).setDisplayName(" ").build());
                item.setString("action", "cancel");
                
                return ((Static) theme).setItem(item.getItem()).setMenu(m);
            }
        }
        
        return new Solid().setItem(new ItemStack(Material.AIR)).setMenu(m);
    }

}
