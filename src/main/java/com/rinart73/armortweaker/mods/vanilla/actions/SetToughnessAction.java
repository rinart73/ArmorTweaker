package com.rinart73.armortweaker.mods.vanilla.actions;

import com.rinart73.armortweaker.brackets.util.IArmorMaterial;
import com.rinart73.armortweaker.mods.vanilla.VanillaHelper;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.ItemArmor;

public class SetToughnessAction implements IUndoableAction
{
    private final IArmorMaterial material;
    private final float newValue;
    private final float oldValue;

    public SetToughnessAction(IArmorMaterial material, float newValue) {
        this.material = material;
        this.newValue = newValue;
        this.oldValue = material.getToughness();
    }

    private void set(ItemArmor.ArmorMaterial material, float newValue) {
        try {
            VanillaHelper.materialToughness.setFloat(material, newValue);
            VanillaHelper.getArmorForMaterial(this.material).forEach(armor -> {
                new SetArmorToughnessAction(armor, material.getToughness()).apply();
            });
        } catch (Exception e) {
            MineTweakerAPI.logError("SetToughnessAction for " + material.name() + " with value " + newValue + ": " + e.toString());
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
        return "Setting Toughness of " + material.getName() + " to " + newValue;
    }

    @Override
    public String describeUndo() {
        return "Reverting Toughness of " + material.getName() + " to " + oldValue;
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}