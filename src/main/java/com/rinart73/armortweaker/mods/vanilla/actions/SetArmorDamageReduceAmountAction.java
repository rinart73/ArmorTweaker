package com.rinart73.armortweaker.mods.vanilla.actions;

import com.rinart73.armortweaker.brackets.util.IItemArmor;
import com.rinart73.armortweaker.mods.vanilla.VanillaHelper;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.ItemArmor;

public class SetArmorDamageReduceAmountAction implements IUndoableAction
{
    private final IItemArmor armor;
    private final int newValue;
    private final int oldValue;

    public SetArmorDamageReduceAmountAction(IItemArmor armor, int newValue) {
        this.armor = armor;
        this.newValue = newValue;
        this.oldValue = armor.getDamageReduceAmount();
    }

    private void set(ItemArmor armor, int newValue) {
        try {
            VanillaHelper.armorDamageReduceAmount.setInt(armor, newValue);
        } catch (Exception e) {
            MineTweakerAPI.logError("SetArmorDamageReduceAmountAction for " + armor.getRegistryName() + " with value " + newValue + ": " + e.toString());
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
        return "Setting DamageReduceAmount of " + armor.getName() + " to " + newValue;
    }

    @Override
    public String describeUndo() {
        return "Reverting DamageReduceAmount of " + armor.getName() + " to " + oldValue;
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}