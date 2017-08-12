package com.rinart73.armortweaker.brackets.util;

import stanhebben.zenscript.annotations.*;

public interface IItemArmor
{
    Object getInternal();

    @ZenGetter("name")
    String getName();


    @ZenGetter("material")
    IArmorMaterial getMaterial();

    @ZenSetter("material")
    void setMaterial(IArmorMaterial material);


    @ZenGetter("damageReduceAmount")
    int getDamageReduceAmount();

    @ZenSetter("damageReduceAmount")
    void setDamageReduceAmount(int damageReduceAmount);


    @ZenGetter("maxDamage")
    int getMaxDamage();

    @ZenSetter("maxDamage")
    void setMaxDamage(int maxDamage);


    @ZenGetter("toughness")
    float getToughness();

    @ZenSetter("toughness")
    void setToughness(float toughness);
}