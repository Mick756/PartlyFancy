package fancy.util;

import com.sun.istack.internal.NotNull;
import fancy.PartlyFancy;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NBTUtil {

    // idk if this works
    public static ItemStack addNBTStringTag(@NotNull ItemStack stack, @NotNull String tag) {
        try {
            Method nmsItem = ReflectionUtils.getMethod(Class.forName("CraftItemStack"), "asNMSCopy", ItemStack.class);
            Constructor stringTag = ReflectionUtils.getConstructor(Class.forName("NBTTagString"), String.class);
            Method setTag = ReflectionUtils.getMethod(Class.forName("net.minecraft.server." + PartlyFancy.bukkitVersion + ".ItemStack"), "setTag");
            setTag.invoke(stack, stringTag.newInstance(tag));
            return (ItemStack) ReflectionUtils.getMethod(Class.forName("net.minecraft.server." + PartlyFancy.bukkitVersion + ".ItemStack"), "asBukkitCopy").invoke(null, stack);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return stack;
        }
    }
}
