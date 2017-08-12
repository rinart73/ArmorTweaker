package com.rinart73.armortweaker.mods.vanilla.actions;

import com.rinart73.armortweaker.brackets.util.IArmorMaterial;
import com.rinart73.armortweaker.mods.vanilla.VanillaHelper;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.ItemArmor;

public class SetEnchantabilityAction implements IUndoableAction
{
    private final IArmorMaterial material;
    private final int newValue;
    private final int oldValue;

    public SetEnchantabilityAction(IArmorMaterial material, int newValue) {
        this.material = material;
        this.newValue = newValue;
        this.oldValue = material.getEnchantability();
    }

    private void set(ItemArmor.ArmorMaterial material, int newValue) {
        try {
            VanillaHelper.materialEnchantability.setInt(material, newValue);
        } catch (Exception e) {
            MineTweakerAPI.logError("SetEnchantabilityAction for " + material.name() + " with value " + newValue + ": " + e.toString());
        }
    }

    @Override
    public void apply() {
        set((ItemArmor.ArmorMaterial) material.getInternal(), newValue);
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public void undo() {
        set((ItemArmor.ArmorMaterial) material.getInternal(), oldValue);
    }

    @Override
    public String describe() {
        return "Setting Enchantability of " + material.getName() + " to " + newValue;
    }

    @Override
    public String describeUndo() {
        return "Reverting Enchantability of " + material.getName() + " to " + oldValue;
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}