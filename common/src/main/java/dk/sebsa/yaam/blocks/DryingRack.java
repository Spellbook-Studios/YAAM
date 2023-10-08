package dk.sebsa.yaam.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import dk.sebsa.yaam.blocks.entities.DryingRackEntity;

public class DryingRack extends HorizontalDirectionalBlock implements EntityBlock {
    public DryingRack(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Direction dir = state.getValue(FACING);
        return switch (dir) {
            case SOUTH -> Shapes.box(0.0, 0.875, 0.0, 1.0, 1.0, 0.125);
            case NORTH -> Shapes.box(0.0, 0.875, 0.875, 1.0, 1.0, 1.0);
            case WEST -> Shapes.box(0.875, 0.875, 0.0, 1.0, 1.0, 1.0);
            case EAST -> Shapes.box(0.0, 0.875, 0.0, 0.125, 1.0, 1.0);
            default -> Shapes.block();
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return super.getStateForPlacement(blockPlaceContext).setValue(BlockStateProperties.HORIZONTAL_FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DryingRackEntity(blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if(level.isClientSide) return InteractionResult.SUCCESS;
        DryingRackEntity blockEntity = (DryingRackEntity) level.getBlockEntity(blockPos);

        if(!player.getItemInHand(interactionHand).isEmpty()) {
            if(blockEntity.getItemStack().isEmpty()) {
                ItemStack copy = player.getItemInHand(interactionHand).copy();
                player.getItemInHand(interactionHand).setCount(copy.getCount()-1);
                copy.setCount(1);

                blockEntity.setItemStack(copy);
                return InteractionResult.SUCCESS;
            }
        } else {
            if(!blockEntity.getItemStack().isEmpty()) {
                player.getInventory().placeItemBackInInventory(blockEntity.getItemStack());
                blockEntity.setItemStack(ItemStack.EMPTY);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return (DryingRackEntity::tick);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.is(blockState2.getBlock())) {
            DryingRackEntity blockEntity = (DryingRackEntity) level.getBlockEntity(blockPos);
            Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockEntity.getItemStack());

            super.onRemove(blockState, level, blockPos, blockState2, bl);
        }
    }
}
