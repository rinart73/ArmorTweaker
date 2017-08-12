package com.rinart73.armortweaker.mods.vanilla.actions;

import com.rinart73.armortweaker.brackets.util.IArmorMaterial;
import com.rinart73.armortweaker.mods.jei.JeiHelper;
import com.rinart73.armortweaker.mods.vanilla.VanillaHelper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import java.util.*;

public class SetRepairMaterialAction implements IUndoableAction
{
    private final IArmorMaterial material;
    private final IItemStack newValue;
    private final IItemStack oldValue;
    //List<AnvilRecipeWrapper>
    private final List<Object> newRecipes;
    //List<AnvilRecipeWrapper>
    private final List<Object> oldRecipes;

    public SetRepairMaterialAction(IArmorMaterial material, IItemStack newValue) {
        this.material = material;
        this.newValue = newValue;
        this.oldValue = material.getRepairMaterial();

        //List<AnvilRecipeWrapper>
        List<Object> newRecipes = new ArrayList<>();
        //List<AnvilRecipeWrapper>
        List<Object> oldRecipes = new ArrayList<>();

        if (VanillaHelper.isJei) {
            /* Get all armor repair recipes for armor with this armor material
             * And construct new repair recipes with new repairMaterial */
            JeiHelper.getArmorRepairRecipes(oldRecipes, newRecipes, material,
                    (ItemStack) this.oldValue.getInternal(),
                    (ItemStack) this.newValue.getInternal());
        }

        this.newRecipes = newRecipes;
        this.oldRecipes = oldRecipes;
    }

    private void set(ItemArmor.ArmorMaterial material, ItemStack newValue, List<Object> oldRecipes, List<Object> newRecipes) {
        material.repairMaterial = newValue;

        if (VanillaHelper.isJei) {
            oldRecipes.forEach(recipe -> {
                MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe, VanillaRecipeCategoryUid.ANVIL);
            });
            newRecipes.forEach(recipe -> {
                MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(recipe, VanillaRecipeCategoryUid.ANVIL);
            });
        }
    }

    @Override
    public void apply() {
        ItemStack valueStack = newValue == null ? null : (ItemStack) newValue.getInternal();
        set((ItemArmor.ArmorMaterial) material.getInternal(), valueStack, oldRecipes, newRecipes);
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public void undo() {
        ItemStack valueStack = oldValue == null ? null : (ItemStack) oldValue.getInternal();
        set((ItemArmor.ArmorMaterial) material.getInternal(), valueStack, newRecipes, oldRecipes);
    }

    @Override
    public String describe() {
        return "Setting RepairMaterial of " + material.getName() + " to " + newValue;
    }

    @Override
    public String describeUndo() {
        return "Reverting RepairMaterial of " + material.getName() + " to " + oldValue;
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}