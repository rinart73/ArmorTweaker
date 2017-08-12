package com.rinart73.armortweaker.mods.vanilla.commands;

import com.blamejared.mtlib.commands.CommandLoggerMulti;
import com.blamejared.mtlib.helpers.LogHelper;
import com.rinart73.armortweaker.brackets.ArmorMaterialBracketHandler;
import com.rinart73.armortweaker.brackets.util.IArmorMaterial;
import com.rinart73.armortweaker.brackets.util.MCArmorMaterial;
import com.rinart73.armortweaker.mods.vanilla.VanillaHelper;
import minetweaker.MineTweakerAPI;
import minetweaker.api.server.ICommandFunction;
import net.minecraft.item.ItemArmor;

import java.util.*;

public class ArmorLogger extends CommandLoggerMulti
{
    @Override
    public Map<String, ICommandFunction> getLists() {
        Map<String, ICommandFunction> logs = new HashMap<>();

        logs.put("materials", (strings, player) -> {
            for (ItemArmor.ArmorMaterial materialIn : ArmorMaterialBracketHandler.getMaterialNames().values()) {
                IArmorMaterial material = new MCArmorMaterial(materialIn);
                String name = material.getName();

                MineTweakerAPI.logCommand(String.format("<armorMaterial:%s>.textureName = \"%s\";",
                        name,
                        material.getTextureName()
                ));
                MineTweakerAPI.logCommand(String.format("<armorMaterial:%s>.maxDamageFactor = %d;",
                        name,
                        material.getMaxDamageFactor()
                ));
                MineTweakerAPI.logCommand(String.format("<armorMaterial:%s>.damageReductionAmountArray = %s;",
                        name,
                        Arrays.toString(material.getDamageReductionAmountArray())
                ));
                MineTweakerAPI.logCommand(String.format("<armorMaterial:%s>.enchantability = %d;",
                        name,
                        material.getEnchantability()
                ));
                MineTweakerAPI.logCommand(String.format("<armorMaterial:%s>.toughness = %f;",
                        name,
                        material.getToughness()
                ));
                MineTweakerAPI.logCommand(String.format("<armorMaterial:%s>.repairMaterial = %s;",
                        name,
                        material.getRepairMaterial() == null ? null : LogHelper.getStackDescription(material.getRepairMaterial())
                ));
            }
        });

        logs.put("items", (strings, player) -> {
            VanillaHelper.armorList.forEach(armor -> {
                String name = armor.getName();

                MineTweakerAPI.logCommand(String.format("<%s>.material = %s;",
                        name,
                        armor.getMaterial().getName()
                ));
                MineTweakerAPI.logCommand(String.format("<%s>.damageReduceAmount = %d;",
                        name,
                        armor.getDamageReduceAmount()
                ));
                MineTweakerAPI.logCommand(String.format("<%s>.maxDamage = %d;",
                        name,
                        armor.getMaxDamage()
                ));
                MineTweakerAPI.logCommand(String.format("<%s>.toughness = %f;",
                        name,
                        armor.getToughness()
                ));
            });
        });

        return logs;
    }
}