package org.erg.deforestry.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.erg.deforestry.client.model.BoomerangChopperModel;
import org.erg.deforestry.client.model.BoomerangModel;
import org.erg.deforestry.common.entity.BoomerangChopperEntity;
import org.erg.deforestry.common.entity.BoomerangEntity;
import org.erg.deforestry.common.util.DeforestryUtil;
import org.jetbrains.annotations.NotNull;

public class BoomerangChopperRenderer extends EntityRenderer<BoomerangChopperEntity> {

    private static final ResourceLocation BOOMERANG_TEXTURE_LOCATION = DeforestryUtil.getDeforestryRLFrom("textures/entity/boomerang_chopper/boomerang_chopper.png");
    private static final int FRAMES_PER_ROTATION = 10;
    private final BoomerangChopperModel model;

    public BoomerangChopperRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new BoomerangChopperModel(ctx.bakeLayer(BoomerangChopperModel.BOOMERANG_CHOPPER_LAYER));
    }

    @Override
    public ResourceLocation getTextureLocation(BoomerangChopperEntity boomerangChopperEntity) {
        return BOOMERANG_TEXTURE_LOCATION;
    }

    @Override
    public void render(BoomerangChopperEntity boomerang, float entityYaw, float partialTick, @NotNull PoseStack matrix, @NotNull MultiBufferSource renderer, int light) {
        matrix.pushPose();
        float maxRotationFrames = boomerang.isInWaterOrBubble() ? FRAMES_PER_ROTATION * 4 : FRAMES_PER_ROTATION;
        float rotationRad = (float) (2 * Math.PI / maxRotationFrames) * (boomerang.getTicksForRotation() % maxRotationFrames);
        matrix.translate(0.0d, -1.35d, 0.0d); //Is there a better way to fix this?
        matrix.mulPose(Axis.YP.rotation(rotationRad));
        model.renderToBuffer(matrix, renderer.getBuffer(this.model.renderType(this.getTextureLocation(boomerang))), light, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        matrix.popPose();
    }

}
