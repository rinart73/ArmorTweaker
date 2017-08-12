package com.rinart73.armortweaker.mods.vanilla.actions;

import com.rinart73.armortweaker.brackets.util.IItemArmor;
import minetweaker.IUndoableAction;
import net.minecraft.item.ItemArmor;

public class SetArmorMaxDamageAction implements IUndoableAction
{
    private final IItemArmor armor;
    private final int newValue;
    private final int oldValue;

    public SetArmorMaxDamageAction(IItemArmor armor, int newValue) {
        this.armor = armor;
        this.newValue = newValue;
        this.oldValue = armor.getMaxDamage();
    }

    private void set(ItemArmor armor, int newValue) {
        armor.setMaxDamage(newValue);
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
        return "Setting MaxDamage of " + armor.getName() + " to " + newValue;
    }

    @Override
    public String describeUndo() {
        return "Reverting MaxDamage of " + armor.getName() + " to " + oldValue;
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}