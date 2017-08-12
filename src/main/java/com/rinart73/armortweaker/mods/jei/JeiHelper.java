package com.rinart73.armortweaker.mods.jei;

import com.google.common.collect.Lists;
import com.rinart73.armortweaker.brackets.util.IArmorMaterial;
import com.rinart73.armortweaker.mods.vanilla.VanillaHelper;
import com.rinart73.armortweaker.mods.vanilla.util.AnvilRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.ingredients.Ingredients;
import mezz.jei.plugins.vanilla.anvil.AnvilRecipeWrapper;
import minetweaker.mods.jei.JEIAddonPlugin;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//Will be loaded only if JEI is loaded
public class JeiHelper
{
    //Find all JEI anvil recipes
    public static void rebuildAnvilRecipes() {
        VanillaHelper.anvilRecipeWrappers.clear();
        List<IRecipeCategory> categories = JEIAddonPlugin.recipeRegistry.getRecipeCategories(
                Lists.newArrayList(VanillaRecipeCategoryUid.ANVIL));
        if (categories.size() == 0)
            return;

        List<AnvilRecipeWrapper> wrappers = JEIAddonPlugin.recipeRegistry.getRecipeWrappers(categories.get(0));
        for (AnvilRecipeWrapper wrapper : wrappers) {
            IIngredients ingredients = new Ingredients();
            wrapper.getIngredients(ingredients);

            List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
            if (inputs.size() != 2)
                continue;
            List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);
            if (outputs.size() != 1)
                continue;

            VanillaHelper.anvilRecipeWrappers.put(new AnvilRecipe(inputs.get(0).get(0), inputs.get(1), outputs.get(0)), wrapper);
        }
    }

    /* Gets all armor repair recipes for armor which have certain armor material and repair material
     * Also constructs new armor repair recipes for new repair material
     */
    public static void getArmorRepairRecipes(List<Object> oldRecipes, List<Object> newRecipes, IArmorMaterial material, ItemStack oldStack, ItemStack newStack) {
        List<ItemStack> armorStackList = new ArrayList<>();
        VanillaHelper.getArmorForMaterial(material).forEach(armor -> {
            armorStackList.add(new ItemStack((ItemArmor) armor.getInternal()));
        });
        int armorListSize = armorStackList.size();

        List<ItemStack> newRightInputs = Collections.singletonList(newStack);

        for (Map.Entry<AnvilRecipe, Object> entry : VanillaHelper.anvilRecipeWrappers.entrySet()) {
            AnvilRecipe recipe = entry.getKey();

            for (int i = 0; i < armorListSize; i++) {
                if (!recipe.leftInput.isItemEqualIgnoreDurability(armorStackList.get(i)))
                    continue;

                boolean found = false;
                /* Checking every ItemStack if it matches repairMaterial:
                 * <minecraft:leather:*> and <minecraft:leather> will match
                 * Reverse order will match too */
                for (ItemStack right : recipe.rightInputs) {
                    if (right.isEmpty() && !oldStack.isEmpty() || !right.isEmpty() && oldStack.isEmpty())
                        continue;

                    if (right.getItem() == oldStack.getItem()) {
                        if (right.getItemDamage() == OreDictionary.WILDCARD_VALUE
                                || oldStack.getItemDamage() == OreDictionary.WILDCARD_VALUE
                                || right.getItemDamage() == oldStack.getItemDamage()) {
                            found = true;
                            armorStackList.remove(i);
                            armorListSize--;
                            break;
                        }
                    }
                }

                if (!found)
                    continue;

                oldRecipes.add(entry.getValue());
                newRecipes.add(new AnvilRecipeWrapper(recipe.leftInput, newRightInputs, recipe.outputs));
                break;
            }
        }
    }
}