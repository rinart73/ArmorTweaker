package com.rinart73.armortweaker;

import com.rinart73.armortweaker.brackets.ArmorMaterialBracketHandler;
import com.rinart73.armortweaker.mods.vanilla.VanillaHelper;
import com.rinart73.armortweaker.mods.vanilla.commands.ArmorLogger;
import com.rinart73.armortweaker.mods.vanilla.handlers.*;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import static com.rinart73.armortweaker.reference.Reference.*;

@Mod(modid = MODID, name = NAME, version = VERSION, dependencies = DEPENDENCIES)
public class ArmorTweaker
{
    @EventHandler
    public void init(FMLInitializationEvent event) {
        MineTweakerAPI.registerClass(ItemArmor.class);
        MineTweakerAPI.registerClass(ArmorMaterial.class);

        VanillaHelper.rebuildArmorList();

        ArmorMaterialBracketHandler.rebuildRegistry();
        MineTweakerAPI.registerBracketHandler(new ArmorMaterialBracketHandler());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        VanillaHelper.isJei = Loader.isModLoaded("jei");

        MineTweakerImplementationAPI.addMineTweakerCommand("armor", new String[]{
                "/minetweaker armor [ materials | items ]",
                "    Lists armor materials or armor items and their properties."}, new ArmorLogger());
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        VanillaHelper.rebuildAnvilRecipes();
    }
}