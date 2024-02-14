package de.dafuqs.spectrum.compat.create;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.compat.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.biome.v1.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.*;
import net.minecraft.world.gen.*;

public class CreateCompat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	@Override
	public void register() {
		BiomeModifications.addFeature(BiomeSelectors.tag(SpectrumBiomeTags.DD_BIOMES), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(RegistryKeys.PLACED_FEATURE, SpectrumCommon.locate("mod_integration/create/dd_zinc_ore")));
	}
	
	@Override
	public void registerClient() {}

	public static boolean testFilter(ItemStack filter, ItemStack item) {
		if (FabricLoader.getInstance().isModLoaded("create")) {
			CreateFilterCompat.test(filter, item);
		}
		return true;
	}
}
