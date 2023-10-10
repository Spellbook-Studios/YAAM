package dk.sebsa.yaam.blocks.entities;

import dk.sebsa.yaam.YAAMRegistry;
import dk.sebsa.yaam.recipe.DryingRecipe;
import dk.sebsa.yaam.utils.ImplementedInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DryingRackEntity extends BlockEntity implements Container {
    private DryingRecipe currentRecipe = null;
    private int time = 0;

    public DryingRackEntity(BlockPos blockPos, BlockState blockState) {
        super(YAAMRegistry.DRYING_RACK_ENTITY.get(), blockPos, blockState);
    }

    public static <T extends BlockEntity> void tick(Level level1, BlockPos blockPos, BlockState blockState1, T blockEntity) {
        DryingRackEntity rack = (DryingRackEntity) blockEntity;
        if(rack.itemStack.isEmpty()) return;
        rack.currentRecipe = level1.getRecipeManager().getRecipeFor(DryingRecipe.Type.INSTANCE, rack, level1).orElse(null);
        if(rack.currentRecipe == null) return;

        rack.time++;
        if(rack.time >= rack.currentRecipe.getTimeMax()) rack.finish();
    }

    private void finish() {
        setItemStack(currentRecipe.getOutput().copy());
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        time = 0;
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

    private ItemStack itemStack = ItemStack.EMPTY;
    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return itemStack.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot == 0 ? itemStack : null;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return itemStack = ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return itemStack = ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if(slot==0) itemStack = stack;
    }

    @Override
    public void setChanged() {
        if(level != null && !level.isClientSide()) {
            Packet<ClientGamePacketListener> updatePacket = getUpdatePacket();
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }

        super.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        itemStack = ItemStack.EMPTY;
    }
}
