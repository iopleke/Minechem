package minechem.apparatus.tier1.opticalMicroscope;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class OpticalMicroscopeModel extends ModelBase
{
    ModelRenderer a;
    ModelRenderer b;
    ModelRenderer base1;
    ModelRenderer base2;
    ModelRenderer base3;
    ModelRenderer base4;
    ModelRenderer base5;
    ModelRenderer c;
    ModelRenderer d;
    ModelRenderer e;
    ModelRenderer lamina1;
    ModelRenderer lamina2;
    ModelRenderer lens1;
    ModelRenderer lens2;
    ModelRenderer lens3;
    ModelRenderer lensholder;
    ModelRenderer mainlens1;
    ModelRenderer mainlens2;
    ModelRenderer mainlens3;
    ModelRenderer mainlens4;
    ModelRenderer mainlens5;
    ModelRenderer mirror1;
    ModelRenderer mirror2;
    ModelRenderer mirror3;
    ModelRenderer mirror4;
    ModelRenderer mirror5;
    ModelRenderer mirror6;
    ModelRenderer pin1;
    ModelRenderer pin2;
    ModelRenderer pin3;
    ModelRenderer screw1;
    ModelRenderer screw2;
    ModelRenderer tray;
    ModelRenderer traylens1;
    ModelRenderer traylens2;
    ModelRenderer wheel1;
    ModelRenderer wheel2;
    ModelRenderer wheel3;
    ModelRenderer wheel4;

    public OpticalMicroscopeModel()
    {
        this(0.0f);
    }

    public OpticalMicroscopeModel(float scale)
    {
        base1 = new ModelRenderer(this, 1, 9);
        base1.setTextureSize(128, 64);
        base1.addBox(-2.5F, -5F, -1F, 5, 10, 2);
        base1.setRotationPoint(0.9999996F, 17F, -2.999997F);
        base2 = new ModelRenderer(this, 1, 9);
        base2.setTextureSize(128, 64);
        base2.addBox(-2.5F, -5F, -1F, 5, 10, 2);
        base2.setRotationPoint(-3F, 17F, -2.999996F);
        base3 = new ModelRenderer(this, 79, 35);
        base3.setTextureSize(128, 64);
        base3.addBox(-5F, -1F, -7F, 10, 2, 14);
        base3.setRotationPoint(-1F, 23F, -2.999997F);
        base4 = new ModelRenderer(this, 89, 28);
        base4.setTextureSize(128, 64);
        base4.addBox(-5F, -1F, -2F, 10, 2, 4);
        base4.setRotationPoint(4.000001F, 23F, 7.000002F);
        base5 = new ModelRenderer(this, 89, 28);
        base5.setTextureSize(128, 64);
        base5.addBox(-5F, -1F, -2F, 10, 2, 4);
        base5.setRotationPoint(-5.999998F, 23F, 7.000003F);
        a = new ModelRenderer(this, 2, 32);
        a.setTextureSize(128, 64);
        a.addBox(-2F, -3F, -1F, 4, 6, 2);
        a.setRotationPoint(-1F, -3.811634F, -3.940605F);
        b = new ModelRenderer(this, 14, 56);
        b.setTextureSize(128, 64);
        b.addBox(-3F, -2F, -1F, 6, 4, 2);
        b.setRotationPoint(-1.000001F, -0.9437523F, -8.036366F);
        c = new ModelRenderer(this, 95, 22);
        c.setTextureSize(128, 64);
        c.addBox(-3F, -2F, -1F, 6, 4, 2);
        c.setRotationPoint(-0.9999995F, 12.16268F, 1.140856F);
        d = new ModelRenderer(this, 2, 40);
        d.setTextureSize(128, 64);
        d.addBox(-2F, -10F, -1F, 4, 20, 2);
        d.setRotationPoint(-1.000001F, 8.477346F, -7.543516F);
        e = new ModelRenderer(this, 98, 10);
        e.setTextureSize(128, 64);
        e.addBox(-0.5F, -4F, -2F, 1, 8, 4);
        e.setRotationPoint(-0.9999988F, 11.79347F, 5.155041F);
        pin1 = new ModelRenderer(this, 47, 54);
        pin1.setTextureSize(128, 64);
        pin1.addBox(-0.5F, -0.5F, -3.5F, 1, 1, 7);
        pin1.setRotationPoint(-1F, 14F, -2.999997F);
        mirror1 = new ModelRenderer(this, 52, 49);
        mirror1.setTextureSize(128, 64);
        mirror1.addBox(-1F, -0.5F, -0.5F, 2, 1, 1);
        mirror1.setRotationPoint(-0.9999982F, 13.67735F, 7.694922F);
        mirror2 = new ModelRenderer(this, 52, 48);
        mirror2.setTextureSize(128, 64);
        mirror2.addBox(-1.5F, -0.5F, 0F, 3, 1, 0);
        mirror2.setRotationPoint(2.000002F, 12.2434F, 9.742802F);
        mirror3 = new ModelRenderer(this, 49, 40);
        mirror3.setTextureSize(128, 64);
        mirror3.addBox(0F, -0.5F, -3F, 0, 1, 6);
        mirror3.setRotationPoint(-0.9999981F, 13.10377F, 8.514074F);
        mirror4 = new ModelRenderer(this, 52, 47);
        mirror4.setTextureSize(128, 64);
        mirror4.addBox(-1.5F, -0.5F, 0F, 3, 1, 0);
        mirror4.setRotationPoint(-3.999998F, 12.2434F, 9.742804F);
        mirror5 = new ModelRenderer(this, 54, 27);
        mirror5.setTextureSize(128, 64);
        mirror5.addBox(-2.5F, -1F, -2.5F, 5, 2, 5);
        mirror5.setRotationPoint(-0.9999976F, 11.38304F, 10.97153F);
        mirror6 = new ModelRenderer(this, 49, 40);
        mirror6.setTextureSize(128, 64);
        mirror6.addBox(0F, -0.5F, -3F, 0, 1, 6);
        mirror6.setRotationPoint(-0.9999976F, 11.38304F, 10.97153F);
        lensholder = new ModelRenderer(this, 24, 30);
        lensholder.setTextureSize(128, 64);
        lensholder.addBox(-3.5F, -1F, -3.5F, 7, 2, 7);
        lensholder.setRotationPoint(-0.999999F, -2F, 3F);
        lens1 = new ModelRenderer(this, 65, 51);
        lens1.setTextureSize(128, 64);
        lens1.addBox(-1F, -2F, -1F, 2, 4, 2);
        lens1.setRotationPoint(-0.9999989F, 0.7340641F, 3.515034F);
        lens2 = new ModelRenderer(this, 65, 46);
        lens2.setTextureSize(128, 64);
        lens2.addBox(-1F, -1.5F, -1F, 2, 3, 2);
        lens2.setRotationPoint(-2.999999F, -1.646448F, 5.474874F);
        lens3 = new ModelRenderer(this, 65, 57);
        lens3.setTextureSize(128, 64);
        lens3.addBox(-1F, -2.5F, -1F, 2, 5, 2);
        lens3.setRotationPoint(1.000001F, -1.292894F, 6.535533F);
        tray = new ModelRenderer(this, 75, 53);
        tray.setTextureSize(128, 64);
        tray.addBox(-5.5F, -0.5F, -5F, 11, 1, 10);
        tray.setRotationPoint(-0.9999985F, 6.222553F, 6.137342F);
        traylens1 = new ModelRenderer(this, 26, 23);
        traylens1.setTextureSize(128, 64);
        traylens1.addBox(-3F, -0.5F, -3F, 6, 1, 6);
        traylens1.setRotationPoint(-0.9999984F, 7.041706F, 6.710919F);
        traylens2 = new ModelRenderer(this, 28, 17);
        traylens2.setTextureSize(128, 64);
        traylens2.addBox(-2.5F, -0.5F, -2.5F, 5, 1, 5);
        traylens2.setRotationPoint(-0.9999983F, 7.860857F, 7.284496F);
        screw1 = new ModelRenderer(this, 17, 45);
        screw1.setTextureSize(128, 64);
        screw1.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        screw1.setRotationPoint(2.000001F, 7.503544F, 2.218279F);
        screw2 = new ModelRenderer(this, 17, 45);
        screw2.setTextureSize(128, 64);
        screw2.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        screw2.setRotationPoint(-3.999999F, 7.503544F, 2.218279F);
        lamina1 = new ModelRenderer(this, 14, 41);
        lamina1.setTextureSize(128, 64);
        lamina1.addBox(-3.5F, 0F, -0.5F, 7, 0, 1);
        lamina1.setRotationPoint(2.000001F, 5.54513F, 5.711788F);
        lamina2 = new ModelRenderer(this, 14, 41);
        lamina2.setTextureSize(128, 64);
        lamina2.addBox(-3.5F, 0F, -0.5F, 7, 0, 1);
        lamina2.setRotationPoint(-3.999999F, 5.545128F, 5.711788F);
        mainlens1 = new ModelRenderer(this, 2, 21);
        mainlens1.setTextureSize(128, 64);
        mainlens1.addBox(-1.5F, -4F, -1.5F, 3, 8, 3);
        mainlens1.setRotationPoint(-0.9999997F, -5F, -0.5000002F);
        mainlens2 = new ModelRenderer(this, 30, 51);
        mainlens2.setTextureSize(128, 64);
        mainlens2.addBox(-2F, -0.5F, -2F, 4, 1, 4);
        mainlens2.setRotationPoint(-1F, -8.686184F, -3.081094F);
        mainlens3 = new ModelRenderer(this, 16, 49);
        mainlens3.setTextureSize(128, 64);
        mainlens3.addBox(-1.5F, -2F, -1.5F, 3, 4, 3);
        mainlens3.setRotationPoint(-1F, -10.73406F, -4.515036F);
        mainlens4 = new ModelRenderer(this, 30, 56);
        mainlens4.setTextureSize(128, 64);
        mainlens4.addBox(-2F, -1F, -2F, 4, 2, 4);
        mainlens4.setRotationPoint(-1.000001F, -13.19152F, -6.235765F);
        mainlens5 = new ModelRenderer(this, 30, 39);
        mainlens5.setTextureSize(128, 64);
        mainlens5.addBox(-2F, -0.5F, -2F, 4, 1, 4);
        mainlens5.setRotationPoint(-0.9999994F, -2.542543F, 1.220729F);
        pin2 = new ModelRenderer(this, 47, 54);
        pin2.setTextureSize(128, 64);
        pin2.addBox(-0.5F, -0.5F, -3.5F, 1, 1, 7);
        pin2.setRotationPoint(-1F, -4F, -5F);
        wheel1 = new ModelRenderer(this, 34, 47);
        wheel1.setTextureSize(128, 64);
        wheel1.addBox(-1.5F, -1.5F, -0.5F, 3, 3, 1);
        wheel1.setRotationPoint(-3.5F, -4F, -4.999999F);
        wheel2 = new ModelRenderer(this, 34, 47);
        wheel2.setTextureSize(128, 64);
        wheel2.addBox(-1.5F, -1.5F, -0.5F, 3, 3, 1);
        wheel2.setRotationPoint(1.499999F, -4F, -5F);
        pin3 = new ModelRenderer(this, 47, 54);
        pin3.setTextureSize(128, 64);
        pin3.addBox(-0.5F, -0.5F, -3.5F, 1, 1, 7);
        pin3.setRotationPoint(-1.000001F, -0.9999981F, -7.999999F);
        wheel3 = new ModelRenderer(this, 35, 44);
        wheel3.setTextureSize(128, 64);
        wheel3.addBox(-1F, -1F, -0.5F, 2, 2, 1);
        wheel3.setRotationPoint(-3.500001F, -0.9999981F, -7.999999F);
        wheel4 = new ModelRenderer(this, 35, 44);
        wheel4.setTextureSize(128, 64);
        wheel4.addBox(-1F, -1F, -0.5F, 2, 2, 1);
        wheel4.setRotationPoint(1.499999F, -0.9999981F, -8F);
    }

    public void render(float rotation)
    {
        base1.rotateAngleX = 0F;
        base1.rotateAngleY = -1.570796F;
        base1.rotateAngleZ = 0F;
        base1.renderWithRotation(rotation);

        base2.rotateAngleX = 0F;
        base2.rotateAngleY = -1.570796F;
        base2.rotateAngleZ = 0F;
        base2.renderWithRotation(rotation);

        base3.rotateAngleX = 0F;
        base3.rotateAngleY = -1.570796F;
        base3.rotateAngleZ = 0F;
        base3.renderWithRotation(rotation);

        base4.rotateAngleX = 0F;
        base4.rotateAngleY = -1.570796F;
        base4.rotateAngleZ = 0F;
        base4.renderWithRotation(rotation);

        base5.rotateAngleX = 0F;
        base5.rotateAngleY = -1.570796F;
        base5.rotateAngleZ = 0F;
        base5.renderWithRotation(rotation);

        a.rotateAngleX = 1.049903E-08F;
        a.rotateAngleY = -1.570796F;
        a.rotateAngleZ = -0.6108652F;
        a.renderWithRotation(rotation);

        b.rotateAngleX = 1.049903E-08F;
        b.rotateAngleY = -1.570796F;
        b.rotateAngleZ = -0.6108652F;
        b.renderWithRotation(rotation);

        c.rotateAngleX = 1.049903E-08F;
        c.rotateAngleY = -1.570796F;
        c.rotateAngleZ = -0.6108652F;
        c.renderWithRotation(rotation);

        d.rotateAngleX = 1.049903E-08F;
        d.rotateAngleY = -1.570796F;
        d.rotateAngleZ = -0.6108652F;
        d.renderWithRotation(rotation);

        e.rotateAngleX = 1.049903E-08F;
        e.rotateAngleY = -1.570796F;
        e.rotateAngleZ = -0.6108651F;
        e.renderWithRotation(rotation);

        pin1.rotateAngleX = 0F;
        pin1.rotateAngleY = -1.570796F;
        pin1.rotateAngleZ = 0F;
        pin1.renderWithRotation(rotation);

        mirror1.rotateAngleX = 1.049904E-08F;
        mirror1.rotateAngleY = -1.570796F;
        mirror1.rotateAngleZ = -0.6108653F;
        mirror1.renderWithRotation(rotation);

        mirror2.rotateAngleX = 1.049905E-08F;
        mirror2.rotateAngleY = -1.570796F;
        mirror2.rotateAngleZ = -0.6108654F;
        mirror2.renderWithRotation(rotation);

        mirror3.rotateAngleX = 1.049905E-08F;
        mirror3.rotateAngleY = -1.570796F;
        mirror3.rotateAngleZ = -0.6108654F;
        mirror3.renderWithRotation(rotation);

        mirror4.rotateAngleX = 1.049905E-08F;
        mirror4.rotateAngleY = -1.570796F;
        mirror4.rotateAngleZ = -0.6108654F;
        mirror4.renderWithRotation(rotation);

        mirror5.rotateAngleX = 9.689236E-09F;
        mirror5.rotateAngleY = -1.570796F;
        mirror5.rotateAngleZ = -0.3628305F;
        mirror5.renderWithRotation(rotation);

        mirror6.rotateAngleX = 1.049905E-08F;
        mirror6.rotateAngleY = -1.570796F;
        mirror6.rotateAngleZ = -0.6108654F;
        mirror6.renderWithRotation(rotation);

        lensholder.rotateAngleX = 2.557701E-08F;
        lensholder.rotateAngleY = -1.570796F;
        lensholder.rotateAngleZ = -0.7853983F;
        lensholder.renderWithRotation(rotation);

        lens1.rotateAngleX = 3.059715E-08F;
        lens1.rotateAngleY = -1.570796F;
        lens1.rotateAngleZ = -0.6108654F;
        lens1.renderWithRotation(rotation);

        lens2.rotateAngleX = 0.18405F;
        lens2.rotateAngleY = -1.758045F;
        lens2.rotateAngleZ = -1.064528F;
        lens2.renderWithRotation(rotation);

        lens3.rotateAngleX = 0.1628114F;
        lens3.rotateAngleY = -1.12931F;
        lens3.rotateAngleZ = -0.962957F;
        lens3.renderWithRotation(rotation);

        tray.rotateAngleX = 6.162199E-09F;
        tray.rotateAngleY = -1.570796F;
        tray.rotateAngleZ = -0.5608934F;
        tray.renderWithRotation(rotation);

        traylens1.rotateAngleX = 6.162196E-09F;
        traylens1.rotateAngleY = -1.570796F;
        traylens1.rotateAngleZ = -0.5608932F;
        traylens1.renderWithRotation(rotation);

        traylens2.rotateAngleX = 6.162196E-09F;
        traylens2.rotateAngleY = -1.570796F;
        traylens2.rotateAngleZ = -0.5608932F;
        traylens2.renderWithRotation(rotation);

        screw1.rotateAngleX = 6.162199E-09F;
        screw1.rotateAngleY = -1.570796F;
        screw1.rotateAngleZ = -0.5608934F;
        screw1.renderWithRotation(rotation);

        screw2.rotateAngleX = 6.162199E-09F;
        screw2.rotateAngleY = -1.570796F;
        screw2.rotateAngleZ = -0.5608934F;
        screw2.renderWithRotation(rotation);

        lamina1.rotateAngleX = 6.162199E-09F;
        lamina1.rotateAngleY = -1.570796F;
        lamina1.rotateAngleZ = -0.5608934F;
        lamina1.renderWithRotation(rotation);

        lamina2.rotateAngleX = 6.162199E-09F;
        lamina2.rotateAngleY = -1.570796F;
        lamina2.rotateAngleZ = -0.5608934F;
        lamina2.renderWithRotation(rotation);

        mainlens1.rotateAngleX = 1.049904E-08F;
        mainlens1.rotateAngleY = -1.570796F;
        mainlens1.rotateAngleZ = -0.6108653F;
        mainlens1.renderWithRotation(rotation);

        mainlens2.rotateAngleX = 1.049905E-08F;
        mainlens2.rotateAngleY = -1.570796F;
        mainlens2.rotateAngleZ = -0.6108654F;
        mainlens2.renderWithRotation(rotation);

        mainlens3.rotateAngleX = 1.049905E-08F;
        mainlens3.rotateAngleY = -1.570796F;
        mainlens3.rotateAngleZ = -0.6108654F;
        mainlens3.renderWithRotation(rotation);

        mainlens4.rotateAngleX = 1.049904E-08F;
        mainlens4.rotateAngleY = -1.570797F;
        mainlens4.rotateAngleZ = -0.6108655F;
        mainlens4.renderWithRotation(rotation);

        mainlens5.rotateAngleX = 2.557699E-08F;
        mainlens5.rotateAngleY = -1.570796F;
        mainlens5.rotateAngleZ = -0.7853982F;
        mainlens5.renderWithRotation(rotation);

        pin2.rotateAngleX = 0F;
        pin2.rotateAngleY = -1.570796F;
        pin2.rotateAngleZ = 0F;
        pin2.renderWithRotation(rotation);

        wheel1.rotateAngleX = 0F;
        wheel1.rotateAngleY = -1.570796F;
        wheel1.rotateAngleZ = 0F;
        wheel1.renderWithRotation(rotation);

        wheel2.rotateAngleX = 0F;
        wheel2.rotateAngleY = -1.570796F;
        wheel2.rotateAngleZ = 0F;
        wheel2.renderWithRotation(rotation);

        pin3.rotateAngleX = 0F;
        pin3.rotateAngleY = -1.570796F;
        pin3.rotateAngleZ = 0F;
        pin3.renderWithRotation(rotation);

        wheel3.rotateAngleX = 0F;
        wheel3.rotateAngleY = -1.570796F;
        wheel3.rotateAngleZ = 0F;
        wheel3.renderWithRotation(rotation);

        wheel4.rotateAngleX = 0F;
        wheel4.rotateAngleY = -1.570796F;
        wheel4.rotateAngleZ = 0F;
        wheel4.renderWithRotation(rotation);

    }

}
