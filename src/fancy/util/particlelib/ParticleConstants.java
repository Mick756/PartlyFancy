package fancy.util.particlelib;

import fancy.PartlyFancy;
import fancy.util.NBTUtil;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ParticleConstants {

    public static int version;

    // Classes
    public static final Class ITEM_STACK_CLASS;
    public static final Class PACKET_CLASS;
    public static final Class PACKET_PLAY_OUT_WORLD_PARTICLES_CLASS;
    public static final Class PARTICLE_ENUM;
    public static final Class PARTICLE_CLASS;
    public static final Class MINECRAFT_KEY_CLASS;
    public static final Class REGISTRY_CLASS;
    public static final Class BLOCK_CLASS;
    public static final Class BLOCK_DATA_INTERFACE;
    public static final Class BLOCKS_CLASS;
    public static final Class ENTITY_PLAYER_CLASS;
    public static final Class PLAYER_CONNECTION_CLASS;
    public static final Class CRAFT_PLAYER_CLASS;
    public static final Class CRAFT_ITEM_STACK_CLASS;
    public static final Class PARTICLE_PARAM_CLASS;
    public static final Class PARTICLE_PARAM_REDSTONE_CLASS;
    public static final Class PARTICLE_PARAM_BLOCK_CLASS;
    public static final Class PARTICLE_PARAM_ITEM_CLASS;

    // Methods
    public static final Method REGISTRY_GET_METHOD;
    public static final Method PLAYER_CONNECTION_SEND_PACKET_METHOD;
    public static final Method CRAFT_PLAYER_GET_HANDLE_METHOD;
    public static final Method BLOCK_GET_BLOCK_DATA_METHOD;
    public static final Method CRAFT_ITEM_STACK_AS_NMS_COPY_METHOD;

    // Fields
    public static final Field ENTITY_PLAYER_PLAYER_CONNECTION_FIELD;

    // Constructors
    public static final Constructor PACKET_PLAY_OUT_WORLD_PARTICLES_CONSTRUCTOR;
    public static final Constructor MINECRAFT_KEY_CONSTRUCTOR;
    public static final Constructor PARTICLE_PARAM_REDSTONE_CONSTRUCTOR;
    public static final Constructor PARTICLE_PARAM_BLOCK_CONSTRUCTOR;
    public static final Constructor PARTICLE_PARAM_ITEM_CONSTRUCTOR;

    // Constants
    public static final Object PARTICLE_TYPE_REGISTRY;

    // Initialization
    static {
        version = Integer.valueOf(PartlyFancy.bukkitVersion.substring(0, PartlyFancy.bukkitVersion.lastIndexOf("_")).replace("_", ".").substring(2));

        // Classes
        ITEM_STACK_CLASS = NBTUtil.getNMSClass("ItemStack");
        PACKET_CLASS = NBTUtil.getNMSClass("Packet");
        PACKET_PLAY_OUT_WORLD_PARTICLES_CLASS = NBTUtil.getNMSClass("PacketPlayOutWorldParticles");
        PARTICLE_ENUM = version < 13 ? NBTUtil.getNMSClass("EnumParticle") : null;
        PARTICLE_CLASS = version < 13 ? null : NBTUtil.getNMSClass("Particle");
        MINECRAFT_KEY_CLASS = NBTUtil.getNMSClass("MinecraftKey");
        REGISTRY_CLASS = version < 13 ? null : NBTUtil.getNMSClass("IRegistry");
        BLOCK_CLASS = NBTUtil.getNMSClass("Block");
        BLOCK_DATA_INTERFACE = NBTUtil.getNMSClass("IBlockData");
        BLOCKS_CLASS = version < 13 ? null : NBTUtil.getNMSClass("Blocks");
        ENTITY_PLAYER_CLASS = NBTUtil.getNMSClass("EntityPlayer");
        PLAYER_CONNECTION_CLASS = NBTUtil.getNMSClass("PlayerConnection");
        CRAFT_PLAYER_CLASS = NBTUtil.getCraftBukkitClass("entity.CraftPlayer");
        CRAFT_ITEM_STACK_CLASS = NBTUtil.getCraftBukkitClass("inventory.CraftItemStack");
        PARTICLE_PARAM_CLASS = version < 13 ? null : NBTUtil.getNMSClass("ParticleParam");
        PARTICLE_PARAM_REDSTONE_CLASS = version < 13 ? null : NBTUtil.getNMSClass("ParticleParamRedstone");
        PARTICLE_PARAM_BLOCK_CLASS = version < 13 ? null : NBTUtil.getNMSClass("ParticleParamBlock");
        PARTICLE_PARAM_ITEM_CLASS = version < 13 ? null : NBTUtil.getNMSClass("ParticleParamItem");

        // Methods
        REGISTRY_GET_METHOD = version < 13 ? null : NBTUtil.getMethod(REGISTRY_CLASS, "get", MINECRAFT_KEY_CLASS);
        PLAYER_CONNECTION_SEND_PACKET_METHOD = NBTUtil.getMethod(PLAYER_CONNECTION_CLASS, "sendPacket", PACKET_CLASS);
        CRAFT_PLAYER_GET_HANDLE_METHOD = NBTUtil.getMethod(CRAFT_PLAYER_CLASS, "getHandle");
        BLOCK_GET_BLOCK_DATA_METHOD = NBTUtil.getMethod(BLOCK_CLASS, "getBlockData");
        CRAFT_ITEM_STACK_AS_NMS_COPY_METHOD = NBTUtil.getMethod(CRAFT_ITEM_STACK_CLASS, "asNMSCopy", ItemStack.class);

        //Fields
        ENTITY_PLAYER_PLAYER_CONNECTION_FIELD = NBTUtil.getField(ENTITY_PLAYER_CLASS, "playerConnection", false);

        //Constructors
        PACKET_PLAY_OUT_WORLD_PARTICLES_CONSTRUCTOR = version < 13 ? NBTUtil.getConstructor(PACKET_PLAY_OUT_WORLD_PARTICLES_CLASS, PARTICLE_ENUM, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class) : NBTUtil.getConstructor(PACKET_PLAY_OUT_WORLD_PARTICLES_CLASS, PARTICLE_PARAM_CLASS, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class);
        MINECRAFT_KEY_CONSTRUCTOR = NBTUtil.getConstructor(MINECRAFT_KEY_CLASS, String.class);
        PARTICLE_PARAM_REDSTONE_CONSTRUCTOR = version < 13 ? null : NBTUtil.getConstructor(PARTICLE_PARAM_REDSTONE_CLASS, float.class, float.class, float.class, float.class);
        PARTICLE_PARAM_BLOCK_CONSTRUCTOR = version < 13 ? null : NBTUtil.getConstructor(PARTICLE_PARAM_BLOCK_CLASS, PARTICLE_CLASS, BLOCK_DATA_INTERFACE);
        PARTICLE_PARAM_ITEM_CONSTRUCTOR = version < 13 ? null : NBTUtil.getConstructor(PARTICLE_PARAM_ITEM_CLASS, PARTICLE_CLASS, ITEM_STACK_CLASS);

        // Constants
        PARTICLE_TYPE_REGISTRY = version < 13 ? null : NBTUtil.readField(NBTUtil.getField(REGISTRY_CLASS, "PARTICLE_TYPE", false), null);
    }

}
