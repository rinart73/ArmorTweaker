package com.rinart73.armortweaker.brackets.util;

import com.rinart73.armortweaker.mods.vanilla.VanillaHelper;
import com.rinart73.armortweaker.mods.vanilla.actions.*;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.mc1112.item.MCItemStack;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class MCArmorMaterial implements IArmorMaterial
{
    private final ItemArmor.ArmorMaterial material;

    public MCArmorMaterial(ItemArmor.ArmorMaterial material) {
        this.material = material;
    }


    @Override
    public Object getInternal() {
        return material;
    }

    @Override
    public String getName() {
        return material.name();
    }

    @Override
    public boolean matches(IArmorMaterial material) {
        return this.getName().equals(material.getName());
    }


    @Override
    public String getTextureName() {
        return material.getName();
    }

    @Override
    public void setTextureName(String textureName) {
        MineTweakerAPI.apply(new SetTextureNameAction(this, textureName));
    }


    @Override
    public int getMaxDamageFactor() {
        try {
            return VanillaHelper.materialMaxDamageFactor.getInt(material);
        } catch (Exception e) {
            MineTweakerAPI.logError("IArmorMaterial - getMaxDamageFactor for " + material.name() + ": " + e.toString());
        }
        return 0;
    }

    @Override
    public void setMaxDamageFactor(int maxDamageFactor) {
        MineTweakerAPI.apply(new SetMaxDamageFactorAction(this, maxDamageFactor));
    }


    @Override
    public int[] getDamageReductionAmountArray() {
        return new int[]{
                material.getDamageReductionAmount(EntityEquipmentSlot.FEET),
                material.getDamageReductionAmount(EntityEquipmentSlot.LEGS),
                material.getDamageReductionAmount(EntityEquipmentSlot.CHEST),
                material.getDamageReductionAmount(EntityEquipmentSlot.HEAD)
        };
    }

    @Override
    public void setDamageReductionAmountArray(int[] damageReductionAmountArray) {
        MineTweakerAPI.apply(new SetDamageReductionAmountArrayAction(this, damageReductionAmountArray));
    }


    @Override
    public int getEnchantability() {
        return material.getEnchantability();
    }

    @Override
    public void setEnchantability(int enchantability) {
        MineTweakerAPI.apply(new SetEnchantabilityAction(this, enchantability));
    }


    @Override
    public float getToughness() {
        return material.getToughness();
    }

    @Override
    public void setToughness(float toughness) {
        MineTweakerAPI.apply(new SetToughnessAction(this, toughness));
    }


    @Override
    public IItemStack getRepairMaterial() {
        ItemStack repairItem = material.getRepairItemStack();
        return !repairItem.isEmpty() ? new MCItemStack(repairItem) : null;
    }

    @Override
    public void setRepairMaterial(IItemStack repairMaterial) {
        MineTweakerAPI.apply(new SetRepairMaterialAction(this, repairMaterial));
    }
}