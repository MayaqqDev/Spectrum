package de.dafuqs.spectrum.blocks;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.dimension.v1.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.state.*;
import net.minecraft.state.property.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.util.registry.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;

public class DeeperDownPortalBlock extends Block {

	private final static Identifier CREATE_PORTAL_ADVANCEMENT_IDENTIFIER = SpectrumCommon.locate("midgame/open_deeper_down_portal");
	private final static String CREATE_PORTAL_ADVANCEMENT_CRITERION = "opened_deeper_down_portal";

	public static final BooleanProperty FACING_UP = Properties.UP;

	protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4D, 16.0D);
	protected static final VoxelShape SHAPE_UP = Block.createCuboidShape(0.0D, 4D, 0.0D, 16.0D, 16.0D, 16.0D);

	public DeeperDownPortalBlock(Settings settings) {
		super(settings);
		this.setDefaultState((this.stateManager.getDefaultState()).with(FACING_UP, false));
	}

	@Override
	public boolean hasSidedTransparency(BlockState state) {
		return true;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		super.onBlockAdded(state, world, pos, oldState, notify);

		if (!world.isClient) { // that should be a given, but in modded you never know
			SpectrumS2CPacketSender.playParticleWithRandomOffsetAndVelocity((ServerWorld) world, Vec3d.ofCenter(pos), SpectrumParticleTypes.VOID_FOG, 30, new Vec3d(0.5, 0.0, 0.5), Vec3d.ZERO);
			if (!hasNeighboringPortals(world, pos)) {
				world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SpectrumSoundEvents.DEEPER_DOWN_PORTAL_OPEN, SoundCategory.BLOCKS, 0.75F, 0.75F);

				for (PlayerEntity nearbyPlayer : world.getEntitiesByType(EntityType.PLAYER, Box.of(Vec3d.ofCenter(pos), 16D, 16D, 16D), LivingEntity::isAlive)) {
					Support.grantAdvancementCriterion((ServerPlayerEntity) nearbyPlayer, CREATE_PORTAL_ADVANCEMENT_IDENTIFIER, CREATE_PORTAL_ADVANCEMENT_CRITERION);
				}
			}
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack handStack = player.getStackInHand(hand);
		if (handStack.isOf(SpectrumItems.BEDROCK_DUST)) {
			if (world.isClient) {
				return ActionResult.SUCCESS;
			} else {
				BlockState placedState = Blocks.BEDROCK.getDefaultState();
				world.setBlockState(pos, placedState);
				world.playSound(null, pos, placedState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
				return ActionResult.CONSUME;
			}
		}

		return ActionResult.PASS;
	}

	private boolean hasNeighboringPortals(World world, BlockPos pos) {
		for (Direction direction : Direction.Type.HORIZONTAL) {
			if (world.getBlockState(pos.offset(direction)).isOf(this)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return state.get(FACING_UP) ? SHAPE_UP : SHAPE;
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canBucketPlace(BlockState state, Fluid fluid) {
		return false;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING_UP);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (world instanceof ServerWorld
				&& !entity.hasVehicle()
				&& !entity.hasPassengers()
				&& entity.canUsePortals()) {

			RegistryKey<World> currentWorldKey = world.getRegistryKey();
			if (currentWorldKey == World.OVERWORLD) {
				if (!entity.hasPortalCooldown()) {
					entity.resetPortalCooldown();

					// => teleport to DD
					ServerWorld targetWorld = ((ServerWorld) world).getServer().getWorld(SpectrumDimensions.DIMENSION_KEY);
					if (targetWorld != null) {
						BlockPos portalPos = new BlockPos(pos.getX(), targetWorld.getTopY() - 1, pos.getZ());
						if (!targetWorld.getBlockState(portalPos).isOf(SpectrumBlocks.DEEPER_DOWN_PORTAL)) {
							targetWorld.setBlockState(portalPos, SpectrumBlocks.DEEPER_DOWN_PORTAL.getDefaultState().with(FACING_UP, true));
						}

						BlockPos targetPos = portalPos.down(3);
						if (entity instanceof PlayerEntity) {
							makeRoomAround(targetWorld, targetPos, 2);
						}
						FabricDimensions.teleport(entity, targetWorld, new TeleportTarget(Vec3d.ofCenter(targetPos), Vec3d.ZERO, entity.getYaw(), entity.getPitch()));
						teleportToSafePosition(targetWorld, entity, targetPos.down(), 5);
					}
				}
			} else {
				if (!entity.hasPortalCooldown()) {
					entity.resetPortalCooldown();

					// => teleport to Overworld
					ServerWorld targetWorld = ((ServerWorld) world).getServer().getWorld(World.OVERWORLD);
					if (targetWorld != null) {
						BlockPos portalPos = new BlockPos(pos.getX(), targetWorld.getBottomY(), pos.getZ());
						if (!targetWorld.getBlockState(portalPos).isOf(SpectrumBlocks.DEEPER_DOWN_PORTAL)) {
							targetWorld.setBlockState(portalPos, SpectrumBlocks.DEEPER_DOWN_PORTAL.getDefaultState().with(FACING_UP, false));
						}

						BlockPos targetPos = portalPos.up(2);
						makeRoomAround(targetWorld, targetPos, 2);
						FabricDimensions.teleport(entity, targetWorld, new TeleportTarget(Vec3d.ofCenter(targetPos), Vec3d.ZERO, entity.getYaw(), entity.getPitch()));
						teleportToSafePosition(targetWorld, entity, targetPos, 3);
					}
				}
			}
		}
	}

	public void makeRoomAround(World world, BlockPos blockPos, int radius) {
		BlockState state = world.getBlockState(blockPos);
		if (state.getCollisionShape(world, blockPos).isEmpty() && state.getCollisionShape(world, blockPos.up()).isEmpty()) {
			return;
		}

		for (BlockPos pos : BlockPos.iterateOutwards(blockPos, radius, radius, radius)) {
			if (world.getBlockEntity(pos) != null) {
				continue;
			}

			state = world.getBlockState(pos);

			if (state.isOf(Blocks.BEDROCK)) {
				if (pos.getX() == blockPos.getX() && pos.getZ() == blockPos.getZ()) {
					world.breakBlock(pos, true, null);
				}
				continue;
			}

			if (!state.isIn(SpectrumBlockTags.BASE_STONE_DEEPER_DOWN)) {
				continue;
			}

			float hardness = state.getHardness(world, pos);
			if (hardness >= 0 && hardness < 30) {
				world.breakBlock(pos, true, null);
			}
		}
	}

	public void teleportToSafePosition(World world, Entity entity, BlockPos targetPos, int maxRadius) {
		for (BlockPos bp : BlockPos.iterateOutwards(targetPos, maxRadius, maxRadius, maxRadius)) {
			entity.setPosition(Vec3d.ofBottomCenter(bp));
			if (world.getBlockState(bp.down()).getCollisionShape(world, bp.down()) == VoxelShapes.fullCube()
					&& world.isSpaceEmpty(entity)
					&& entity.getY() < (double) world.getTopY()
					&& entity.getY() > (double) world.getBottomY()) {

				entity.teleport(bp.getX() + 0.5, bp.getY() + 0.5, bp.getZ() + 0.5);
				return;
			}
		}

		world.removeBlock(targetPos.up(1), false);
		world.removeBlock(targetPos, false);
		world.setBlockState(targetPos.down(1), Blocks.COBBLED_DEEPSLATE.getDefaultState());
		entity.teleport(targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		if (!state.get(DeeperDownPortalBlock.FACING_UP) || random.nextInt(8) == 0) {
			spawnVoidFogParticle(world, pos, random);
		}
	}

	private static void spawnVoidFogParticle(World world, BlockPos pos, Random random) {
		double d = (double) pos.getX() + random.nextDouble();
		double e = (double) pos.getY() + 0.3D;
		double f = (double) pos.getZ() + random.nextDouble();
		world.addParticle(SpectrumParticleTypes.VOID_FOG, d, e, f, 0.0D, 0.1D, 0.0D);
	}

}
