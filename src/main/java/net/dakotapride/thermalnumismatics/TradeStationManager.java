package net.dakotapride.thermalnumismatics;

import cofh.lib.api.fluid.IFluidStackHolder;
import cofh.lib.api.inventory.IItemStackHolder;
import cofh.lib.util.crafting.ComparableItemStack;
import cofh.thermal.core.common.item.SlotSealItem;
import cofh.thermal.lib.util.managers.AbstractManager;
import cofh.thermal.lib.util.managers.IRecipeManager;
import cofh.thermal.lib.util.recipes.IThermalInventory;
import cofh.thermal.lib.util.recipes.ThermalRecipe;
import cofh.thermal.lib.util.recipes.internal.IMachineRecipe;
import cofh.thermal.lib.util.recipes.internal.SimpleMachineRecipe;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

import static java.util.Arrays.asList;

public class TradeStationManager extends AbstractManager implements IRecipeManager {

    private static final TradeStationManager INSTANCE = new TradeStationManager();
    protected static final int DEFAULT_ENERGY = 2400;

    protected Map<List<ComparableItemStack>, IMachineRecipe> recipeMap = new Object2ObjectOpenHashMap<>();
    protected Set<ComparableItemStack> validInputs = new ObjectOpenHashSet<>();
    protected Set<ComparableItemStack> validDies = new ObjectOpenHashSet<>();

    protected int maxOutputItems;
    protected int maxOutputFluids;

    public static TradeStationManager instance() {

        return INSTANCE;
    }

    private TradeStationManager() {

        super(DEFAULT_ENERGY);
        this.maxOutputItems = 1;
        this.maxOutputFluids = 1;
    }

    public void addRecipe(ThermalRecipe recipe) {

        if (recipe.getInputItems().size() == 1) {
            for (ItemStack recipeInput : recipe.getInputItems().get(0).getItems()) {
                addRecipe(recipe.getEnergy(), recipe.getXp(), Collections.singletonList(recipeInput), Collections.emptyList(), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids());
            }
        } else {
            // The die should never have multiple variations but eh, who knows?
            for (ItemStack dieInput : recipe.getInputItems().get(1).getItems()) {
                for (ItemStack recipeInput : recipe.getInputItems().get(0).getItems()) {
                    addRecipe(recipe.getEnergy(), recipe.getXp(), asList(recipeInput, dieInput), Collections.emptyList(), recipe.getOutputItems(), recipe.getOutputItemChances(), recipe.getOutputFluids());
                }
            }
        }
    }

    public boolean validInput(ItemStack item) {

        return validInputs.contains(makeComparable(item));
    }

    public boolean validDie(ItemStack item) {

        return validDies.contains(makeComparable(item));
    }

    protected void clear() {

        recipeMap.clear();
        validInputs.clear();
        validDies.clear();
    }

    protected ArrayList<ComparableItemStack> getKeyFromSlots(List<? extends IItemStackHolder> inputSlots) {

        ArrayList<ComparableItemStack> key = new ArrayList<>();
        for (IItemStackHolder slot : inputSlots) {
            if (!slot.isEmpty() && !(slot.getItemStack().getItem() instanceof SlotSealItem)) {
                key.add(makeComparable(slot.getItemStack()));
            }
        }
        return key;
    }

    protected ArrayList<ComparableItemStack> getKeyFromStacks(List<ItemStack> inputStacks) {

        ArrayList<ComparableItemStack> key = new ArrayList<>();
        for (ItemStack stack : inputStacks) {
            if (!stack.isEmpty() && !(stack.getItem() instanceof SlotSealItem)) {
                key.add(makeComparable(stack));
            }
        }
        return key;
    }

    protected IMachineRecipe getRecipe(List<? extends IItemStackHolder> inputSlots, List<? extends IFluidStackHolder> inputTanks) {

        if (inputSlots.isEmpty() || inputSlots.get(0).isEmpty()) {
            return null;
        }
        return recipeMap.get(getKeyFromSlots(inputSlots));
    }

    protected IMachineRecipe addRecipe(int energy, float experience, List<ItemStack> inputItems, List<FluidStack> inputFluids, List<ItemStack> outputItems, List<Float> chance, List<FluidStack> outputFluids) {

        if (inputItems.isEmpty() || outputItems.isEmpty() && outputFluids.isEmpty() || outputItems.size() > maxOutputItems || outputFluids.size() > maxOutputFluids || energy <= 0) {
            return null;
        }
        if (inputItems.get(0).isEmpty()) {
            return null;
        }
        for (ItemStack stack : outputItems) {
            if (stack.isEmpty()) {
                return null;
            }
        }
        for (FluidStack stack : outputFluids) {
            if (stack.isEmpty()) {
                return null;
            }
        }
        energy = (int) (energy * getDefaultScale());

        SimpleMachineRecipe recipe = new SimpleMachineRecipe(energy, experience, inputItems, inputFluids, outputItems, chance, outputFluids);
        validInputs.add(makeComparable(inputItems.get(0)));

        if (inputItems.size() > 1 && !inputItems.get(1).isEmpty()) {
            validDies.add(makeComparable(inputItems.get(1)));
        }
        recipeMap.put(getKeyFromStacks(inputItems), recipe);
        return recipe;
    }

    // region IRecipeManager
    @Override
    public IMachineRecipe getRecipe(IThermalInventory inventory) {

        return getRecipe(inventory.inputSlots(), inventory.inputTanks());
    }

    @Override
    public List<IMachineRecipe> getRecipeList() {

        return new ArrayList<>(recipeMap.values());
    }
    // endregion

    // region IManager
    @Override
    public void refresh(RecipeManager recipeManager) {

        clear();
        //        if (true) {
        //            ThermalCore.LOG.debug("Adding default Packing and Unpacking recipes to the Multiservo Press...");
        //            createPackingRecipes(recipeManager);
        //            for (ThermalRecipe recipe : getPackingRecipes()) {
        //                addRecipe(recipe);
        //            }
        //        }
        var recipes = recipeManager.byType(ThermalNumismaticsRecipeTypes.TRADE_STATION.get());
        for (var entry : recipes.entrySet()) {
            addRecipe(entry.getValue());
        }
    }
}
