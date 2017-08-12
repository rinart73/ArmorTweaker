package com.rinart73.armortweaker.mods.vanilla.handlers;

import com.rinart73.armortweaker.brackets.ArmorMaterialBracketHandler;
import com.rinart73.armortweaker.brackets.util.IArmorMaterial;
import com.rinart73.armortweaker.brackets.util.MCArmorMaterial;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import stanhebben.zenscript.annotations.*;

@ZenClass("mods.vanilla.ArmorMaterial")
public class ArmorMaterial
{
    @ZenMethod
    public static IArmorMaterial add(String enumName, String textureName, int durability, int[] reductionAmounts, int enchantability, float toughness) {
        ItemArmor.ArmorMaterial material = EnumHelper.addArmorMaterial(enumName, textureName, durability, reductionAmounts, enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, toughness);
        ArmorMaterialBracketHandler.rebuildRegistry();

        return new MCArmorMaterial(material);
    }
}