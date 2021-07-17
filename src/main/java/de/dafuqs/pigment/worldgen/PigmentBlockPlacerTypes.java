package de.dafuqs.pigment.worldgen;

import com.mojang.serialization.Codec;
import de.dafuqs.pigment.PigmentCommon;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.placer.*;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.List;

public class PigmentBlockPlacerTypes<P extends BlockPlacer> {

    public static final BlockPlacerType<QuitoxicReedsColumnPlacer> QUITOXIC_REEDS_COLUMN_PLACER;
    private final Codec<CallbackI.P> codec;

    public static List<BlockPlacerType<?>> types = new ArrayList<>();

    private static <P extends BlockPlacer> BlockPlacerType<P> registerBlockPlacer(String id, BlockPlacerType<P> type) {
        Registry.register(Registry.BLOCK_PLACER_TYPE, new Identifier(PigmentCommon.MOD_ID, id), type);
        types.add(type);
        return type;
    }

    private PigmentBlockPlacerTypes(Codec<CallbackI.P> codec) {
        this.codec = codec;
    }

    public Codec<CallbackI.P> getCodec() {
        return this.codec;
    }

    static {
        QUITOXIC_REEDS_COLUMN_PLACER = registerBlockPlacer("quitoxic_reeds_column_placer", new BlockPlacerType<>(QuitoxicReedsColumnPlacer.CODEC));
    }

}