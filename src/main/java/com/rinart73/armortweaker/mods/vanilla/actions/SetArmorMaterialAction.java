package com.rinart73.armortweaker.mods.vanilla.actions;

import com.rinart73.armortweaker.brackets.util.IArmorMaterial;
import com.rinart73.armortweaker.brackets.util.IItemArmor;
import com.rinart73.armortweaker.mods.jei.JeiHelper;
import com.rinart73.armortweaker.mods.vanilla.VanillaHelper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SetArmorMaterialAction implements IUndoableAction
{
    private final IItemArmor armor;
    private final IArmorMaterial newValue;
    private final IArmorMaterial oldValue;

    //List<AnvilRecipeWrapper>
    private final List<Object> newRecipes;
    //List<AnvilRecipeWrapper>
    private final List<Object> oldRecipes;

    public SetArmorMaterialAction(IItemArmor armor, IArmorMaterial newValue) {
        this.armor = armor;
        this.newValue = newValue;
        this.oldValue = armor.getMaterial();

        //List<AnvilRecipeWrapper>
        List<Object> newRecipes = new ArrayList<>();
        //List<AnvilRecipeWrapper>
        List<Object> oldRecipes = new ArrayList<>();

        if (VanillaHelper.isJei) {
            IItemStack oldRepairStack = oldValue.getRepairMaterial();
            IItemStack newRepairStack = newValue.getRepairMaterial();
            ItemStack oldStack = oldRepairStack != null ? (ItemStack) oldRepairStack.getInternal() : ItemStack.EMPTY;
            ItemStack newStack = newRepairStack != null ? (ItemStack) newRepairStack.getInternal() : ItemStack.EMPTY;
            /* Get all armor repair recipes for armor with old armor material
             * And construct new repair recipes with new repairMaterial from new armor material */
            JeiHelper.getArmorRepairRecipes(oldRecipes, newRecipes, oldValue, oldStack, newStack);
        }

        this.newRecipes = newRecipes;
        this.oldRecipes = oldRecipes;
    }

    private void set(ItemArmor armor, ItemArmor.ArmorMaterial newValue, List<Object> oldRecipes, List<Object> newRecipes) {
        try {
            VanillaHelper.armorMaterial.set(armor, newValue);
            //Updating armor stats according to the new material
            new SetArmorDamageReduceAmountAction(this.armor, newValue.getDamageReductionAmount(armor.getEquipmentSlot())).apply();
            new SetArmorMaxDamageAction(this.armor, newValue.getDurability(armor.getEquipmentSlot())).apply();
            new SetArmorToughnessAction(this.armor, newValue.getToughness()).apply();

            if (VanillaHelper.isJei) {
                oldRecipes.forEach(recipe -> {
                    MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe, VanillaRecipeCategoryUid.ANVIL);
                });
                newRecipes.forEach(recipe -> {
                    MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(recipe, VanillaRecipeCategoryUid.ANVIL);
                });
            }
        } catch (Exception e) {
            MineTweakerAPI.logError("SetArmorMaterialAction " + armor.getRegistryName() + " with value " + newValue.name() + ": " + e.toString());
        }
    }

    @Override
    public void apply() {
        set((ItemArmor) armor.getInternal(), (ItemArmor.ArmorMaterial) newValue.getInternal(), oldRecipes, newRecipes);
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public void undo() {
        set((ItemArmor) armor.getInternal(), (ItemArmor.ArmorMaterial) oldValue.getInternal(), newRecipes, oldRecipes);
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