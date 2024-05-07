package net.dakotapride.thermalnumismatics;

import cofh.core.client.event.CoreClientEvents;
import cofh.thermal.core.init.registries.ThermalCreativeTabs;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Mod(ThermalNumismatics.MOD_ID)
@Mod.EventBusSubscriber(modid = ThermalNumismatics.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ThermalNumismatics {

    public static final String MOD_ID = "thermalnumismatics";
    public static final Logger LOGGER = LogManager.getLogger();

    public ThermalNumismatics() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // ThermalNumismaticsRegistrar.ITEMS.register(bus);
        // ThermalNumismaticsRegistrar.BLOCKS.register(bus);
        // ThermalNumismaticsRegistrar.BLOCK_ENTITIES.register(bus);
        // ThermalNumismaticsRegistrar.RECIPE_TYPES.register(bus);

        ThermalNumismaticsItems.ITEMS.register(bus);
        ThermalNumismaticsBlocks.BLOCKS.register(bus);
        ThermalNumismaticsBlockEntities.BLOCK_ENTITIES.register(bus);
        ThermalNumismaticsMenuTypes.CONTAINERS.register(bus);
        ThermalNumismaticsRecipeTypes.RECIPE_TYPES.register(bus);
        ThermalNumismaticsRecipeTypes.RECIPE_SERIALIZERS.register(bus);
        ThermalNumismaticsRecipeTypes.register();
        ThermalNumismaticsBlocks.register();
    }

    @SubscribeEvent
    public static void clientEvents(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            CoreClientEvents.addNamespace(MOD_ID);
            // MenuScreens.register(ThermalNumismaticsRegistrar.TRADE_STATION_CONTAINER.get(), TradeStationScreen::new);
            MenuScreens.register(ThermalNumismaticsMenuTypes.TRADE_STATION_CONTAINER.get(), TradeStationScreen::new);
        });
    }
}