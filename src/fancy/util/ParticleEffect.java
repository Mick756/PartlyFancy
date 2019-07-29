package fancy.util;

import fancy.PartlyFancy;
import fancy.effects.FancyEffect;
import fancy.util.ReflectionUtils.PackageType;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

public enum ParticleEffect {
    NORMAL_EXPLOSION("explode", 0, ParticleProperty.DIRECTIONAL),
    LARGE_EXPLOSION("largeexplode", 1),
    HUGE_EXPLOSION("hugeexplosion", 2),
    SPARKS("fireworksSpark", 3, ParticleProperty.DIRECTIONAL),
    WATER_BUBBLES("bubble", 4, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_WATER),
    WATER_SPLASH("splash", 5, ParticleProperty.DIRECTIONAL),
    WATER_WAKE("wake", 6, ParticleProperty.DIRECTIONAL),
    SUSPENDED("suspended", 7, ParticleProperty.REQUIRES_WATER),
    SUSPENDED_DEPTH("depthSuspend", 8, ParticleProperty.DIRECTIONAL),
    CRITS("crit", 9, ParticleProperty.DIRECTIONAL),
    MAGIC_CRITS("magicCrit", 10, ParticleProperty.DIRECTIONAL),
    NORMAL_SMOKE("smoke", 11, ParticleProperty.DIRECTIONAL),
    LARGE_SMOKE("largesmoke", 12, ParticleProperty.DIRECTIONAL),
    SPELL("spell", 13),
    SPELL_INSTANT("instantSpell", 14),
    MOB_SPELL("mobSpell", 15, ParticleProperty.COLORABLE),
    AMBIENT_MOB_SPELL("mobSpellAmbient", 16, ParticleProperty.COLORABLE),
    WITCH_SPELL("witchMagic", 17),
    WATER_DROPS("dripWater", 18),
    LAVA_DROPS("dripLava", 19),
    ANGRY_VILLAGER("angryVillager", 20),
    HAPPY_VILLAGER("happyVillager", 21, ParticleProperty.DIRECTIONAL),
    TOWN_AURA("townaura", 22, ParticleProperty.DIRECTIONAL),
    NOTE("note", 23, ParticleProperty.COLORABLE),
    PORTAL("portal", 24, ParticleProperty.DIRECTIONAL),
    ENCHANTMENT_TABLE("enchantmenttable", 25, ParticleProperty.DIRECTIONAL),
    FLAME("flame", 26, ParticleProperty.DIRECTIONAL),
    LAVA("lava", 27),
    FOOTSTEP("footstep", 28),
    CLOUD("cloud", 29, ParticleProperty.DIRECTIONAL),
    REDSTONE("reddust", 30, ParticleProperty.COLORABLE),
    SNOWBALL("snowballpoof", 31),
    SNOW_SHOVEL("snowshovel", 32, ParticleProperty.DIRECTIONAL),
    SLIME("slime", 33),
    HEARTS("heart", 34),
    BARRIER("barrier", 35),
    ITEM_CRACK("iconcrack", 36, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
    BLOCK_CRACK("blockcrack", 37, ParticleProperty.REQUIRES_DATA),
    BLOCK_DUST("blockdust", 38, ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA),
    WATER_DROP("droplet", 39),
    ITEM_TAKE("take", 40),
    MOB_APPEARANCE("mobappearance", 41);

    private static final Map<String, ParticleEffect> NAME_MAP = new HashMap<>();
    private static final Map<Integer, ParticleEffect> ID_MAP = new HashMap<>();
    private final String name;
    private final int id;
    private final List<ParticleEffect.ParticleProperty> properties;

    ParticleEffect(String name, int id, ParticleEffect.ParticleProperty... properties) {
        this.name = name;
        this.id = id;
        this.properties = Arrays.asList(properties);
    }

    public static ParticleEffect fromName(String name) {
        Iterator var1 = NAME_MAP.entrySet().iterator();

        Entry entry;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            entry = (Entry) var1.next();
        } while (!((String) entry.getKey()).equalsIgnoreCase(name));

        return (ParticleEffect) entry.getValue();
    }

    public static ParticleEffect fromId(int id) {
        Iterator var1 = ID_MAP.entrySet().iterator();

        Entry entry;
        do {
            if (!var1.hasNext()) {
                return null;
            }

            entry = (Entry) var1.next();
        } while ((Integer) entry.getKey() != id);

        return (ParticleEffect) entry.getValue();
    }

    private static boolean isWater(Location location) {
        Material material = location.getBlock().getType();
        return material == Material.WATER;
    }

    private static boolean isLongDistance(Location location, List<Player> players) {
        String world = location.getWorld().getName();
        Iterator var3 = players.iterator();

        Location playerLocation;
        do {
            if (!var3.hasNext()) {
                return false;
            }

            Player player = (Player) var3.next();
            playerLocation = player.getLocation();
        } while (!world.equals(playerLocation.getWorld().getName()) || playerLocation.distanceSquared(location) < 65536.0D);

        return true;
    }

    public static boolean isColorable(ParticleEffect effect) {
        return effect == NOTE || effect == MOB_SPELL || effect == REDSTONE;
    }

    public static boolean isSpammy(ParticleEffect effect) {
        return effect == SLIME || effect == HEARTS || effect == NOTE || effect == ANGRY_VILLAGER || effect == HAPPY_VILLAGER || effect == LAVA_DROPS || effect == WATER_DROPS;
    }

    private static boolean isDataCorrect(ParticleEffect effect, ParticleEffect.ParticleData data) {
        return (effect == BLOCK_CRACK || effect == BLOCK_DUST) && data instanceof ParticleEffect.BlockData || effect == ITEM_CRACK && data instanceof ParticleEffect.ItemData;
    }

    private static boolean isColorCorrect(ParticleEffect effect, ParticleEffect.ParticleColor color) {
        return (effect == MOB_SPELL || effect == AMBIENT_MOB_SPELL || effect == REDSTONE) && color instanceof ParticleEffect.OrdinaryColor || effect == NOTE && color instanceof ParticleEffect.NoteColor;
    }

    public static PermissionUtil getPermission(FancyEffect effect, ParticleEffect particle) {
        if (effect.getName().contains("Rotation")) {
            if (particle.equals(FLAME)) {
                return PermissionUtil.EFFECT_ROTATION_FLAME;
            }

            if (particle.equals(ANGRY_VILLAGER)) {
                return PermissionUtil.EFFECT_ROTATION_ANGRYVILLAGER;
            }

            if (particle.equals(CRITS)) {
                return PermissionUtil.EFFECT_ROTATION_CRIT;
            }

            if (particle.equals(HAPPY_VILLAGER)) {
                return PermissionUtil.EFFECT_ROTATION_HAPPYVILLAGER;
            }

            if (particle.equals(HEARTS)) {
                return PermissionUtil.EFFECT_ROTATION_HEART;
            }

            if (particle.equals(LAVA_DROPS)) {
                return PermissionUtil.EFFECT_ROTATION_LAVADROP;
            }

            if (particle.equals(MAGIC_CRITS)) {
                return PermissionUtil.EFFECT_ROTATION_MAGICCRIT;
            }

            if (particle.equals(REDSTONE)) {
                return PermissionUtil.EFFECT_ROTATION_REDSTONE;
            }

            if (particle.equals(NOTE)) {
                return PermissionUtil.EFFECT_ROTATION_NOTE;
            }

            if (particle.equals(SPARKS)) {
                return PermissionUtil.EFFECT_ROTATION_SPARKS;
            }

            if (particle.equals(WATER_DROPS)) {
                return PermissionUtil.EFFECT_ROTATION_WATERDROP;
            }

            if (particle.equals(SLIME)) {
                return PermissionUtil.EFFECT_ROTATION_SLIME;
            }

            if (particle.equals(NORMAL_SMOKE)) {
                return PermissionUtil.EFFECT_ROTATION_SMOKE;
            }
        } else if (effect.getName().contains("Spiral")) {
            if (particle.equals(FLAME)) {
                return PermissionUtil.EFFECT_SPIRAL_FLAME;
            }

            if (particle.equals(ANGRY_VILLAGER)) {
                return PermissionUtil.EFFECT_SPIRAL_ANGRYVILLAGER;
            }

            if (particle.equals(CRITS)) {
                return PermissionUtil.EFFECT_SPIRAL_CRIT;
            }

            if (particle.equals(HAPPY_VILLAGER)) {
                return PermissionUtil.EFFECT_SPIRAL_HAPPYVILLAGER;
            }

            if (particle.equals(HEARTS)) {
                return PermissionUtil.EFFECT_SPIRAL_HEART;
            }

            if (particle.equals(LAVA_DROPS)) {
                return PermissionUtil.EFFECT_SPIRAL_LAVADROP;
            }

            if (particle.equals(MAGIC_CRITS)) {
                return PermissionUtil.EFFECT_SPIRAL_MAGICCRIT;
            }

            if (particle.equals(REDSTONE)) {
                return PermissionUtil.EFFECT_SPIRAL_REDSTONE;
            }

            if (particle.equals(NOTE)) {
                return PermissionUtil.EFFECT_SPIRAL_NOTE;
            }

            if (particle.equals(SPARKS)) {
                return PermissionUtil.EFFECT_SPIRAL_SPARKS;
            }

            if (particle.equals(WATER_DROPS)) {
                return PermissionUtil.EFFECT_SPIRAL_WATERDROP;
            }

            if (particle.equals(SLIME)) {
                return PermissionUtil.EFFECT_SPIRAL_SLIME;
            }

            if (particle.equals(NORMAL_SMOKE)) {
                return PermissionUtil.EFFECT_SPIRAL_SMOKE;
            }
        } else if (effect.getName().contains("Trail")) {
            if (particle.equals(FLAME)) {
                return PermissionUtil.EFFECT_TRAIL_FLAME;
            }

            if (particle.equals(ANGRY_VILLAGER)) {
                return PermissionUtil.EFFECT_TRAIL_ANGRYVILLAGER;
            }

            if (particle.equals(CRITS)) {
                return PermissionUtil.EFFECT_TRAIL_CRIT;
            }

            if (particle.equals(HAPPY_VILLAGER)) {
                return PermissionUtil.EFFECT_TRAIL_HAPPYVILLAGER;
            }

            if (particle.equals(HEARTS)) {
                return PermissionUtil.EFFECT_TRAIL_HEART;
            }

            if (particle.equals(LAVA_DROPS)) {
                return PermissionUtil.EFFECT_TRAIL_LAVADROP;
            }

            if (particle.equals(MAGIC_CRITS)) {
                return PermissionUtil.EFFECT_TRAIL_MAGICCRIT;
            }

            if (particle.equals(REDSTONE)) {
                return PermissionUtil.EFFECT_TRAIL_REDSTONE;
            }

            if (particle.equals(NOTE)) {
                return PermissionUtil.EFFECT_TRAIL_NOTE;
            }

            if (particle.equals(SPARKS)) {
                return PermissionUtil.EFFECT_TRAIL_SPARKS;
            }

            if (particle.equals(WATER_DROPS)) {
                return PermissionUtil.EFFECT_TRAIL_WATERDROP;
            }

            if (particle.equals(SLIME)) {
                return PermissionUtil.EFFECT_TRAIL_SLIME;
            }

            if (particle.equals(NORMAL_SMOKE)) {
                return PermissionUtil.EFFECT_TRAIL_SMOKE;
            }
        } else if (effect.getName().contains("Crossing")) {
            if (particle.equals(FLAME)) {
                return PermissionUtil.EFFECT_CROSSING_FLAME;
            }

            if (particle.equals(ANGRY_VILLAGER)) {
                return PermissionUtil.EFFECT_CROSSING_ANGRYVILLAGER;
            }

            if (particle.equals(CRITS)) {
                return PermissionUtil.EFFECT_CROSSING_CRIT;
            }

            if (particle.equals(HAPPY_VILLAGER)) {
                return PermissionUtil.EFFECT_CROSSING_HAPPYVILLAGER;
            }

            if (particle.equals(HEARTS)) {
                return PermissionUtil.EFFECT_CROSSING_HEART;
            }

            if (particle.equals(LAVA_DROPS)) {
                return PermissionUtil.EFFECT_CROSSING_LAVADROP;
            }

            if (particle.equals(MAGIC_CRITS)) {
                return PermissionUtil.EFFECT_CROSSING_MAGICCRIT;
            }

            if (particle.equals(REDSTONE)) {
                return PermissionUtil.EFFECT_CROSSING_REDSTONE;
            }

            if (particle.equals(NOTE)) {
                return PermissionUtil.EFFECT_CROSSING_NOTE;
            }

            if (particle.equals(SPARKS)) {
                return PermissionUtil.EFFECT_CROSSING_SPARKS;
            }

            if (particle.equals(WATER_DROPS)) {
                return PermissionUtil.EFFECT_CROSSING_WATERDROP;
            }

            if (particle.equals(SLIME)) {
                return PermissionUtil.EFFECT_CROSSING_SLIME;
            }

            if (particle.equals(NORMAL_SMOKE)) {
                return PermissionUtil.EFFECT_CROSSING_SMOKE;
            }
        } else if (effect.getName().contains("Force")) {
            if (particle.equals(FLAME)) {
                return PermissionUtil.EFFECT_FORCEFIELD_FLAME;
            }

            if (particle.equals(ANGRY_VILLAGER)) {
                return PermissionUtil.EFFECT_FORCEFIELD_ANGRYVILLAGER;
            }

            if (particle.equals(CRITS)) {
                return PermissionUtil.EFFECT_FORCEFIELD_CRIT;
            }

            if (particle.equals(HAPPY_VILLAGER)) {
                return PermissionUtil.EFFECT_FORCEFIELD_HAPPYVILLAGER;
            }

            if (particle.equals(HEARTS)) {
                return PermissionUtil.EFFECT_FORCEFIELD_HEART;
            }

            if (particle.equals(LAVA_DROPS)) {
                return PermissionUtil.EFFECT_FORCEFIELD_LAVADROP;
            }

            if (particle.equals(MAGIC_CRITS)) {
                return PermissionUtil.EFFECT_FORCEFIELD_MAGICCRIT;
            }

            if (particle.equals(REDSTONE)) {
                return PermissionUtil.EFFECT_FORCEFIELD_REDSTONE;
            }

            if (particle.equals(NOTE)) {
                return PermissionUtil.EFFECT_FORCEFIELD_NOTE;
            }

            if (particle.equals(SPARKS)) {
                return PermissionUtil.EFFECT_FORCEFIELD_SPARKS;
            }

            if (particle.equals(WATER_DROPS)) {
                return PermissionUtil.EFFECT_FORCEFIELD_WATERDROP;
            }

            if (particle.equals(SLIME)) {
                return PermissionUtil.EFFECT_FORCEFIELD_SLIME;
            }

            if (particle.equals(NORMAL_SMOKE)) {
                return PermissionUtil.EFFECT_FORCEFIELD_SMOKE;
            }
        }

        return PermissionUtil.ALL;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public boolean hasProperty(ParticleEffect.ParticleProperty property) {
        return this.properties.contains(property);
    }

    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
        if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
        } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        } else {
            (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256.0D, null)).sendTo(center, range);
        }
    }

    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
        if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
        } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        } else {
            (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), null)).sendTo(center, players);
        }
    }

    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
        this.display(offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }

    public void display(Vector direction, float speed, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
        if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
        } else if (!this.hasProperty(ParticleEffect.ParticleProperty.DIRECTIONAL)) {
            throw new IllegalArgumentException("This particle effect is not directional");
        } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        } else {
            (new ParticleEffect.ParticlePacket(this, direction, speed, range > 256.0D, null)).sendTo(center, range);
        }
    }

    public void display(Vector direction, float speed, Location center, List<Player> players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
        if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleEffect.ParticleDataException("This particle effect requires additional data");
        } else if (!this.hasProperty(ParticleEffect.ParticleProperty.DIRECTIONAL)) {
            throw new IllegalArgumentException("This particle effect is not directional");
        } else if (this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        } else {
            (new ParticleEffect.ParticlePacket(this, direction, speed, isLongDistance(center, players), null)).sendTo(center, players);
        }
    }

    public void display(Vector direction, float speed, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException, IllegalArgumentException {
        this.display(direction, speed, center, Arrays.asList(players));
    }

    public void display(ParticleEffect.ParticleColor color, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleColorException {
        if (!this.hasProperty(ParticleEffect.ParticleProperty.COLORABLE)) {
            throw new ParticleEffect.ParticleColorException("This particle effect is not colorable");
        } else if (!isColorCorrect(this, color)) {
            throw new ParticleEffect.ParticleColorException("The particle color type is incorrect");
        } else {
            (new ParticleEffect.ParticlePacket(this, color, range > 256.0D)).sendTo(center, range);
        }
    }

    public void display(ParticleEffect.ParticleColor color, Location center, List<Player> players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleColorException {
        if (!this.hasProperty(ParticleEffect.ParticleProperty.COLORABLE)) {
            throw new ParticleEffect.ParticleColorException("This particle effect is not colorable");
        } else if (!isColorCorrect(this, color)) {
            throw new ParticleEffect.ParticleColorException("The particle color type is incorrect");
        } else {
            (new ParticleEffect.ParticlePacket(this, color, isLongDistance(center, players))).sendTo(center, players);
        }
    }

    public void display(ParticleEffect.ParticleColor color, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleColorException {
        this.display(color, center, Arrays.asList(players));
    }

    public void display(ParticleEffect.ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
        if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
        } else if (!isDataCorrect(this, data)) {
            throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
        } else {
            (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256.0D, data)).sendTo(center, range);
        }
    }

    public void display(ParticleEffect.ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, List<Player> players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
        if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
        } else if (!isDataCorrect(this, data)) {
            throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
        } else {
            (new ParticleEffect.ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), data)).sendTo(center, players);
        }
    }

    public void display(ParticleEffect.ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
        this.display(data, offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }

    public void display(ParticleEffect.ParticleData data, Vector direction, float speed, Location center, double range) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
        if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
        } else if (!isDataCorrect(this, data)) {
            throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
        } else {
            (new ParticleEffect.ParticlePacket(this, direction, speed, range > 256.0D, data)).sendTo(center, range);
        }
    }

    public void display(ParticleEffect.ParticleData data, Vector direction, float speed, Location center, List<Player> players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
        if (!this.hasProperty(ParticleEffect.ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleEffect.ParticleDataException("This particle effect does not require additional data");
        } else if (!isDataCorrect(this, data)) {
            throw new ParticleEffect.ParticleDataException("The particle data type is incorrect");
        } else {
            (new ParticleEffect.ParticlePacket(this, direction, speed, isLongDistance(center, players), data)).sendTo(center, players);
        }
    }

    public void display(ParticleEffect.ParticleData data, Vector direction, float speed, Location center, Player... players) throws ParticleEffect.ParticleVersionException, ParticleEffect.ParticleDataException {
        this.display(data, direction, speed, center, Arrays.asList(players));
    }

    static {
        ParticleEffect[] var0 = values();
        int var1 = var0.length;

        for (int var2 = 0; var2 < var1; ++var2) {
            ParticleEffect effect = var0[var2];
            NAME_MAP.put(effect.name, effect);
            ID_MAP.put(effect.id, effect);
        }

    }

    public abstract static class ParticleData {
        private final Material material;
        private final byte data;
        private final int[] packetData;

        public ParticleData(Material material, byte data) {
            this.material = material;
            this.data = data;
            this.packetData = new int[]{material.getId(), data};
        }

        public Material getMaterial() {
            return this.material;
        }

        public byte getData() {
            return this.data;
        }

        public int[] getPacketData() {
            return this.packetData;
        }

        public String getPacketDataString() {
            return "_" + this.packetData[0] + "_" + this.packetData[1];
        }
    }

    public static final class ItemData extends ParticleEffect.ParticleData {
        public ItemData(Material material, byte data) {
            super(material, data);
        }
    }

    public static final class BlockData extends ParticleEffect.ParticleData {
        public BlockData(Material material, byte data) throws IllegalArgumentException {
            super(material, data);
            if (!material.isBlock()) {
                throw new IllegalArgumentException("The material is not a block");
            }
        }
    }

    public abstract static class ParticleColor {
        public ParticleColor() {
        }

        public abstract float getValueX();

        public abstract float getValueY();

        public abstract float getValueZ();
    }

    public static final class OrdinaryColor extends ParticleEffect.ParticleColor {
        private final int red;
        private final int green;
        private final int blue;

        public OrdinaryColor(int red, int green, int blue) throws IllegalArgumentException {
            if (red < 0) {
                throw new IllegalArgumentException("The red value is lower than 0");
            } else if (red > 255) {
                throw new IllegalArgumentException("The red value is higher than 255");
            } else {
                this.red = red;
                if (green < 0) {
                    throw new IllegalArgumentException("The green value is lower than 0");
                } else if (green > 255) {
                    throw new IllegalArgumentException("The green value is higher than 255");
                } else {
                    this.green = green;
                    if (blue < 0) {
                        throw new IllegalArgumentException("The blue value is lower than 0");
                    } else if (blue > 255) {
                        throw new IllegalArgumentException("The blue value is higher than 255");
                    } else {
                        this.blue = blue;
                    }
                }
            }
        }

        public OrdinaryColor(Color color) {
            this(color.getRed(), color.getGreen(), color.getBlue());
        }

        public int getRed() {
            return this.red;
        }

        public int getGreen() {
            return this.green;
        }

        public int getBlue() {
            return this.blue;
        }

        public float getValueX() {
            return (float) this.red / 255.0F;
        }

        public float getValueY() {
            return (float) this.green / 255.0F;
        }

        public float getValueZ() {
            return (float) this.blue / 255.0F;
        }
    }

    public static final class NoteColor extends ParticleEffect.ParticleColor {
        private final int note;

        public NoteColor(int note) throws IllegalArgumentException {
            if (note < 0) {
                throw new IllegalArgumentException("The note value is lower than 0");
            } else if (note > 24) {
                throw new IllegalArgumentException("The note value is higher than 24");
            } else {
                this.note = note;
            }
        }

        public float getValueX() {
            return (float) this.note / 24.0F;
        }

        public float getValueY() {
            return 0.0F;
        }

        public float getValueZ() {
            return 0.0F;
        }
    }

    private static final class ParticleDataException extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleDataException(String message) {
            super(message);
        }
    }

    private static final class ParticleColorException extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleColorException(String message) {
            super(message);
        }
    }

    private static final class ParticleVersionException extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleVersionException(String message) {
            super(message);
        }
    }

    public static final class ParticlePacket {
        private static int version;
        private static Class<?> enumParticle;
        private static Constructor<?> packetConstructor;
        private static Method getHandle;
        private static Field playerConnection;
        private static Method sendPacket;
        private static boolean initialized;
        private final ParticleEffect effect;
        private final float offsetY;
        private final float offsetZ;
        private final float speed;
        private final int amount;
        private final boolean longDistance;
        private final ParticleEffect.ParticleData data;
        private float offsetX;
        private Object packet;

        public ParticlePacket(ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int amount, boolean longDistance, ParticleEffect.ParticleData data) throws IllegalArgumentException {
            initialize();
            if (speed < 0.0F) {
                throw new IllegalArgumentException("The speed is lower than 0");
            } else if (amount < 0) {
                throw new IllegalArgumentException("The amount is lower than 0");
            } else {
                this.effect = effect;
                this.offsetX = offsetX;
                this.offsetY = offsetY;
                this.offsetZ = offsetZ;
                this.speed = speed;
                this.amount = amount;
                this.longDistance = longDistance;
                this.data = data;
            }
        }

        public ParticlePacket(ParticleEffect effect, Vector direction, float speed, boolean longDistance, ParticleEffect.ParticleData data) throws IllegalArgumentException {
            this(effect, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(), speed, 0, longDistance, data);
        }

        public ParticlePacket(ParticleEffect effect, ParticleEffect.ParticleColor color, boolean longDistance) {
            this(effect, color.getValueX(), color.getValueY(), color.getValueZ(), 1.0F, 0, longDistance, null);
            if (effect == ParticleEffect.REDSTONE && color instanceof ParticleEffect.OrdinaryColor && ((ParticleEffect.OrdinaryColor) color).getRed() == 0) {
                this.offsetX = 1.17549435E-38F;
            }

        }

        public static void initialize() {
            if (!initialized) {
                try {
                    version = Integer.parseInt(Character.toString(PackageType.getServerVersion().charAt(3)));
                    enumParticle = PackageType.MINECRAFT_SERVER.getClass("EnumParticle");
                    Class packetClass = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutWorldParticles");
                    packetConstructor = ReflectionUtils.getConstructor(packetClass);
                    getHandle = ReflectionUtils.getMethod("CraftPlayer", PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
                    playerConnection = ReflectionUtils.getField("EntityPlayer", PackageType.MINECRAFT_SERVER, false, "playerConnection");
                    sendPacket = ReflectionUtils.getMethod(playerConnection.getType(), "sendPacket", PackageType.MINECRAFT_SERVER.getClass("Packet"));
                } catch (Exception var1) {
                    PartlyFancy.getInstance().getLogger().info("Particle Library is not compatible with your server!");
                }

                initialized = true;
            }
        }

        private void initializePacket(Location center) throws ParticleEffect.ParticlePacket.PacketInstantiationException {
            if (this.packet == null) {
                try {
                    this.packet = packetConstructor.newInstance();
                    ReflectionUtils.setValue(this.packet, true, "a", enumParticle.getEnumConstants()[this.effect.getId()]);
                    ReflectionUtils.setValue(this.packet, true, "j", this.longDistance);
                    if (this.data != null) {
                        int[] packetData = this.data.getPacketData();
                        int[] arrn;
                        if (this.effect == ParticleEffect.ITEM_CRACK) {
                            arrn = packetData;
                        } else {
                            int[] arrn2 = new int[1];
                            arrn = arrn2;
                            arrn2[0] = packetData[0] | packetData[1] << 12;
                        }

                        ReflectionUtils.setValue(this.packet, true, "k", arrn);
                    }

                    ReflectionUtils.setValue(this.packet, true, "b", (float) center.getX());
                    ReflectionUtils.setValue(this.packet, true, "c", (float) center.getY());
                    ReflectionUtils.setValue(this.packet, true, "d", (float) center.getZ());
                    ReflectionUtils.setValue(this.packet, true, "e", this.offsetX);
                    ReflectionUtils.setValue(this.packet, true, "f", this.offsetY);
                    ReflectionUtils.setValue(this.packet, true, "g", this.offsetZ);
                    ReflectionUtils.setValue(this.packet, true, "h", this.speed);
                    ReflectionUtils.setValue(this.packet, true, "i", this.amount);
                } catch (Exception var5) {
                    throw new ParticleEffect.ParticlePacket.PacketInstantiationException("Packet instantiation failed", var5);
                }
            }
        }

        public void sendTo(Location center, Player player) throws ParticleEffect.ParticlePacket.PacketInstantiationException, ParticleEffect.ParticlePacket.PacketSendingException {
            this.initializePacket(center);

            try {
                sendPacket.invoke(playerConnection.get(getHandle.invoke(player)), this.packet);
            } catch (Exception var4) {
                throw new ParticleEffect.ParticlePacket.PacketSendingException("Failed to send the packet to player '" + player.getName() + "'", var4);
            }
        }

        public void sendTo(Location center, List<Player> players) throws IllegalArgumentException {
            if (players.isEmpty()) {
                throw new IllegalArgumentException("The player list is empty");
            } else {
                Iterator var3 = players.iterator();

                while (var3.hasNext()) {
                    Player player = (Player) var3.next();
                    this.sendTo(center, player);
                }

            }
        }

        public void sendTo(Location center, double range) throws IllegalArgumentException {
            if (range < 1.0D) {
                throw new IllegalArgumentException("The range is lower than 1");
            } else {
                String worldName = center.getWorld().getName();
                double squared = range * range;
                Iterator var7 = Bukkit.getOnlinePlayers().iterator();

                while (var7.hasNext()) {
                    Player player = (Player) var7.next();
                    if (player.getWorld().getName().equals(worldName) && player.getLocation().distanceSquared(center) <= squared) {
                        this.sendTo(center, player);
                    }
                }

            }
        }

        private static final class PacketInstantiationException extends RuntimeException {
            private static final long serialVersionUID = 3203085387160737484L;

            public PacketInstantiationException(String message, Throwable cause) {
                super(message, cause);
            }
        }

        private static final class PacketSendingException extends RuntimeException {
            private static final long serialVersionUID = 3203085387160737484L;

            public PacketSendingException(String message, Throwable cause) {
                super(message, cause);
            }
        }
    }

    public enum ParticleProperty {
        REQUIRES_WATER,
        REQUIRES_DATA,
        DIRECTIONAL,
        COLORABLE;

        ParticleProperty() {
        }
    }
}
