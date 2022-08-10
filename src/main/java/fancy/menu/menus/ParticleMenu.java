package fancy.menu.menus;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fancy.cosmetics.particles.AuraParticle;
import fancy.cosmetics.particles.CrownParticle;
import fancy.cosmetics.particles.OrbParticle;
import fancy.cosmetics.particles.WingsParticle;
import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

public class ParticleMenu implements FancyMenuLoader.FancyMenu {

    private final Inventory inv;

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
        NBTItem crown = new NBTItem(CrownParticle.item());
        crown.setInteger("inventory", FancyMenuLoader.FancyMenuIds.PARTICLE_CROWN.getId());
        this.inv.setItem(10, crown.getItem());
    
        NBTItem aura = new NBTItem(AuraParticle.item());
        aura.setInteger("inventory", FancyMenuLoader.FancyMenuIds.PARTICLE_AURA.getId());
        this.inv.setItem(13, aura.getItem());
        
        NBTItem wings = new NBTItem(WingsParticle.item());
        wings.setInteger("inventory", FancyMenuLoader.FancyMenuIds.PARTICLE_WINGS.getId());
        this.inv.setItem(16, wings.getItem());
    
        NBTItem orb = new NBTItem(OrbParticle.item());
        orb.setInteger("inventory", FancyMenuLoader.FancyMenuIds.PARTICLE_ORB.getId());
        this.inv.setItem(29, orb.getItem());
        
        this.inv.setItem(48, MenuItemConstant.BACK_TO_MAIN_MENU.getItem());
        this.inv.setItem(49, MenuItemConstant.CLOSE_MENU.getItem());
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
