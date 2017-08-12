package com.rinart73.armortweaker.mods.vanilla.actions;

import com.rinart73.armortweaker.brackets.util.IItemArmor;
import com.rinart73.armortweaker.mods.vanilla.VanillaHelper;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.ItemArmor;

public class SetArmorToughnessAction implements IUndoableAction
{
    private final IItemArmor armor;
    private final float newValue;
    private final float oldValue;

    public SetArmorToughnessAction(IItemArmor armor, float newValue) {
        this.armor = armor;
        this.newValue = newValue;
        this.oldValue = armor.getToughness();
    }

    private void set(ItemArmor armor, float newValue) {
        try {
            VanillaHelper.armorToughness.setFloat(armor, newValue);
        } catch (Exception e) {
            MineTweakerAPI.logError("SetArmorToughnessAction for " + armor.getRegistryName() + " with value " + newValue + ": " + e.toString());
        }
    }

    @Override
    public void apply() {
        set((ItemArmor) armor.getInternal(), newValue);
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public void undo() {
        set((ItemArmor) armor.getInternal(), oldValue);
    }

    @Override
    public String describe() {
        return "Setting Toughness of " + armor.getName() + " to " + newValue;
    }

    @Override
    public String describeUndo() {
        return "Reverting Toughness of " + armor.getName() + " to " + oldValue;
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}