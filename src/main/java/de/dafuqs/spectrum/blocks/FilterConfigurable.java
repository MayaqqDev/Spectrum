package de.dafuqs.spectrum.blocks;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import java.util.*;

public interface FilterConfigurable {

    List<ItemStack> getItemFilters();

    void setFilterItem(int slot, ItemStack item);

    default void writeFilterNbt(NbtCompound tag, List<ItemStack> filterItems) {
        for (int i = 0; i < filterItems.size(); i++) {
            tag.putString("Filter" + i, Registries.ITEM.getId(filterItems.get(i).getItem()).toString());
            tag.put("Filter" + i + "nbt", filterItems.get(i).getNbt());
        }
    }

    default void readFilterNbt(NbtCompound tag, List<ItemStack> filterItems) {
        for (int i = 0; i < filterItems.size(); i++) {
            if (tag.contains("Filter" + i, NbtElement.STRING_TYPE)) {
                ItemStack stack = new ItemStack(Registries.ITEM.get(new Identifier(tag.getString("Filter" + i))));
                stack.setNbt(tag.getCompound("Filter" + i + "nbt"));
                filterItems.set(i, stack);
            }
        }
    }

    static Inventory getFilterInventoryFromPacket(PacketByteBuf packetByteBuf) {
        int size = packetByteBuf.readInt();
        Inventory inventory = new SimpleInventory(size);
        for (int i = 0; i < size; i++) {
            ItemStack stack = Registries.ITEM.get(packetByteBuf.readIdentifier()).getDefaultStack();
            stack.setNbt(packetByteBuf.readNbt());
			inventory.setStack(i, stack);
        }
        return inventory;
    }

    static Inventory getFilterInventoryFromItems(List<ItemStack> items) {
        Inventory inventory = new SimpleInventory(items.size());
        for (int i = 0; i < items.size(); i++) {
            inventory.setStack(i, items.get(i));
        }
        return inventory;
    }

    static void writeScreenOpeningData(PacketByteBuf buf, List<ItemStack> filterItems) {
        buf.writeInt(filterItems.size());
        for (ItemStack filterItem : filterItems) {
			buf.writeIdentifier(Registries.ITEM.getId(filterItem.getItem()));
            buf.writeNbt(filterItem.getNbt());
        }
    }

    default boolean hasEmptyFilter() {
        for (ItemStack item : getItemFilters()) {
            if (!Objects.equals(item, new ItemStack(Items.AIR))) {
                return false;
            }
        }
        return true;
    }
}
