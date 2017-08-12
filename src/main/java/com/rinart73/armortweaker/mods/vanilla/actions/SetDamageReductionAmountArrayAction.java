package com.rinart73.armortweaker.mods.vanilla.actions;

import com.rinart73.armortweaker.brackets.util.IArmorMaterial;
import com.rinart73.armortweaker.mods.vanilla.VanillaHelper;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.ItemArmor;

import java.util.Arrays;

public class SetDamageReductionAmountArrayAction implements IUndoableAction
{
    private final IArmorMaterial material;
    private final int[] newValue;
    private final int[] oldValue;

    public SetDamageReductionAmountArrayAction(IArmorMaterial material, int[] newValue) {
        this.material = material;
        this.newValue = newValue;
        this.oldValue = material.getDamageReductionAmountArray();
    }

    private void set(ItemArmor.ArmorMaterial material, int[] newValue) {
        try {
            VanillaHelper.materialDamageReductionAmountArray.set(material, newValue);
            VanillaHelper.getArmorForMaterial(this.material).forEach(armor -> {
                new SetArmorDamageReduceAmountAction(armor, material.getDamageReductionAmount(((ItemArmor) armor.getInternal()).getEquipmentSlot())).apply();
            });
        } catch (Exception e) {
            MineTweakerAPI.logError("SetDamageReductionAmountArrayAction for " + material.name() + " with value " + Arrays.toString(newValue) + ": " + e.toString());
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
        return "Setting DamageReductionAmountArray of " + material.getName() + " to " + Arrays.toString(newValue);
    }

    @Override
    public String describeUndo() {
        return "Reverting DamageReductionAmountArray of " + material.getName() + " to " + Arrays.toString(oldValue);
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}