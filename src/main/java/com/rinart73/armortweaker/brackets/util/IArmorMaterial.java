package com.rinart73.armortweaker.brackets.util;

import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

public interface IArmorMaterial
{
    Object getInternal();

    @ZenGetter("name")
    String getName();

    @ZenMethod
    boolean matches(IArmorMaterial material);


    @ZenGetter("textureName")
    String getTextureName();

    @ZenSetter("textureName")
    void setTextureName(String textureName);


    @ZenGetter("maxDamageFactor")
    int getMaxDamageFactor();

    @ZenSetter("maxDamageFactor")
    void setMaxDamageFactor(int maxDamageFactor);


    @ZenGetter("damageReductionAmountArray")
    int[] getDamageReductionAmountArray();

    @ZenSetter("damageReductionAmountArray")
    void setDamageReductionAmountArray(int[] damageReductionAmountArray);


    @ZenGetter("enchantability")
    int getEnchantability();

    @ZenSetter("enchantability")
    void setEnchantability(int enchantability);


    @ZenGetter("toughness")
    float getToughness();

    @ZenSetter("toughness")
    void setToughness(float toughness);


    @ZenGetter("repairMaterial")
    IItemStack getRepairMaterial();

    @ZenSetter("repairMaterial")
    void setRepairMaterial(IItemStack repairMaterial);
}