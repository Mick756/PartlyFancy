package fancy.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemStackBuilder {
	
	private ItemStack stack;
	private ItemMeta meta;
	public ItemStackBuilder(Material material, int amount) {
		this.stack = new ItemStack(material);
		this.stack.setAmount(amount);
		this.meta = this.stack.getItemMeta();
	}
	
	public ItemStackBuilder(Material material) {
		new ItemStackBuilder(material, 1);
	}
	
	public ItemStackBuilder(ItemStack stack) {
		this.stack = stack;
		this.meta = this.stack.getItemMeta();
	}
	
	private ItemMeta getMeta() {
		if (this.meta == null) {
			this.meta = this.stack.getItemMeta();
		}
		return this.meta;
	}
	
	public ItemStackBuilder setDisplayName(String name) {
		this.getMeta().setDisplayName(CosmeticUtil.color(name));
		return this;
	}
	
	public ItemStackBuilder addEnchantment(Enchantment enchantment, int level) {
		this.stack.addEnchantment(enchantment, level);
		return this;
	}
	
	public ItemStackBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
		this.stack.addUnsafeEnchantment(enchantment, level);
		return this;
	}

	public ItemStackBuilder removeEnchantment(Enchantment enchantment) {
		this.meta.removeEnchant(enchantment);
		return this;
	}
	
	public ItemStackBuilder addFlags(ItemFlag... flags) {
		this.stack.addItemFlags(flags);
		return this;
	}
	
	public ItemStackBuilder reset() {
		Material material = this.stack.getType();
		this.stack = new ItemStack(material, 1);
		this.meta = this.stack.getItemMeta();
		return this;
	}
	
	public String getDisplayName() {
		return this.getMeta().getDisplayName();
	}
	
	public ItemStackBuilder setAmount(int amount) {
		this.stack.setAmount(amount);
		return this;
	}
	
	private List<String> lore() {
		List<String> lore = this.getMeta().getLore();
		return (lore == null) ? new ArrayList<>() : lore;
	}
	
	public ItemStackBuilder setLore(String... lore) {
		List<String> toAdd = Arrays.asList(lore);
		List<String> newLore = new ArrayList<>();
		
		toAdd.forEach(line -> newLore.add(CosmeticUtil.color(line)));
		
		this.getMeta().setLore(newLore);
		return this;
	}
	
	public ItemStackBuilder setLore(List<String> lore) {
		List<String> newLore = new ArrayList<>();
		lore.forEach(line -> newLore.add(CosmeticUtil.color(line)));
		this.getMeta().setLore(newLore);
		return this;
	}
	
	public ItemStackBuilder addLore(String line) {
		List<String> lore = this.lore();
		lore.add(CosmeticUtil.color(line));
		this.getMeta().setLore(lore);
		return this;
	}
	
	public ItemStackBuilder addLore(String... lines) {
		List<String> lore = this.lore();
		List<String> toAdd = Arrays.asList(lines);
		
		toAdd.stream().forEach(line -> lore.add(CosmeticUtil.color(line)));
		
		lore.addAll(toAdd);
		this.getMeta().setLore(lore);
		return this;
	}
	
	public ItemStackBuilder setLoreLine(int line, String text) {
		List<String> lore = this.lore();
		lore.set(line, text);
		this.getMeta().setLore(lore);
		return this;
	}
	
	public ItemStackBuilder removeLoreLine(int line) {
		List<String> lore = this.lore();
		if (lore.size() > line) {
			lore.remove(line);
		}
		this.meta.setLore(lore);
		return this;
	}
	
	public ItemStackBuilder clearLore() {
		this.meta.setLore(null);
		return this;
	}
	
	public ItemStackBuilder setMeta() {
		this.stack.setItemMeta(this.meta);
		return this;
	}
	
	public ItemStack build() {
		this.setMeta();
		return this.stack;
	}

}
