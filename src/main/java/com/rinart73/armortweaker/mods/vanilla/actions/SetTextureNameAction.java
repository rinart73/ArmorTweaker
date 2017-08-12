package com.rinart73.armortweaker.mods.vanilla.actions;

import com.rinart73.armortweaker.brackets.util.IArmorMaterial;
import com.rinart73.armortweaker.mods.vanilla.VanillaHelper;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.ItemArmor;

public class SetTextureNameAction implements IUndoableAction
{
    private final IArmorMaterial material;
    private final String newValue;
    private final String oldValue;

    public SetTextureNameAction(IArmorMaterial material, String newValue) {
        this.material = material;
        this.newValue = newValue;
        this.oldValue = material.getName();
    }

    private void set(ItemArmor.ArmorMaterial material, String newValue) {
        try {
            VanillaHelper.materialName.set(material, newValue);
        } catch (Exception e) {
            MineTweakerAPI.logError("SetTextureNameAction for " + material.name() + " with value " + newValue + ": " + e.toString());
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
        return "Setting TextureName of " + material.getName() + " to " + newValue;
    }

    @Override
    public String describeUndo() {
        return "Reverting TextureName of " + material.getName() + " to " + oldValue;
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}