package de.dafuqs.pigment.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.world.World;

public class AutoSmeltEnchantment extends Enchantment {

    public static class AutoSmeltInventory implements Inventory, RecipeInputProvider {
        ItemStack input = ItemStack.EMPTY;

        @Override
        public int size() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return input.isEmpty();
        }

        @Override
        public ItemStack getStack(int slot) {
            return input;
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            return null;
        }

        @Override
        public ItemStack removeStack(int slot) {
            return null;
        }

        @Override
        public void setStack(int slot, ItemStack stack) {
            this.input = stack;
        }

        @Override
        public void markDirty() {
        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return false;
        }

        @Override
        public void clear() {
            input = ItemStack.EMPTY;
        }

        private SmeltingRecipe getRecipe(ItemStack itemStack, World world) {
            setStack(0, itemStack);
            SmeltingRecipe recipe = world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, this, world).orElse(null);
            return recipe;
        }

        @Override
        public void provideRecipeInputs(RecipeFinder finder) {
            finder.addItem(input);
        }

    }

    private static final AutoSmeltInventory autoSmeltInventory = new AutoSmeltInventory();

    public AutoSmeltEnchantment(Rarity weight, EquipmentSlot... slotTypes) {
        super(weight, EnchantmentTarget.DIGGER, slotTypes);
    }

    public int getMinPower(int level) {
        return 15;
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean isTreasure() {
        return false;
    }

    public boolean isAvailableForEnchantedBookOffer() {
        return true;
    }

    public boolean isAvailableForRandomSelection() {
        return true;
    }

    public boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Enchantments.SILK_TOUCH && other != PigmentEnchantments.RESONANCE;
    }

    public static ItemStack applyAutoSmelt(ItemStack inputItemStack, World world) {
       SmeltingRecipe smeltingRecipe = autoSmeltInventory.getRecipe(inputItemStack, world);
        if(!smeltingRecipe.isEmpty()) {
            ItemStack recipeOutputStack = smeltingRecipe.getOutput().copy();
            recipeOutputStack.setCount(recipeOutputStack.getCount() * inputItemStack.getCount());
            return recipeOutputStack;
        } else {
            return inputItemStack;
        }
    }

}