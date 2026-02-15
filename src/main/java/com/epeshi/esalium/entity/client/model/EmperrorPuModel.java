package com.epeshi.esalium.entity.client.model;

import com.epeshi.esalium.entity.custom.EmperrorPuEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class EmperrorPuModel<T extends EmperrorPuEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart emperrorPu;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_arm;
    private final ModelPart left_arm;
    private final ModelPart leg;
    private final ModelPart leg2;
    private final ModelPart hitbox;

    public EmperrorPuModel(ModelPart root) {
        this.root = root;

        this.hitbox = root.getChild("hitbox");
        this.emperrorPu = root.getChild("EmperrorPu");

        this.head = this.emperrorPu.getChild("head");
        this.body = this.emperrorPu.getChild("body");
        this.right_arm = this.emperrorPu.getChild("right_arm");
        this.left_arm = this.emperrorPu.getChild("left_arm");
        this.leg = this.emperrorPu.getChild("leg");
        this.leg2 = this.emperrorPu.getChild("leg2");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        // Hitbox ve Ana Model
        ModelPartData hitbox = modelPartData.addChild("hitbox", ModelPartBuilder.create().uv(72, 87).cuboid(-4.0F, -34.0F, -1.0F, 14.0F, 27.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData EmperrorPu = modelPartData.addChild("EmperrorPu", ModelPartBuilder.create(), ModelTransform.pivot(-3.0F, 21.0F, 0.0F));

        // Kafa
        ModelPartData head = EmperrorPu.addChild("head", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 9.0F, new Dilation(0.0F))
                .uv(62, 22).cuboid(-1.0F, -5.0F, -5.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, -22.0F, 3.0F));
        head.addChild("chin_r1", ModelPartBuilder.create().uv(20, 54).cuboid(-1.0F, -2.2F, 0.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, -5.0F, -0.3927F, 0.0F, 0.0F));
        head.addChild("ears", ModelPartBuilder.create().uv(28, 69).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(72, 27).cuboid(-9.0F, -2.0F, -2.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -3.0F, 1.0F));

        // Gövde
        ModelPartData body = EmperrorPu.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(3.0F, -16.0F, 0.0F));
        ModelPartData upper_body = body.addChild("upper_body", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -3.0F, 0.0F, 14.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));
        upper_body.addChild("chest_r1", ModelPartBuilder.create().uv(34, 16).cuboid(-2.0F, -2.0F, 0.0F, 10.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 1.0F, -2.0F, 0.3491F, 0.0F, 0.0F));
        ModelPartData lower_body = body.addChild("lower_body", ModelPartBuilder.create().uv(0, 34).cuboid(-5.0F, -2.0F, 0.0F, 10.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.0F, 0.0F));
        lower_body.addChild("skirt_front_r1", ModelPartBuilder.create().uv(56, 51).cuboid(-1.0F, 0.0F, -1.0F, 8.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 3.0F, 0.0F, -0.7418F, 0.0F, 0.0F));
        lower_body.addChild("skirt_front_r2", ModelPartBuilder.create().uv(44, 10).cuboid(-1.0F, 0.0F, -1.0F, 8.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 3.0F, 7.0F, 0.6109F, 0.0F, 0.0F));
        lower_body.addChild("skirt_right_r1", ModelPartBuilder.create().uv(64, 0).cuboid(-1.0F, 0.0F, -1.0F, 7.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 3.0F, 6.0F, -2.318F, 1.5549F, -1.8149F));
        lower_body.addChild("skirt_right_r2", ModelPartBuilder.create().uv(62, 16).cuboid(-1.0F, 0.0F, -1.0F, 7.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, 3.0F, 6.0F, -1.2272F, 1.5549F, -1.8149F));

        // Sağ Kol
        ModelPartData right_arm = EmperrorPu.addChild("right_arm", ModelPartBuilder.create().uv(34, 26).cuboid(0.6413F, -4.2749F, -5.0F, 1.0F, 5.0F, 10.0F, new Dilation(0.0F))
                .uv(44, 0).cuboid(-3.3587F, -3.2749F, -3.0F, 4.0F, 4.0F, 6.0F, new Dilation(0.0F))
                .uv(20, 56).cuboid(-3.3587F, -0.2749F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-6.0F, -19.0F, 3.0F, -0.0436F, 0.0F, 0.2182F));
        right_arm.addChild("cube_r1", ModelPartBuilder.create().uv(64, 6).cuboid(-1.5F, 0.5F, -2.5F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(16, 69).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-1.3587F, 9.7251F, 0.0F, -0.1745F, 0.0F, 0.0F));
        right_arm.addChild("cube_r2", ModelPartBuilder.create().uv(20, 47).cuboid(-3.0F, -2.0F, -3.0F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.3587F, -1.2749F, 1.0F, 0.0F, 0.0F, -0.3491F));

        // Sol Kol ve Kılıç
        ModelPartData left_arm = EmperrorPu.addChild("left_arm", ModelPartBuilder.create().uv(34, 41).cuboid(0.6876F, -4.766F, -5.0F, 1.0F, 5.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 47).cuboid(-3.3124F, -3.766F, -3.0F, 4.0F, 4.0F, 6.0F, new Dilation(0.0F))
                .uv(56, 26).cuboid(-3.3124F, -0.766F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(12.0F, -19.0F, 3.0F, -3.0981F, 0.0038F, 2.9235F));
        left_arm.addChild("cube_r3", ModelPartBuilder.create().uv(0, 65).cuboid(-1.5F, 0.5F, -2.5F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(70, 57).cuboid(-1.0F, -2.0F, -2.0F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.3124F, 9.234F, 0.0F, 2.9234F, 0.0F, 3.1416F));
        left_arm.addChild("cube_r4", ModelPartBuilder.create().uv(52, 65).cuboid(-3.0F, -2.0F, -3.0F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.3124F, -1.766F, 1.0F, 0.0F, 0.0F, -0.3491F));
        ModelPartData sword = left_arm.addChild("sword", ModelPartBuilder.create().uv(91, 17).cuboid(-0.0933F, -0.5288F, 1.008F, 1.0F, 1.0F, 7.0F, new Dilation(0.0F))
                .uv(91, 1).cuboid(-0.3933F, -1.5288F, -11.992F, 1.5F, 3.0F, 13.0F, new Dilation(0.0F))
                .uv(113, 23).cuboid(-0.3933F, -1.5088F, -13.992F, 1.5F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(91, 25).cuboid(-0.3933F, -1.5288F, -14.992F, 1.5F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(95, 25).cuboid(-0.3433F, -0.7288F, 7.008F, 1.5F, 1.5F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 10.0F, 5.0F, 2.8532F, -0.0238F, -3.0602F));
        sword.addChild("korumalk", ModelPartBuilder.create().uv(107, 17).cuboid(-0.5F, -3.0F, -0.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(107, 23).cuboid(-0.5F, -4.0F, 0.0F, 2.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.0933F, 0.9712F, 0.008F));

        // Bacaklar
        ModelPartData leg2 = EmperrorPu.addChild("leg2", ModelPartBuilder.create(), ModelTransform.of(6.0F, -8.0F, 2.0F, -0.1097F, -0.0543F, -0.2234F));
        leg2.addChild("cube_r5", ModelPartBuilder.create().uv(0, 57).cuboid(-2.1316F, 2.2023F, -2.9342F, 3.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-0.2145F, 7.1187F, 0.7488F, 0.2439F, -0.1412F, 0.1317F));
        leg2.addChild("cube_r6", ModelPartBuilder.create().uv(66, 65).cuboid(-2.1316F, -1.7977F, -0.9342F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.2145F, 6.1187F, 0.7488F, 0.2439F, -0.1412F, 0.1317F));
        leg2.addChild("cube_r7", ModelPartBuilder.create().uv(36, 56).cuboid(-1.0F, -3.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.2145F, 1.1187F, 0.7488F, 0.0694F, -0.1412F, 0.1317F));
        ModelPartData leg_armor2 = leg2.addChild("leg_armor2", ModelPartBuilder.create(), ModelTransform.of(-1.2145F, 7.1187F, 0.7488F, -0.0436F, 0.0F, 0.0F));
        leg_armor2.addChild("cube_r8", ModelPartBuilder.create().uv(88, 46).cuboid(-0.8F, -4.0F, -1.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, 0.0F, 0.4863F, -0.1546F, 0.0933F));
        leg_armor2.addChild("cube_r9", ModelPartBuilder.create().uv(0, 72).cuboid(-1.6F, -2.0F, -1.0F, 3.8F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.5158F, -0.161F, 0.0999F));

        ModelPartData leg = EmperrorPu.addChild("leg", ModelPartBuilder.create(), ModelTransform.of(0.0F, -7.0F, 2.0F, -0.378F, 0.3096F, -0.1325F));
        leg.addChild("cube_r10", ModelPartBuilder.create().uv(52, 57).cuboid(-2.1316F, 2.2023F, -2.9342F, 3.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-0.0514F, 6.1933F, 0.4068F, 0.2439F, -0.1412F, 0.1317F));
        leg.addChild("cube_r11", ModelPartBuilder.create().uv(36, 68).cuboid(-2.1316F, -1.7977F, -0.9342F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.0514F, 5.1933F, 0.4068F, 0.2439F, -0.1412F, 0.1317F));
        leg.addChild("cube_r12", ModelPartBuilder.create().uv(56, 39).cuboid(-1.0F, -3.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.0514F, 0.1933F, 0.4068F, 0.0694F, -0.1412F, 0.1317F));
        ModelPartData leg_armor = leg.addChild("leg_armor", ModelPartBuilder.create(), ModelTransform.of(-1.0514F, 6.1933F, 0.4068F, -0.0436F, 0.0F, 0.0F));
        leg_armor.addChild("cube_r13", ModelPartBuilder.create().uv(71, 34).cuboid(-0.8F, -4.0F, -1.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 1.0F, 0.0F, 0.4863F, -0.1546F, 0.0933F));
        leg_armor.addChild("cube_r14", ModelPartBuilder.create().uv(72, 22).cuboid(-1.6F, -2.0F, -1.0F, 3.8F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.5158F, -0.161F, 0.0999F));

        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        if (!entity.isAwakened()) {
            float timer = (entity.age + animationProgress) * 0.058F;
            float headCycle = (MathHelper.sin(timer - (float)Math.PI / 2.0F) + 1.0F) / 2.0F;
            this.head.pitch = (-5.0F + (headCycle * 12.5F)) * 0.017453292F;
            this.head.yaw = 0.0F;
            this.body.pitch = -12.5F * 0.017453292F;
            float bodyCycle = (MathHelper.sin(timer - (float)Math.PI / 2.0F) + 1.0F) / 2.0F;
            this.body.yaw = (bodyCycle * 4.8812F) * 0.017453292F;
            this.body.roll = (bodyCycle * -1.0848F) * 0.017453292F;
            this.emperrorPu.pivotY = 26.0F;
            this.leg.pitch = -1.5F;
            this.leg2.pitch = -1.5F;
            this.right_arm.pitch = -0.5F;
            this.left_arm.pitch = -2.5F;
        } else {
            this.head.yaw = headYaw * 0.017453292F;
            this.head.pitch = headPitch * 0.017453292F;
            this.leg.pitch += MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
            this.leg2.pitch += MathHelper.cos(limbAngle * 0.6662F + (float)Math.PI) * 1.4F * limbDistance;
            float armSwing = MathHelper.cos(limbAngle * 0.6662F + (float)Math.PI) * 1.4F * limbDistance;
            this.right_arm.pitch += armSwing;

            if (entity.handSwinging) {
                float swingProgress = entity.getHandSwingProgress(animationProgress);
                this.left_arm.pitch = -1.2F + MathHelper.sin(swingProgress * (float)Math.PI) * 2.5F;
                this.body.yaw = MathHelper.sin(swingProgress * (float)Math.PI) * 0.3F;
            } else {
                float leftArmBasePitch = -2.8F;
                this.left_arm.pitch = leftArmBasePitch + armSwing;
            }
            float breath = MathHelper.sin(animationProgress * 0.05F) * 0.05F;
            this.right_arm.roll += breath;
            this.left_arm.roll -= breath;
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        emperrorPu.render(matrices, vertices, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return root;
    }
}