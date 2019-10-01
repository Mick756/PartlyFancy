package fancy.menu.themes.types;

import fancy.menu.FancyMenuTheme;
import org.bukkit.Material;

import java.util.List;

public interface MultiColor {

    Material[] item();

    FancyMenuTheme setItems(List<Material> materials);

}
