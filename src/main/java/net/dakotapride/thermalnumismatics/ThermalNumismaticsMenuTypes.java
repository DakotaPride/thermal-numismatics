package net.dakotapride.thermalnumismatics;

import cofh.lib.util.DeferredRegisterCoFH;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static cofh.core.util.ProxyUtils.getClientPlayer;
import static cofh.core.util.ProxyUtils.getClientWorld;

public class ThermalNumismaticsMenuTypes {

    public static DeferredRegisterCoFH<MenuType<?>> CONTAINERS = DeferredRegisterCoFH.create(ForgeRegistries.MENU_TYPES, ThermalNumismatics.MOD_ID);

    public static final RegistryObject<MenuType<TradeStationMenu>> TRADE_STATION_CONTAINER = CONTAINERS.register("trade_station", () -> IForgeMenuType.create((windowId, inv, data) -> new TradeStationMenu(windowId, getClientWorld(), data.readBlockPos(), inv, getClientPlayer())));

}
