package dk.sebsa.yaam.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dk.sebsa.yaam.blocks.entities.DryingRackEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class DryingRackEntityRenderer implements BlockEntityRenderer<DryingRackEntity> {
    public DryingRackEntityRenderer(BlockEntityRendererProvider.Context ctx) { }

    @Override
    public void render(DryingRackEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        if(blockEntity.getItemStack().isEmpty()) return;
        poseStack.pushPose();

        switch (blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case EAST:
                poseStack.translate(0, 0.3, 0.5);
                poseStack.scale(1,1.5f,1.5f);
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                break;
            case WEST:
                poseStack.translate(1, 0.3, 0.5);
                poseStack.scale(1,1.5f,1.5f);
                poseStack.mulPose(Axis.YP.rotationDegrees(270));
                break;
            case NORTH:
                poseStack.translate(0.5, 0.3, 1);
                poseStack.scale(1.5f,1.5f,1f);
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                break;
            case SOUTH:
                poseStack.translate(0.5, 0.3, 0);
                poseStack.scale(1.5f,1.5f,1f);
                poseStack.mulPose(Axis.YP.rotationDegrees(0));
                break;
        }

        int lightAbove = LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().above());
        Minecraft.getInstance().getItemRenderer().renderStatic(blockEntity.getItemStack(), ItemDisplayContext.GROUND, lightAbove, OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, blockEntity.getLevel(), 0);

        poseStack.popPose();
    }
}
