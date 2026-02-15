package com.epeshi.esalium.block;

import com.epeshi.esalium.block.entity.EpicBeaconBlockEntity;
import com.epeshi.esalium.block.entity.ModBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EpicBeaconBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<EpicBeaconBlock> CODEC = createCodec(EpicBeaconBlock::new);

    public EpicBeaconBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof EpicBeaconBlockEntity) {
                player.openHandledScreen((NamedScreenHandlerFactory)blockEntity);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof EpicBeaconBlockEntity) {
                ItemScatterer.spawn(world, pos, (EpicBeaconBlockEntity)blockEntity);
                world.playSound(null, pos, SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.EPIC_BEACON_BLOCK_ENTITY, EpicBeaconBlockEntity::tick);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EpicBeaconBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean isTransparent(BlockState state, net.minecraft.world.BlockView world, BlockPos pos) {
        return true;
    }
}