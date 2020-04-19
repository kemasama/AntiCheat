package kemq.cheat.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class Modifier {
	public static double getSpeed(Player pl) {
		double speed = 0.0D;

		speed += getValue(pl.getInventory().getItemInMainHand(), Attribute.GENERIC_MOVEMENT_SPEED, EquipmentSlot.HAND);
		speed += getValue(pl.getInventory().getItemInOffHand(), Attribute.GENERIC_MOVEMENT_SPEED, EquipmentSlot.OFF_HAND);
		speed += getValue(pl.getInventory().getHelmet(), Attribute.GENERIC_MOVEMENT_SPEED, EquipmentSlot.HEAD);
		speed += getValue(pl.getInventory().getChestplate(), Attribute.GENERIC_MOVEMENT_SPEED, EquipmentSlot.CHEST);
		speed += getValue(pl.getInventory().getLeggings(), Attribute.GENERIC_MOVEMENT_SPEED, EquipmentSlot.LEGS);
		speed += getValue(pl.getInventory().getBoots(), Attribute.GENERIC_MOVEMENT_SPEED, EquipmentSlot.FEET);

		return speed;
	}

	public static double getValue(ItemStack item, Attribute attr, EquipmentSlot slot) {

		try {
			if (item != null && item.hasItemMeta() && item.getItemMeta().hasAttributeModifiers() && item.getItemMeta().getAttributeModifiers(attr) != null) {
				double mx = 0.0D;
				for (AttributeModifier ats : item.getItemMeta().getAttributeModifiers(attr)) {
					if (ats != null && slot.equals(ats.getSlot())) {
						mx += ats.getAmount();
					}
				}

				return mx;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0.0D;
	}

	public static double getValue(ItemStack item, Attribute attr) {

		try {
			if (item != null && item.hasItemMeta() && item.getItemMeta().hasAttributeModifiers()) {
				double mx = 0.0D;
				for (AttributeModifier ats : item.getItemMeta().getAttributeModifiers(attr)) {
					if (ats != null) {
						mx += ats.getAmount();
					}
				}

				return mx;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0.0D;
	}
}
