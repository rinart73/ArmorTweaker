package com.rinart73.armortweaker.mods.vanilla;

import com.rinart73.armortweaker.brackets.util.*;
import com.rinart73.armortweaker.mods.jei.JeiHelper;
import com.rinart73.armortweaker.mods.vanilla.util.AnvilRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.*;

public class VanillaHelper
{
    //is JEI installed
    public static boolean isJei = false;

    //ItemArmor fields
    public static final Field armorMaterial = ReflectionHelper.findField(ItemArmor.class,
            "material", "field_77878_bZ");
    public static final Field armorDamageReduceAmount = ReflectionHelper.findField(ItemArmor.class,
            "damageReduceAmount", "field_77879_b");
    public static final Field armorToughness = ReflectionHelper.findField(ItemArmor.class,
            "toughness", "field_189415_e");

    //ItemArmor.ArmorMaterial fields
    public static final Field materialName = ReflectionHelper.findField(ItemArmor.ArmorMaterial.class,
            "name", "field_179243_f");
    public static final Field materialMaxDamageFactor = ReflectionHelper.findField(ItemArmor.ArmorMaterial.class,
            "maxDamageFactor", "field_78048_f");
    public static final Field materialDamageReductionAmountArray = ReflectionHelper.findField(ItemArmor.ArmorMaterial.class,
            "damageReductionAmountArray", "field_78049_g");
    public static final Field materialEnchantability = ReflectionHelper.findField(ItemArmor.ArmorMaterial.class,
            "enchantability", "field_78055_h");
    public static final Field materialToughness = ReflectionHelper.findField(ItemArmor.ArmorMaterial.class,
            "toughness", "field_189417_k");

    //IItemArmor list. Used for updating armor when material parameters were changed
    public static final List<IItemArmor> armorList = new ArrayList<>();

    public static void rebuildArmorList() {
        armorList.clear();
        Item.REGISTRY.forEach(item -> {
            if (!(item instanceof ItemArmor))
                return;
            armorList.add(new MCItemArmor((ItemArmor) item));
        });
    }

    public static List<IItemArmor> getArmorForMaterial(IArmorMaterial material) {
        List<IItemArmor> result = new ArrayList<>();

        armorList.forEach(armor -> {
            if (armor.getMaterial().matches(material))
                result.add(armor);
        });

        return result;
    }

    //Anvil recipes. Used to add and remove anvil repair recipes
    //Map<AnvilRecipe, AnvilRecipeWrapper>
    public static final Map<AnvilRecipe, Object> anvilRecipeWrappers = new HashMap<>();

    public static void rebuildAnvilRecipes() {
        if (VanillaHelper.isJei)
            JeiHelper.rebuildAnvilRecipes();
    }
}