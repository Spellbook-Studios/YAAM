package dk.sebsa.yaam.blocks.entities;

import dk.sebsa.yaam.YAAMRegistry;
import dk.sebsa.yaam.utils.ImplementedInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DryingRackEntity extends BlockEntity {
    private ItemStack itemStack = ItemStack.EMPTY;
    private int time = 0;
    private int timeGoal = -1;
    private Item result;

    public DryingRackEntity(BlockPos blockPos, BlockState blockState) {
        super(YAAMRegistry.DRYING_RACK_ENTITY.get(), blockPos, blockState);
    }

    public static <T extends BlockEntity> void tick(Level level1, BlockPos blockPos, BlockState blockState1, T blockEntity) {
        DryingRackEntity rack = (DryingRackEntity) blockEntity;
        if(rack.itemStack.isEmpty()) return;
        if(rack.timeGoal < 1) return;

        rack.time++;
        if(rack.time >= rack.timeGoal) rack.finish();
    }

    private void setTimeGoal(Item item) {
        if (item.equals(Items.ROTTEN_FLESH)) {
            timeGoal = 20 * 120;
            result = Items.LEATHER;
        } else {
            timeGoal = -1;
            result = null;
        }
    }

    private void finish() {
        setItemStack(new ItemStack(result, 1));
        time = 0;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        setTimeGoal(itemStack.getItem());
        setChanged();
    }

    private static final String ITEMS_KEY = "item";
    private static final String TIME_KEY = "timer";

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);

        CompoundTag nbtCompound = new CompoundTag();
        itemStack.save(nbtCompound);

        nbt.put(ITEMS_KEY, nbtCompound);
        nbt.putInt(TIME_KEY, time);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if(nbt.contains(TIME_KEY)) time = nbt.getInt(TIME_KEY);
        if(nbt.contains(ITEMS_KEY)) {
            CompoundTag nbtCompound = nbt.getCompound(ITEMS_KEY);
            itemStack = ItemStack.of(nbtCompound);
            setTimeGoal(itemStack.getItem());
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void setChanged() {
        if(level != null && !level.isClientSide()) {
            Packet<ClientGamePacketListener> updatePacket = getUpdatePacket();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }

        super.setChanged();
    }
}
