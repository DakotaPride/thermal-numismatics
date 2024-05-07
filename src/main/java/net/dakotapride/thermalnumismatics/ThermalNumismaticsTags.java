package net.dakotapride.thermalnumismatics;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ThermalNumismaticsTags {
    public static final TagKey<Item> ALLOWED_CATALYST_FOR_TRADE_STATION = thermalNumismaticsTag("trade_station/allowed_catalysts");

    private static TagKey<Item> thermalNumismaticsTag(String name) {

        return ItemTags.create(new ResourceLocation(ThermalNumismatics.MOD_ID, name));
    }
}
