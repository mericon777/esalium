package com.epeshi.esalium.entity.client.model;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class EmperrorPuModelAnimation {

    public static final Animation walk = Animation.Builder.create(4.7083F).looping()
            .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.0F, 7.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.0833F, AnimationHelper.createRotationalVector(0.0F, -7.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.7083F, AnimationHelper.createRotationalVector(0.0F, 5.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.3333F, AnimationHelper.createRotationalVector(0.0F, -7.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.9583F, AnimationHelper.createRotationalVector(0.0F, 5.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(3.5417F, AnimationHelper.createRotationalVector(0.0F, -7.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(4.1667F, AnimationHelper.createRotationalVector(0.0F, 5.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(4.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.0F, -7.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.0833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.7083F, AnimationHelper.createRotationalVector(0.0F, -5.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.3333F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.9583F, AnimationHelper.createRotationalVector(0.0F, -5.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(3.5417F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(4.1667F, AnimationHelper.createRotationalVector(0.0F, -5.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(4.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("right_arm", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-47.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.0833F, AnimationHelper.createRotationalVector(7.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.7083F, AnimationHelper.createRotationalVector(-37.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.3333F, AnimationHelper.createRotationalVector(7.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.9583F, AnimationHelper.createRotationalVector(-37.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(3.5417F, AnimationHelper.createRotationalVector(7.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(4.1667F, AnimationHelper.createRotationalVector(-37.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(4.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("right_arm", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-1.0F, -2.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("left_arm", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-37.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.0833F, AnimationHelper.createRotationalVector(22.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.7083F, AnimationHelper.createRotationalVector(-32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.3333F, AnimationHelper.createRotationalVector(22.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.9583F, AnimationHelper.createRotationalVector(-32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(3.5417F, AnimationHelper.createRotationalVector(22.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(4.1667F, AnimationHelper.createRotationalVector(-32.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(4.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("left_arm", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(1.0F, -2.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("leg", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.4583F, AnimationHelper.createRotationalVector(38.21F, -2.26F, 4.76F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.0833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.7083F, AnimationHelper.createRotationalVector(38.21F, -2.26F, 4.76F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.3333F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.9583F, AnimationHelper.createRotationalVector(38.21F, -2.26F, 4.76F), Transformation.Interpolations.LINEAR),
                    new Keyframe(3.5417F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(4.1667F, AnimationHelper.createRotationalVector(38.21F, -2.26F, 4.76F), Transformation.Interpolations.LINEAR),
                    new Keyframe(4.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("leg2", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5417F, AnimationHelper.createRotationalVector(-42.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.125F, AnimationHelper.createRotationalVector(35.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.7083F, AnimationHelper.createRotationalVector(-42.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.375F, AnimationHelper.createRotationalVector(35.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.9583F, AnimationHelper.createRotationalVector(-42.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(3.5833F, AnimationHelper.createRotationalVector(35.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(4.1667F, AnimationHelper.createRotationalVector(-42.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(4.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .build();

    public static final Animation attack = Animation.Builder.create(0.9167F)
            .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-5.0F, -12.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5417F, AnimationHelper.createRotationalVector(7.5F, 17.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-10.0F, -12.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5417F, AnimationHelper.createRotationalVector(7.6768F, 17.4563F, 0.6833F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("right_arm", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-18.2361F, -7.7051F, 5.3908F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5417F, AnimationHelper.createRotationalVector(0.0F, 15.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("right_arm", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -2.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(-1.69F, -2.69F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(-1.84F, -2.84F, -1.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5417F, AnimationHelper.createTranslationalVector(1.0F, -2.0F, 2.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, -2.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0.0F, -2.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("left_arm", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.4167F, AnimationHelper.createRotationalVector(120.0F, 7.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5417F, AnimationHelper.createRotationalVector(0.0F, -25.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("left_arm", new Transformation(Transformation.Targets.TRANSLATE,
                    new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -2.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.46F, -1.08F, -1.38F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0.34F, -0.46F, -2.31F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5417F, AnimationHelper.createTranslationalVector(1.0F, -2.0F, -3.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, -2.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0.0F, -2.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("leg", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.4167F, AnimationHelper.createRotationalVector(15.1488F, -2.4491F, 0.5235F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("leg2", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-10.0F, 17.5F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.5417F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .build();

    public static final Animation sitting_idle = Animation.Builder.create(5.4167F).looping()
            .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.875F, AnimationHelper.createRotationalVector(7.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(5.4167F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(2.875F, AnimationHelper.createRotationalVector(-12.5462F, 4.8812F, -1.0848F), Transformation.Interpolations.LINEAR),
                    new Keyframe(5.4167F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .build();

    public static final Animation sit = Animation.Builder.create(1.2917F)
            .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
            ))
            .build();

    public static final Animation get_up_and_push = Animation.Builder.create(1.2083F).looping()
            .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
                    new Keyframe(0.0F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                    new Keyframe(0.2917F, AnimationHelper.createRotationalVector(2.5606F, 12.4879F, 0.554F), Transformation.Interpolations.LINEAR),
                    new Keyframe(1.0F, AnimationHelper.createRotationalVector(2.5024F, 2.4976F, 0.1091F), Transformation.Interpolations.LINEAR)
            ))
            .build();
}