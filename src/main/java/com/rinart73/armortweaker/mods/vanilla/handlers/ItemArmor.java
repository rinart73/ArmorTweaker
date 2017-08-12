package com.rinart73.armortweaker.mods.vanilla.handlers;

import com.rinart73.armortweaker.brackets.util.IItemArmor;
import com.rinart73.armortweaker.brackets.util.MCItemArmor;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.*;

@ZenClass("mods.vanilla.ItemArmor")
public class ItemArmor
{
    @ZenMethod
    public static IItemArmor get(IItemStack input) {
        ItemStack stack = (ItemStack) input.getInternal();
        if (stack == null)
            return null;
        Item item = stack.getItem();
        return item instanceof net.minecraft.item.ItemArmor ? new MCItemArmor((net.minecraft.item.ItemArmor) item) : null;
    }
}