package de.dafuqs.pigment.blocks.decay;

import de.dafuqs.pigment.registries.PigmentBlockTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class DecayBlock1 extends DecayBlock {

    private static final EnumProperty<DecayConversion> DECAY_STATE = EnumProperty.of("decay_state", DecayConversion.class);

    public enum DecayConversion implements StringIdentifiable {
        DEFAULT("default"),
        LEAVES("leaves"),
        MAGIC_LEAVES("magic_leaves");

        private final String name;

        private DecayConversion(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public String asString() {
            return this.name;
        }
    }

    public DecayBlock1(Settings settings, Tag<Block> whiteListBlockTag, Tag<Block> blackListBlockTag, int tier, float damageOnTouching) {
        super(settings, whiteListBlockTag, blackListBlockTag, tier, damageOnTouching);
        setDefaultState(getStateManager().getDefaultState().with(DECAY_STATE, DecayConversion.DEFAULT));

        BlockState destinationBlockState = this.getDefaultState().with(DECAY_STATE, DecayConversion.LEAVES);
        addDecayConversion(BlockTags.LEAVES, destinationBlockState);

        BlockState destinationBlockState2 = this.getDefaultState().with(DECAY_STATE, DecayConversion.MAGIC_LEAVES);
        addDecayConversion(PigmentBlockTags.DECAY_MAGICAL_LEAVES, destinationBlockState2);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(DECAY_STATE);
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(DecayBlock1.DECAY_STATE).equals(DecayConversion.MAGIC_LEAVES)) {
            float xOffset = random.nextFloat();
            float zOffset = random.nextFloat();
            world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, state), pos.getX() + xOffset, pos.getY() + 1, pos.getZ() + zOffset, 0.0D, 0.0D, 0.0D);
        }
    }

}
