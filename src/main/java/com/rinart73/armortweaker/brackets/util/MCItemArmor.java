package com.rinart73.armortweaker.brackets.util;

import com.rinart73.armortweaker.mods.vanilla.actions.*;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.ItemArmor;

public class MCItemArmor implements IItemArmor
{
    private final ItemArmor armor;

    public MCItemArmor(ItemArmor armor) {
        this.armor = armor;
    }


    @Override
    public Object getInternal() {
        return armor;
    }

    @Override
    public String getName() {
        return armor.getRegistryName().toString();
    }


    @Override
    public IArmorMaterial getMaterial() {
        return new MCArmorMaterial(armor.getArmorMaterial());
    }

    @Override
    public void setMaterial(IArmorMaterial material) {
        MineTweakerAPI.apply(new SetArmorMaterialAction(this, material));
    }


    @Override
    public int getDamageReduceAmount() {
        return armor.damageReduceAmount;
    }

    @Override
    public void setDamageReduceAmount(int damageReduceAmount) {
        MineTweakerAPI.apply(new SetArmorDamageReduceAmountAction(this, damageReduceAmount));
    }


    @Override
    public int getMaxDamage() {
        return armor.getMaxDamage();
    }

    @Override
    public void setMaxDamage(int maxDamage) {
        MineTweakerAPI.apply(new SetArmorMaxDamageAction(this, maxDamage));
    }


    @Override
    public float getToughness() {
        return armor.toughness;
    }

    @Override
    public void setToughness(float toughness) {
        MineTweakerAPI.apply(new SetArmorToughnessAction(this, toughness));
    }
}