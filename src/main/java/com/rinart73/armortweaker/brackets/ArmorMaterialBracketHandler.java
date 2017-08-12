package com.rinart73.armortweaker.brackets;

import com.rinart73.armortweaker.brackets.util.IArmorMaterial;
import com.rinart73.armortweaker.brackets.util.MCArmorMaterial;
import minetweaker.IBracketHandler;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.BracketHandler;
import net.minecraft.item.ItemArmor;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.ZenPosition;

import java.util.*;

@BracketHandler(priority = 100)
public class ArmorMaterialBracketHandler implements IBracketHandler
{
    private static final Map<String, ItemArmor.ArmorMaterial> materialNames = new HashMap<>();
    private final IJavaMethod method;

    public ArmorMaterialBracketHandler() {
        method = MineTweakerAPI.getJavaMethod(ArmorMaterialBracketHandler.class, "getArmorMaterial", String.class);
    }

    public static Map<String, ItemArmor.ArmorMaterial> getMaterialNames() {
        return materialNames;
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if (tokens.size() > 2) {
            if (tokens.get(0).getValue().equals("armorMaterial") && tokens.get(1).getValue().equals(":")) {
                return find(environment, tokens, 2, tokens.size());
            }
        }
        return null;
    }

    private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int startIndex, int endIndex) {
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            Token token = tokens.get(i);
            valueBuilder.append(token.getValue());
        }
        IArmorMaterial material = getArmorMaterial(valueBuilder.toString());
        if (material != null)
            return new ArmorMaterialReferenceSymbol(environment, valueBuilder.toString());

        MineTweakerAPI.logError("Armor material was null");
        return null;
    }

    public static IArmorMaterial getArmorMaterial(String name) {
        ItemArmor.ArmorMaterial material = materialNames.get(name);
        if (material == null)
            MineTweakerAPI.logError("Armor material was null");
        return material == null ? null : new MCArmorMaterial(material);
    }

    public static void rebuildRegistry() {
        materialNames.clear();
        for (ItemArmor.ArmorMaterial material : ItemArmor.ArmorMaterial.values()) {
            materialNames.put(material.name(), material);
        }
    }

    private class ArmorMaterialReferenceSymbol implements IZenSymbol
    {
        private final IEnvironmentGlobal environment;
        private final String name;

        public ArmorMaterialReferenceSymbol(IEnvironmentGlobal environment, String name) {
            this.environment = environment;
            this.name = name;
        }

        @Override
        public IPartialExpression instance(ZenPosition position) {
            return new ExpressionCallStatic(position, environment, method, new ExpressionString(position, name));
        }
    }
}