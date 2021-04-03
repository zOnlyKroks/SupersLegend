package superworldsun.superslegend.entities.mobs.poe;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import superworldsun.superslegend.util.RenderTypes;
import superworldsun.superslegend.SupersLegend;


//public class PoeEntityRenderer extends MobRenderer<PoeEntity, PoeEntityModel> {

    /*public static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegend.modid, "textures/entity/mobs/poeentity/poeentity_body.png");
    private PlayerEntity PlayerIn;

    public PoeEntityRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new PoeEntityModel(), 0.55F);
    }

    @Override
    public void render(PoeEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        shadowSize = 0;
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<PoeEntity, PoeEntityModel>(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn))) return;
        matrixStackIn.push();
        this.entityModel.swingProgress = this.getSwingProgress(entityIn, partialTicks);

        boolean shouldSit = entityIn.isPassenger() && (entityIn.getRidingEntity() != null && entityIn.getRidingEntity().shouldRiderSit());
        this.entityModel.isSitting = shouldSit;
        this.entityModel.isChild = entityIn.isChild();
        float f = MathHelper.interpolateAngle(partialTicks, entityIn.prevRenderYawOffset, entityIn.renderYawOffset);
        float f1 = MathHelper.interpolateAngle(partialTicks, entityIn.prevRotationYawHead, entityIn.rotationYawHead);
        float f2 = f1 - f;
        if (shouldSit && entityIn.getRidingEntity() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entityIn.getRidingEntity();
            f = MathHelper.interpolateAngle(partialTicks, livingentity.prevRenderYawOffset, livingentity.renderYawOffset);
            f2 = f1 - f;
            float f3 = MathHelper.wrapDegrees(f2);
            if (f3 < -85.0F) {
                f3 = -85.0F;
            }

            if (f3 >= 85.0F) {
                f3 = 85.0F;
            }

            f = f1 - f3;
            if (f3 * f3 > 2500.0F) {
                f += f3 * 0.2F;
            }

            f2 = f1 - f;
        }

        float f6 = MathHelper.lerp(partialTicks, entityIn.prevRotationPitch, entityIn.rotationPitch);
        if (entityIn.getPose() == Pose.SLEEPING) {
            Direction direction = entityIn.getBedDirection();
            if (direction != null) {
                float f4 = entityIn.getEyeHeight(Pose.STANDING) - 0.1F;
                matrixStackIn.translate((double)((float)(-direction.getXOffset()) * f4), 0.0D, (double)((float)(-direction.getZOffset()) * f4));
            }
        }

        float f7 = this.handleRotationFloat(entityIn, partialTicks);
        this.applyRotations(entityIn, matrixStackIn, f7, f, partialTicks);
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        this.preRenderCallback(entityIn, matrixStackIn, partialTicks);
        matrixStackIn.translate(0.0D, (double)-1.501F, 0.0D);
        float f8 = 0.0F;
        float f5 = 0.0F;
        if (!shouldSit && entityIn.isAlive()) {
            f8 = MathHelper.lerp(partialTicks, entityIn.prevLimbSwingAmount, entityIn.limbSwingAmount);
            f5 = entityIn.limbSwing - entityIn.limbSwingAmount * (1.0F - partialTicks);
            if (entityIn.isChild()) {
                f5 *= 3.0F;
            }

            if (f8 > 1.0F) {
                f8 = 1.0F;
            }
        }

        this.entityModel.setLivingAnimations(entityIn, f5, f8, partialTicks);
        this.entityModel.setRotationAngles(entityIn, f5, f8, f7, f2, f6);
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = this.isVisible(entityIn);
        //boolean flag1 = !flag && !entityIn.isInvisibleToPlayer(minecraft.player);
        //boolean flag2 = minecraft.func_238206_b_(entityIn);
        float alphaForRender = this.getAlphaForRender(entityIn, partialTicks);
        RenderType rendertype = entityIn.isDaytimeMode() ? RenderTypes.getPoeInDayTime(getEntityTexture(entityIn)) : RenderTypes.getGhost(getEntityTexture(entityIn));//this.func_230496_a_(entityIn, flag, flag1, flag2);
        if (rendertype != null && !entityIn.isInvisible()) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(rendertype);
            int i = getPackedOverlay(entityIn, this.getOverlayProgress(entityIn, partialTicks));
            this.entityModel.render(matrixStackIn, ivertexbuilder, 240, i, 1.0F, 1.0F, 1.0F, alphaForRender);
        }

        if (!entityIn.isSpectator()) {
            for(LayerRenderer<PoeEntity, PoeEntityModel> layerrenderer : this.layerRenderers) {
                layerrenderer.render(matrixStackIn, bufferIn, packedLightIn, entityIn, f5, f8, partialTicks, f7, f2, f6);
            }
        }

        matrixStackIn.pop();
        net.minecraftforge.client.event.RenderNameplateEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(entityIn, entityIn.getDisplayName(), this, matrixStackIn, bufferIn, packedLightIn, partialTicks);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
        if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.canRenderName(entityIn))) {
            this.renderName(entityIn, renderNameplateEvent.getContent(), matrixStackIn, bufferIn, packedLightIn);
        }
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<PoeEntity, PoeEntityModel>(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn));
    }

    protected float getDeathMaxRotation(PoeEntity poe) {
        return 0.0F;
    }


    public float getAlphaForRender(PoeEntity entityIn, float partialTicks) {
        if(entityIn.isDaytimeMode()){
            return MathHelper.clamp((101 - Math.min(entityIn.getDaytimeCounter(), 100)) / 100F, 0, 1);
        }
        return MathHelper.clamp(((float) Math.sin((entityIn.ticksExisted + partialTicks) * 0.1F) + 1F) * 0.5F + 0.1F, 0F, 1F);
    }

    @Override
    public void preRenderCallback(PoeEntity LivingEntityIn, MatrixStack stack, float partialTickTime) {
    }

    @Override
    public ResourceLocation getEntityTexture(PoeEntity entity) {
        return TEXTURE;
    }
*/
