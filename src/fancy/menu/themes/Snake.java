package fancy.menu.themes;

import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import fancy.menu.themes.types.Static;
import org.bukkit.Material;

public class Snake implements FancyMenuTheme, Static {

    @Override
    public String name() {
        return "SNAKE";
    }

    @Override
    public void apply() {

    }

    @Override
    public void clear() {

    }

    @Override
    public FancyMenuLoader.FancyMenu menu() {
        return null;
    }

    @Override
    public FancyMenuTheme setMenu(FancyMenuLoader.FancyMenu menu) {
        return null;
    }

    @Override
    public Material item() {
        return null;
    }

    @Override
    public FancyMenuTheme setItem(Material material) {
        return null;
    }
}
