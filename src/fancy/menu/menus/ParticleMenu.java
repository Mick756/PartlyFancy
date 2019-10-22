package fancy.menu.menus;

import fancy.cosmetics.particles.AuraParticle;
import fancy.cosmetics.particles.CrownParticle;
import fancy.cosmetics.particles.OrbParticle;
import fancy.cosmetics.particles.WingsParticle;
import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import fancy.util.FancyUtil;
import fancy.util.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

public class ParticleMenu implements FancyMenuLoader.FancyMenu {

    private Inventory inv;

    public ParticleMenu() {
        this.inv = Bukkit.createInventory(null, 54, this.getName());
        this.getTheme().apply();
    }

    @Override
    public Integer inventoryId() {
        return FancyMenuLoader.FancyMenuIds.PARTICLE.getId();
    }

    @Override
    public FancyMenuLoader.FancyMenu getInstance() {
        return this;
    }

    @Override
    public Inventory getInventory() {
        inv.setItem(10,
                NBTUtil.setItemTag(
                        CrownParticle.item(),
                        FancyMenuLoader.FancyMenuIds.PARTICLE_CROWN.getId(),
                        "PartlyFancy", "openinv"
                ));
        inv.setItem(13,
                NBTUtil.setItemTag(
                        AuraParticle.item(),
                        FancyMenuLoader.FancyMenuIds.PARTICLE_AURA.getId(),
                        "PartlyFancy", "openinv"
                ));
        inv.setItem(16,
                NBTUtil.setItemTag(
                        WingsParticle.item(),
                        FancyMenuLoader.FancyMenuIds.PARTICLE_WINGS.getId(),
                        "PartlyFancy", "openinv"
                ));
        inv.setItem(29,
                NBTUtil.setItemTag(
                        OrbParticle.item(),
                        FancyMenuLoader.FancyMenuIds.PARTICLE_ORB.getId(),
                        "PartlyFancy", "openinv"
                ));
        inv.setItem(48,
                NBTUtil.setItemTag(
                        FancyUtil.createItemStack(Material.ARROW, 1, "&cGo Back", null, "&7Go back a menu.."),
                        FancyMenuLoader.FancyMenuIds.MAIN.getId(),
                        "PartlyFancy", "goback"
                ));
        inv.setItem(49, MenuItemConstant.CLOSE_MENU.getItem());

        return this.inv;
    }

    @Override
    public String getName() {
        return ChatColor.BLACK + "Fancy Particle Menu";
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.menu.particle", "Permission to the particle fancy menu.");
    }

    @Override
    public FancyMenuTheme getTheme() {
        return FancyMenuTheme.parseTheme(this, "menu.particle");
    }
}
