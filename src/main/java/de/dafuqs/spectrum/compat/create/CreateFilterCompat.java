package de.dafuqs.spectrum.compat.create;

import com.simibubi.create.content.logistics.filter.FilterItem;
import com.simibubi.create.content.logistics.filter.FilterItemStack;
import net.minecraft.item.ItemStack;

public class CreateFilterCompat {
    public static boolean test(ItemStack filter, ItemStack item) {
        if (filter.getItem() instanceof FilterItem) {
            return FilterItemStack.of(filter).test(null, item);
        }
        return true;
    }
}