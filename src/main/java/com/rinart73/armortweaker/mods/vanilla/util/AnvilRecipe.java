package com.rinart73.armortweaker.mods.vanilla.util;

import net.minecraft.item.ItemStack;

import java.util.List;

public class AnvilRecipe
{
    public final ItemStack leftInput;
    public final List<ItemStack> rightInputs;
    public final List<ItemStack> outputs;

    public AnvilRecipe(ItemStack leftInput, List<ItemStack> rightInputs, List<ItemStack> outputs) {
        this.leftInput = leftInput;
        this.rightInputs = rightInputs;
        this.outputs = outputs;
    }
}