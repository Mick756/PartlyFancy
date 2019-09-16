package fancy.util.particlelib;

import com.google.common.collect.Maps;
import fancy.util.NBTUtil;
import fancy.util.particlelib.data.ParticleData;
import fancy.util.particlelib.data.color.NoteColor;
import fancy.util.particlelib.data.color.ParticleColor;
import fancy.util.particlelib.data.color.RegularColor;
import fancy.util.particlelib.data.texture.BlockTexture;
import fancy.util.particlelib.data.texture.ItemTexture;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum ParticleEffect {

    BARRIER(version -> version < 8 ? "NONE" : (version < 13 ? "BARRIER" : "barrier")),
    BLOCK_CRACK(version -> version < 8 ? "NONE" : (version < 13 ? "BLOCK_CRACK" : "block"), PropertyType.REQUIRES_BLOCK),
    BLOCK_DUST(version -> version < 8 ? "NONE" : (version < 13 ? "BLOCK_DUST" : "falling_dust"), PropertyType.DIRECTIONAL, PropertyType.REQUIRES_BLOCK),
    BUBBLE_COLUMN_UP(version -> version < 13 ? "NONE" : "bubble_column_up", PropertyType.DIRECTIONAL),
    BUBBLE_POP(version -> version < 13 ? "NONE" : "bubble_pop", PropertyType.DIRECTIONAL),
    CAMPFIRE_COSY_SMOKE(version -> version < 14 ? "NONE" : "campfire_cosy_smoke", PropertyType.DIRECTIONAL),
    CAMPFIRE_SIGNAL_SMOKE(version -> version < 14 ? "NONE" : "campfire_signal_smoke", PropertyType.DIRECTIONAL),
    CLOUD(version -> version < 8 ? "NONE" : (version < 13 ? "CLOUD" : "cloud"), PropertyType.DIRECTIONAL),
    COMPOSTER(version -> version < 14 ? "NONE" : "composter"),
    CRIT(version -> version < 8 ? "NONE" : (version < 13 ? "CRIT" : "crit"), PropertyType.DIRECTIONAL),
    CRIT_MAGIC(version -> version < 8 ? "NONE" : (version < 13 ? "CRIT_MAGIC" : "enchanted_hit"), PropertyType.DIRECTIONAL),
    CURRENT_DOWN(version -> version < 13 ? "NONE" : "current_down"),
    DAMAGE_INDICATOR(version -> version < 9 ? "NONE" : (version < 13 ? "DAMAGE_INDICATOR" : "damage_indicator"), PropertyType.DIRECTIONAL),
    DOLPHIN(version -> version < 13 ? "NONE" : "dolphin"),
    DRAGON_BREATH(version -> version < 9 ? "NONE" : (version < 13 ? "DRAGON_BREATH" : "dragon_breath"), PropertyType.DIRECTIONAL),
    DRIP_LAVA(version -> version < 8 ? "NONE" : (version < 13 ? "DRIP_WATER" : "dripping_lava")),
    DRIP_WATER(version -> version < 8 ? "NONE" : (version < 13 ? "DRIP_WATER" : "dripping_water")),
    ENCHANTMENT_TABLE(version -> version < 8 ? "NONE" : (version < 13 ? "ENCHANTMENT_TABLE" : "enchant"), PropertyType.DIRECTIONAL),
    END_ROD(version -> version < 9 ? "NONE" : (version < 13 ? "END_ROD" : "end_rod"), PropertyType.DIRECTIONAL),
    EXPLOSION_HUGE(version -> version < 8 ? "NONE" : (version < 13 ? "EXPLOSION_HUGE" : "explosion_emitter")),
    EXPLOSION_LARGE(version -> version < 8 ? "NONE" : (version < 13 ? "EXPLOSION_LARGE" : "explosion")),
    EXPLOSION_NORMAL(version -> version < 8 ? "NONE" : (version < 13 ? "EXPLOSION_NORMAL" : "poof"), PropertyType.DIRECTIONAL),
    FALLING_DUST(version -> version < 10 ? "NONE" : (version < 13 ? "FALLING_DUST" : "falling_dust"), PropertyType.REQUIRES_BLOCK),
    FIREWORKS_SPARK(version -> version < 8 ? "NONE" : (version < 13 ? "FIREWORKS_SPARK" : "firework"), PropertyType.DIRECTIONAL),
    FLAME(version -> version < 8 ? "NONE" : (version < 13 ? "FLAME" : "flame"), PropertyType.DIRECTIONAL),
    FOOTSTEP(version -> version > 8 && version < 13 ? "FOOTSTEP" : "NONE"),
    HEART(version -> version < 8 ? "NONE" : (version < 13 ? "HEART" : "heart")),
    ITEM_CRACK(version -> version < 8 ? "NONE" : (version < 13 ? "ITEM_CRACK" : "item"), PropertyType.DIRECTIONAL, PropertyType.REQUIRES_ITEM),
    LAVA(version -> version < 8 ? "NONE" : (version < 13 ? "LAVA" : "lava")),
    MOB_APPEARANCE(version -> version < 8 ? "NONE" : (version < 13 ? "MOB_APPEARANCE" : "elder_guardian")),
    NAUTILUS(version -> version < 13 ? "NONE" : "nautilus", PropertyType.DIRECTIONAL),
    NOTE(version -> version < 8 ? "NONE" : (version < 13 ? "NOTE" : "note"), PropertyType.COLORABLE),
    PORTAL(version -> version < 8 ? "NONE" : (version < 13 ? "PORTAL" : "portal"), PropertyType.DIRECTIONAL),
    REDSTONE(version -> version < 8 ? "NONE" : (version < 13 ? "REDSTONE" : "dust"), PropertyType.COLORABLE),
    SLIME(version -> version < 8 ? "NONE" : (version < 13 ? "SLIME" : "item_slime")),
    SMOKE_LARGE(version -> version < 8 ? "NONE" : (version < 13 ? "SMOKE_LARGE" : "large_smoke"), PropertyType.DIRECTIONAL),
    SMOKE_NORMAL(version -> version < 8 ? "NONE" : (version < 13 ? "SMOKE_NORMAL" : "smoke"), PropertyType.DIRECTIONAL),
    SNEEZE(version -> version < 14 ? "NONE" : "sneeze", PropertyType.DIRECTIONAL),
    SNOWBALL(version -> version < 8 ? "NONE" : (version < 13 ? "SNOWBALL" : "item_snowball")),
    SNOW_SHOVEL(version -> version < 8 ? "NONE" : (version < 13 ? "SNOW_SHOVEL" : "poof"), PropertyType.DIRECTIONAL),
    SPELL(version -> version < 8 ? "NONE" : (version < 13 ? "SPELL" : "effect")),
    SPELL_INSTANT(version -> version < 8 ? "NONE" : (version < 13 ? "SPELL_INSTANT" : "instant_effect")),
    SPELL_MOB(version -> version < 8 ? "NONE" : (version < 13 ? "SPELL_MOB" : "entity_effect"), PropertyType.COLORABLE),
    SPELL_MOB_AMBIENT(version -> version < 8 ? "NONE" : (version < 13 ? "SPELL_MOB_AMBIENT" : "ambient_entity_effect"), PropertyType.COLORABLE),
    SPELL_WITCH(version -> version < 8 ? "NONE" : (version < 13 ? "SPELL_WITCH" : "witch")),
    SPIT(version -> version < 11 ? "NONE" : (version < 13 ? "SPIT" : "spit")),
    SQUID_INK(version -> version < 13 ? "NONE" : "squid_ink", PropertyType.DIRECTIONAL),
    SUSPENDED(version -> version < 8 ? "NONE" : (version < 13 ? "SUSPENDED" : "underwater"), PropertyType.REQUIRES_WATER),
    SUSPENDED_DEPTH(version -> version > 8 && version < 13 ? "SUSPENDED_DEPTH" : "NONE", PropertyType.DIRECTIONAL),
    SWEEP_ATTACK(version -> version < 8 ? "NONE" : (version < 13 ? "SWEEP_ATTACK" : "sweep_attack"), PropertyType.RESIZEABLE),
    TOTEM(version -> version < 11 ? "NONE" : (version < 13 ? "TOTEM" : "totem_of_undying"), PropertyType.DIRECTIONAL),
    TOWN_AURA(version -> version < 8 ? "NONE" : (version < 13 ? "TOWN_AURA" : "mycelium"), PropertyType.DIRECTIONAL),
    VILLAGER_ANGRY(version -> version < 8 ? "NONE" : (version < 13 ? "VILLAGER_ANGRY" : "angry_villager")),
    VILLAGER_HAPPY(version -> version < 8 ? "NONE" : (version < 13 ? "VILLAGER_HAPPY" : "happy_villager"), PropertyType.DIRECTIONAL),
    WATER_BUBBLE(version -> version < 8 ? "NONE" : (version < 13 ? "WATER_BUBBLE" : "bubble"), PropertyType.DIRECTIONAL, PropertyType.REQUIRES_WATER),
    WATER_DROP(version -> version > 8 && version < 13 ? "WATER_DROP" : "NONE"),
    WATER_SPLASH(version -> version < 8 ? "NONE" : (version < 13 ? "WATER_SPLASH" : "splash"), PropertyType.DIRECTIONAL),
    WATER_WAKE(version -> version < 8 ? "NONE" : (version < 13 ? "WATER_WAKE" : "fishing"), PropertyType.DIRECTIONAL);

    private IntFunction<String> fieldNameMapper;
    private PropertyType[] properties;
    public static final ParticleEffect[] VALUES = values();
    public static final Map<ParticleEffect, Object> NMS_EFFECTS = Maps.newHashMap();

    static {
        Arrays.stream(VALUES).filter(effect -> !effect.getFieldName().equals("NONE")).forEach(effect -> NMS_EFFECTS.put(effect, effect.getNMSObject()));
    }

    ParticleEffect(IntFunction<String> fieldNameMapper, PropertyType... properties) {
        this.fieldNameMapper = fieldNameMapper;
        this.properties = properties;
    }

    public String getFieldName() {
        return fieldNameMapper.apply(ParticleConstants.version);
    }

    public boolean hasProperty(PropertyType propertyType) {
        return propertyType != null && Arrays.stream(properties).anyMatch(property -> property.equals(propertyType));
    }

    public boolean isCorrectData(ParticleData data) {
        if (data == null)
            return true;
        if (data instanceof ParticleColor)
            return isCorrectColor(((ParticleColor) data));
        if (data instanceof BlockTexture)
            return hasProperty(PropertyType.REQUIRES_BLOCK);
        return data instanceof ItemTexture && hasProperty(PropertyType.REQUIRES_ITEM);
    }

    public boolean isCorrectColor(ParticleColor color) {
        return hasProperty(PropertyType.COLORABLE) && (this.equals(ParticleEffect.NOTE) ? color instanceof NoteColor : color instanceof RegularColor);
    }

    public Object getNMSObject() {
        if (NMS_EFFECTS.containsKey(this))
            return NMS_EFFECTS.get(this);
        String fieldName = getFieldName();
        if (fieldName.equals("NONE"))
            return null;
        if (ParticleConstants.version < 13)
            return Arrays.stream(ParticleConstants.PARTICLE_ENUM.getEnumConstants()).filter(effect -> effect.toString().equals(fieldName)).findFirst().orElse(null);
        else try {
            return ParticleConstants.REGISTRY_GET_METHOD.invoke(ParticleConstants.PARTICLE_TYPE_REGISTRY, NBTUtil.getMinecraftKey(fieldName));
        } catch (Exception ignored) {
        }
        return null;
    }

    public void display(Location location, ParticleColor color, Player... players) {
        display(location, 0f, 0f, 0f, 1f, 0, color, players);
    }

    public void display(Location location, Color color, Player... players) {
        display(location, new RegularColor(color), players);
    }

    public void display(Location location, ParticleColor color, Predicate filter) {
        display(location, 0f, 0f, 0f, 1f, 0, color, filter);
    }

    public void display(Location location, Color color, Predicate filter) {
        display(location, new RegularColor(color), filter);
    }

    public void display(Location location, ParticleColor color, Collection<? extends Player> players) {
        display(location, 0f, 0f, 0f, 1f, 0, color, players);
    }

    public void display(Location location, Color color, Collection<? extends Player> players) {
        display(location, new RegularColor(color), players);
    }

    public void display(Location location, ParticleColor color) {
        display(location, 0f, 0f, 0f, 1f, 0, color);
    }

    public void display(Location location, Color color) {
        display(location, new RegularColor(color));
    }

    public void display(Location location, Player... players) {
        display(location, 0f, 0f, 0f, 0f, 1, null, players);
    }

    public void display(Location location, Predicate filter) {
        display(location, 0f, 0f, 0f, 0f, 1, null, filter);
    }

    public void display(Location location, Collection<? extends Player> players) {
        display(location, 0f, 0f, 0f, 0f, 1, null, players);
    }

    public void display(Location location) {
        display(location, 0f, 0f, 0f, 0f, 1, null, Bukkit.getOnlinePlayers());
    }

    public void display(Location location, Vector vector, float speed, int amount, ParticleData data, Player... players) {
        display(location, (float) vector.getX(), (float) vector.getY(), (float) vector.getZ(), speed, amount, data, players);
    }

    public void display(Location location, Vector vector, float speed, int amount, ParticleData data, Predicate filter) {
        display(location, (float) vector.getX(), (float) vector.getY(), (float) vector.getZ(), speed, amount, data, filter);
    }

    public void display(Location location, Vector vector, float speed, int amount, ParticleData data, Collection<? extends Player> players) {
        display(location, (float) vector.getX(), (float) vector.getY(), (float) vector.getZ(), speed, amount, data, players);
    }

    public void display(Location location, Vector vector, float speed, int amount, ParticleData data) {
        display(location, (float) vector.getX(), (float) vector.getY(), (float) vector.getZ(), speed, amount, data);
    }

    public void display(Location location, float offsetX, float offsetY, float offsetZ, float speed, int amount, ParticleData data, Player... players) {
        ArrayList<Player> playerList = Arrays.stream(players).collect(Collectors.toCollection(ArrayList::new));
        display(location, offsetX, offsetY, offsetZ, speed, amount, data, playerList);
    }

    public void display(Location location, float offsetX, float offsetY, float offsetZ, float speed, int amount, ParticleData data, Predicate<Player> filter) {
        ArrayList<Player> players = Bukkit.getOnlinePlayers().stream().filter(filter).collect(Collectors.toCollection(ArrayList::new));
        display(location, offsetX, offsetY, offsetZ, speed, amount, data, players);
    }

    public void display(Location location, float offsetX, float offsetY, float offsetZ, float speed, int amount, ParticleData data) {
        display(location, offsetX, offsetY, offsetZ, speed, amount, data, Bukkit.getOnlinePlayers());
    }

    public void display(Location location, float offsetX, float offsetY, float offsetZ, float speed, int amount, ParticleData data, Collection<? extends Player> players) {
        if (!isCorrectData(data)) {
            return;
        }
        if (data != null) {
            data.setEffect(this);
        }
        ParticlePacket packet = new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, data);
        Object nmsPacket = packet.createPacket(location);
        players.forEach(player -> NBTUtil.sendPacket(player, nmsPacket));
    }

}
