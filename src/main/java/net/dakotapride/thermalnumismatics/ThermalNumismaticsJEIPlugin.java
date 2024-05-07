package net.dakotapride.thermalnumismatics;

import cofh.thermal.expansion.compat.jei.machine.PotionFluidRecipeManagerPlugin;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;

@JeiPlugin
public class ThermalNumismaticsJEIPlugin implements IModPlugin {

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {

        RecipeManager recipeManager = getRecipeManager();
        if (recipeManager == null) {
            // TODO: Log an error.
            return;
        }

        registration.addRecipes(TRADE_STATION_TYPE, recipeManager.getAllRecipesFor(ThermalNumismaticsRecipeTypes.TRADE_STATION.get()));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {

        registration.addRecipeCategories(new TradeStationRecipeCategory(registration.getJeiHelpers().getGuiHelper(), new ItemStack(ThermalNumismaticsBlocks.BLOCKS.get("trade_station")), TRADE_STATION_TYPE));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {

        int progressY = 35;
        int progressW = 24;
        int progressH = 16;

        registration.addRecipeClickArea(TradeStationScreen.class, 79, progressY, progressW, progressH, TRADE_STATION_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ThermalNumismaticsBlocks.BLOCKS.get("trade_station")), TRADE_STATION_TYPE);
    }

    @Override
    public void registerAdvanced(IAdvancedRegistration registration) {

        registration.addRecipeManagerPlugin(new PotionFluidRecipeManagerPlugin());
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {

        return new ResourceLocation(ID_THERMAL, "numismatics");
    }

    private RecipeManager getRecipeManager() {

        RecipeManager recipeManager = null;
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            recipeManager = level.getRecipeManager();
        }
        return recipeManager;
    }

    public static final RecipeType<TradeStationRecipe> TRADE_STATION_TYPE = new RecipeType<>(ThermalNumismaticsRecipeTypes.TRADE_STATION.getId(), TradeStationRecipe.class);
}
