package com.rinart73.armortweaker.mods.vanilla.actions;

import com.rinart73.armortweaker.brackets.util.IArmorMaterial;
import com.rinart73.armortweaker.brackets.util.IItemArmor;
import com.rinart73.armortweaker.mods.vanilla.VanillaHelper;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.item.ItemArmor;

public class SetArmorMaterialAction implements IUndoableAction
{
    private final IItemArmor armor;
    private final IArmorMaterial newValue;
    private final IArmorMaterial oldValue;

    public SetArmorMaterialAction(IItemArmor armor, IArmorMaterial newValue) {
        this.armor = armor;
        this.newValue = newValue;
        this.oldValue = armor.getMaterial();
    }

    private void set(ItemArmor armor, ItemArmor.ArmorMaterial newValue) {
        try {
            VanillaHelper.armorMaterial.set(armor, newValue);
            //Updating armor stats according to the new material
            new SetArmorDamageReduceAmountAction(this.armor, newValue.getDamageReductionAmount(armor.getEquipmentSlot())).apply();
            new SetArmorMaxDamageAction(this.armor, newValue.getDurability(armor.getEquipmentSlot())).apply();
            new SetArmorToughnessAction(this.armor, newValue.getToughness()).apply();
        } catch (Exception e) {
            MineTweakerAPI.logError("SetArmorMaterialAction " + armor.getRegistryName() + " with value " + newValue.name() + ": " + e.toString());
        }
    }

    @Override
    public void apply() {
        set((ItemArmor) armor.getInternal(), (ItemArmor.ArmorMaterial) newValue.getInternal());
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public void undo() {
        set((ItemArmor) armor.getInternal(), (ItemArmor.ArmorMaterial) oldValue.getInternal());
    }

    @Override
    public String describe() {
        return "Setting Material of " + armor.getName() + " to " + newValue.getName();
    }

    @Override
    public String describeUndo() {
        return "Reverting Material of " + armor.getName() + " to " + oldValue.getName();
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}