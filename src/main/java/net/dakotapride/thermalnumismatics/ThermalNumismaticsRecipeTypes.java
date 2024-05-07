package net.dakotapride.thermalnumismatics;

import cofh.lib.util.DeferredRegisterCoFH;
import cofh.lib.util.recipes.SerializableRecipeType;
import cofh.thermal.lib.util.recipes.MachineRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static cofh.thermal.lib.util.ThermalRecipeManagers.registerManager;

public class ThermalNumismaticsRecipeTypes {

    public static final DeferredRegisterCoFH<RecipeType<?>> RECIPE_TYPES = DeferredRegisterCoFH.create(ForgeRegistries.RECIPE_TYPES, ThermalNumismatics.MOD_ID);
    public static final DeferredRegisterCoFH<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegisterCoFH.create(ForgeRegistries.RECIPE_SERIALIZERS, ThermalNumismatics.MOD_ID);

    public static final RegistryObject<SerializableRecipeType<TradeStationRecipe>> TRADE_STATION = RECIPE_TYPES.register("trade_station", () -> new SerializableRecipeType<>(ThermalNumismatics.MOD_ID, "trade_station"));
    public static final RegistryObject<MachineRecipeSerializer<TradeStationRecipe>> TRADE_STATION_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("trade_station", () -> new MachineRecipeSerializer<>(TradeStationRecipe::new, TradeStationManager.instance().getDefaultEnergy()));


    public static void register() {
        registerManager(TradeStationManager.instance());
    }
}
