package net.dakotapride.thermalnumismatics;

import cofh.lib.util.DeferredRegisterCoFH;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ThermalNumismaticsBlockEntities {

    public static DeferredRegisterCoFH<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegisterCoFH.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ThermalNumismatics.MOD_ID);

    public static RegistryObject<BlockEntityType<?>> TRADE_STATION = BLOCK_ENTITIES.register("trade_station", () -> BlockEntityType.Builder.of(TradeStationBlockEntity::new, ThermalNumismaticsBlocks.BLOCKS.get("trade_station")).build(null));

}
