package minechem.apparatus.tier1.electricCrucible;

import minechem.apparatus.prefab.model.BasicModel;
import net.minecraft.client.model.ModelRenderer;

public class ElectricCrucibleModel extends BasicModel
{
    ModelRenderer lid1;
    ModelRenderer lid2;
    ModelRenderer lid6;
    ModelRenderer lid4;
    ModelRenderer lid9;
    ModelRenderer lid5;
    ModelRenderer lid8;
    ModelRenderer lever;
    ModelRenderer lever2;
    ModelRenderer lever3;
    ModelRenderer bolt1;
    ModelRenderer bolt2;
    ModelRenderer bolt3;
    ModelRenderer bolt4;
    ModelRenderer lid3;
    ModelRenderer lid7;
    ModelRenderer hscrew1;
    ModelRenderer hscrew2;
    ModelRenderer hscrew3;
    ModelRenderer hscrew4;
    ModelRenderer hscrew5;
    ModelRenderer hscrew6;
    ModelRenderer hscrew7;
    ModelRenderer hscrew8;
    ModelRenderer hinge1;
    ModelRenderer hinge2;
    ModelRenderer hinge3;
    ModelRenderer hinge4;
    ModelRenderer hinge5;
    ModelRenderer Base;
    ModelRenderer Body;
    ModelRenderer body13;
    ModelRenderer body12;
    ModelRenderer body10;
    ModelRenderer body11;
    ModelRenderer body8;
    ModelRenderer body9;
    ModelRenderer body6;
    ModelRenderer body7;
    ModelRenderer body4;
    ModelRenderer body5;
    ModelRenderer body2;
    ModelRenderer body3;
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer leg3;
    ModelRenderer leg4;
    ModelRenderer moltenmetal;
    ModelRenderer Base2;
    ModelRenderer screen;
    ModelRenderer button1;
    ModelRenderer button1m1;
    ModelRenderer button1m2;

    public ElectricCrucibleModel()
    {
        lid1 = new ModelRenderer(this, 0, 0);
        lid1.setTextureSize(256, 256);
        lid1.addBox(-9F, -4F, -9F, 18, 8, 18);
        lid1.setRotationPoint(0F, -35F, 0F);
        lid2 = new ModelRenderer(this, 3, 3);
        lid2.setTextureSize(256, 256);
        lid2.addBox(-0.5F, -4F, -6.5F, 1, 8, 13);
        lid2.setRotationPoint(9.5F, -35F, 0F);
        lid6 = new ModelRenderer(this, 6, 6);
        lid6.setTextureSize(256, 256);
        lid6.addBox(-0.5F, -4F, -5F, 1, 8, 10);
        lid6.setRotationPoint(10.5F, -35F, 0F);
        lid4 = new ModelRenderer(this, 3, 3);
        lid4.setTextureSize(256, 256);
        lid4.addBox(-0.5F, -4F, -6.5F, 1, 8, 13);
        lid4.setRotationPoint(0F, -35F, -9.5F);
        lid9 = new ModelRenderer(this, 6, 6);
        lid9.setTextureSize(256, 256);
        lid9.addBox(-0.5F, -4F, -5F, 1, 8, 10);
        lid9.setRotationPoint(-5.960464E-07F, -35F, -10.5F);
        lid5 = new ModelRenderer(this, 3, 3);
        lid5.setTextureSize(256, 256);
        lid5.addBox(-0.5F, -4F, -6.5F, 1, 8, 13);
        lid5.setRotationPoint(0F, -35F, 9.5F);
        lid8 = new ModelRenderer(this, 6, 6);
        lid8.setTextureSize(256, 256);
        lid8.addBox(-0.5F, -4F, -5F, 1, 8, 10);
        lid8.setRotationPoint(1.192092E-07F, -35F, 10.5F);
        lever = new ModelRenderer(this, 56, 1);
        lever.setTextureSize(256, 256);
        lever.addBox(-1F, -0.5F, -0.5F, 2, 1, 1);
        lever.setRotationPoint(12F, -35F, -0.88F);
        lever2 = new ModelRenderer(this, 54, 4);
        lever2.setTextureSize(256, 256);
        lever2.addBox(-0.5F, -1F, -1F, 1, 2, 2);
        lever2.setRotationPoint(13.22882F, -35F, -0.8828833F);
        lever3 = new ModelRenderer(this, 54, 9);
        lever3.setTextureSize(256, 256);
        lever3.addBox(-4F, -1.5F, -1.5F, 8, 3, 3);
        lever3.setRotationPoint(17.72882F, -35F, -0.8828833F);
        bolt1 = new ModelRenderer(this, 5, 9);
        bolt1.setTextureSize(256, 256);
        bolt1.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
        bolt1.setRotationPoint(-18.5F, 7F, -18F);
        bolt2 = new ModelRenderer(this, 5, 9);
        bolt2.setTextureSize(256, 256);
        bolt2.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
        bolt2.setRotationPoint(18.5F, 7F, -18F);
        bolt3 = new ModelRenderer(this, 5, 9);
        bolt3.setTextureSize(256, 256);
        bolt3.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
        bolt3.setRotationPoint(18.5F, 7F, 18F);
        bolt4 = new ModelRenderer(this, 5, 9);
        bolt4.setTextureSize(256, 256);
        bolt4.addBox(-0.5F, -1.5F, -0.5F, 1, 3, 1);
        bolt4.setRotationPoint(-18.5F, 7F, 18F);
        lid3 = new ModelRenderer(this, 3, 3);
        lid3.setTextureSize(256, 256);
        lid3.addBox(-0.5F, -4F, -6.5F, 1, 8, 13);
        lid3.setRotationPoint(-9.5F, -35F, 0F);
        lid7 = new ModelRenderer(this, 6, 6);
        lid7.setTextureSize(256, 256);
        lid7.addBox(-0.5F, -4F, -5F, 1, 8, 10);
        lid7.setRotationPoint(-10.5F, -35F, 0F);
        hscrew1 = new ModelRenderer(this, 129, 122);
        hscrew1.setTextureSize(256, 256);
        hscrew1.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        hscrew1.setRotationPoint(0F, -8F, 17.5F);
        hscrew2 = new ModelRenderer(this, 129, 122);
        hscrew2.setTextureSize(256, 256);
        hscrew2.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        hscrew2.setRotationPoint(-8.526512E-14F, -19F, 17.5F);
        hscrew3 = new ModelRenderer(this, 129, 125);
        hscrew3.setTextureSize(256, 256);
        hscrew3.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        hscrew3.setRotationPoint(2F, -31F, 17.5F);
        hscrew4 = new ModelRenderer(this, 129, 125);
        hscrew4.setTextureSize(256, 256);
        hscrew4.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        hscrew4.setRotationPoint(-2F, -31F, 17.5F);
        hscrew5 = new ModelRenderer(this, 129, 125);
        hscrew5.setTextureSize(256, 256);
        hscrew5.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        hscrew5.setRotationPoint(3.576279E-07F, -30F, 18.5F);
        hscrew6 = new ModelRenderer(this, 129, 125);
        hscrew6.setTextureSize(256, 256);
        hscrew6.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        hscrew6.setRotationPoint(3.576277E-07F, -33F, 18.5F);
        hscrew7 = new ModelRenderer(this, 129, 125);
        hscrew7.setTextureSize(256, 256);
        hscrew7.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        hscrew7.setRotationPoint(2F, -36F, 16.5F);
        hscrew8 = new ModelRenderer(this, 129, 125);
        hscrew8.setTextureSize(256, 256);
        hscrew8.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        hscrew8.setRotationPoint(-2F, -36F, 16.5F);
        hinge1 = new ModelRenderer(this, 130, 128);
        hinge1.setTextureSize(256, 256);
        hinge1.addBox(-0.5F, -15.5F, -3F, 1, 31, 6);
        hinge1.setRotationPoint(0F, -19F, 17F);
        hinge2 = new ModelRenderer(this, 130, 128);
        hinge2.setTextureSize(256, 256);
        hinge2.addBox(-3.5F, -0.5F, -3F, 7, 1, 6);
        hinge2.setRotationPoint(4.973798E-14F, -35F, 14F);
        hinge3 = new ModelRenderer(this, 133, 131);
        hinge3.setTextureSize(256, 256);
        hinge3.addBox(-0.5F, -3F, -1.5F, 1, 6, 3);
        hinge3.setRotationPoint(3.576279E-07F, -31F, 18F);
        hinge4 = new ModelRenderer(this, 133, 131);
        hinge4.setTextureSize(256, 256);
        hinge4.addBox(-0.5F, -3F, -1.5F, 1, 3, 3);
        hinge4.setRotationPoint(3.576277E-07F, -34F, 18F);
        hinge5 = new ModelRenderer(this, 133, 131);
        hinge5.setTextureSize(256, 256);
        hinge5.addBox(-0.5F, -3F, -1.5F, 1, 3, 3);
        hinge5.setRotationPoint(3.576276E-07F, -36.5F, 18.7F);
        Base = new ModelRenderer(this, 58, 48);
        Base.setTextureSize(256, 256);
        Base.addBox(-22.5F, -7.5F, -25F, 45, 15, 50);
        Base.setRotationPoint(0F, 16F, -1F);
        Body = new ModelRenderer(this, 1, 146);
        Body.setTextureSize(256, 256);
        Body.addBox(-1.5F, -18F, -12F, 3, 36, 24);
        Body.setRotationPoint(10.5F, -13F, 0F);
        body13 = new ModelRenderer(this, 14, 124);
        body13.setTextureSize(256, 256);
        body13.addBox(-10F, -0.5F, -10F, 20, 1, 20);
        body13.setRotationPoint(0F, 4.5F, 0F);
        body12 = new ModelRenderer(this, 6, 208);
        body12.setTextureSize(256, 256);
        body12.addBox(-9F, -18F, -1.5F, 18, 36, 3);
        body12.setRotationPoint(0F, -13F, 10.5F);
        body10 = new ModelRenderer(this, 54, 146);
        body10.setTextureSize(256, 256);
        body10.addBox(-1.5F, -18F, -12F, 3, 36, 24);
        body10.setRotationPoint(-10.5F, -13F, 0F);
        body11 = new ModelRenderer(this, 48, 208);
        body11.setTextureSize(256, 256);
        body11.addBox(-9F, -18F, -1.5F, 18, 36, 3);
        body11.setRotationPoint(0F, -13F, -10.5F);
        body8 = new ModelRenderer(this, 0, 46);
        body8.setTextureSize(256, 256);
        body8.addBox(-1F, -18F, -10F, 2, 36, 20);
        body8.setRotationPoint(0F, -13F, 13F);
        body9 = new ModelRenderer(this, 2, 50);
        body9.setTextureSize(256, 256);
        body9.addBox(-1F, -18F, -8F, 2, 36, 16);
        body9.setRotationPoint(2.980232E-07F, -13F, 15F);
        body6 = new ModelRenderer(this, 0, 46);
        body6.setTextureSize(256, 256);
        body6.addBox(-1F, -18F, -10F, 2, 36, 20);
        body6.setRotationPoint(0F, -13F, -13F);
        body7 = new ModelRenderer(this, 2, 50);
        body7.setTextureSize(256, 256);
        body7.addBox(-1F, -18F, -8F, 2, 36, 16);
        body7.setRotationPoint(-7.748603E-07F, -13F, -15F);
        body4 = new ModelRenderer(this, 0, 46);
        body4.setTextureSize(256, 256);
        body4.addBox(-1F, -18F, -10F, 2, 36, 20);
        body4.setRotationPoint(-13F, -13F, 0F);
        body5 = new ModelRenderer(this, 2, 50);
        body5.setTextureSize(256, 256);
        body5.addBox(-1F, -18F, -8F, 2, 36, 16);
        body5.setRotationPoint(-15F, -13F, 0F);
        body2 = new ModelRenderer(this, 0, 46);
        body2.setTextureSize(256, 256);
        body2.addBox(-1F, -18F, -10F, 2, 36, 20);
        body2.setRotationPoint(13F, -13F, 0F);
        body3 = new ModelRenderer(this, 2, 50);
        body3.setTextureSize(256, 256);
        body3.addBox(-1F, -18F, -8F, 2, 36, 16);
        body3.setRotationPoint(15F, -13F, 0F);
        leg1 = new ModelRenderer(this, 3, 5);
        leg1.setTextureSize(256, 256);
        leg1.addBox(-1F, -0.5F, -1F, 2, 1, 2);
        leg1.setRotationPoint(-11F, 5.5F, -11F);
        leg2 = new ModelRenderer(this, 3, 5);
        leg2.setTextureSize(256, 256);
        leg2.addBox(-1F, -0.5F, -1F, 2, 1, 2);
        leg2.setRotationPoint(11F, 5.5F, -11F);
        leg3 = new ModelRenderer(this, 3, 5);
        leg3.setTextureSize(256, 256);
        leg3.addBox(-1F, -0.5F, -1F, 2, 1, 2);
        leg3.setRotationPoint(11F, 5.5F, 11F);
        leg4 = new ModelRenderer(this, 3, 5);
        leg4.setTextureSize(256, 256);
        leg4.addBox(-1F, -0.5F, -1F, 2, 1, 2);
        leg4.setRotationPoint(-11F, 5.5F, 11F);
        moltenmetal = new ModelRenderer(this, 118, 177);
        moltenmetal.setTextureSize(256, 256);
        moltenmetal.addBox(-10F, -11F, -10F, 20, 22, 20);
        moltenmetal.setRotationPoint(0.5F, -8F, 0F);
        Base2 = new ModelRenderer(this, 94, 2);
        Base2.setTextureSize(256, 256);
        Base2.addBox(-20F, -1F, -20F, 40, 2, 40);
        Base2.setRotationPoint(0F, 7F, 0F);
        screen = new ModelRenderer(this, 37, 43);
        screen.setTextureSize(256, 256);
        screen.addBox(-4.5F, -5F, -0.5F, 9, 10, 1);
        screen.setRotationPoint(-12F, 16F, -26.5F);
        button1 = new ModelRenderer(this, 11, 32);
        button1.setTextureSize(256, 256);
        button1.addBox(-2F, -2.5F, -0.5F, 4, 5, 1);
        button1.setRotationPoint(15F, 16F, -26.5F);
        button1m1 = new ModelRenderer(this, 28, 35);
        button1m1.setTextureSize(256, 256);
        button1m1.addBox(-1F, -1F, -0.5F, 2, 2, 1);
        button1m1.setRotationPoint(15F, 16.5F, -27.2F);
        button1m2 = new ModelRenderer(this, 28, 31);
        button1m2.setTextureSize(256, 256);
        button1m2.addBox(-1F, -1F, -0.5F, 2, 2, 1);
        button1m2.setRotationPoint(15F, 15.4819F, -27.25888F);
    }

    @Override
    public void render(float rotation)
    {
        lid1.rotateAngleX = 0F;
        lid1.rotateAngleY = 0F;
        lid1.rotateAngleZ = 0F;
        lid1.renderWithRotation(rotation);

        lid2.rotateAngleX = 0F;
        lid2.rotateAngleY = 0F;
        lid2.rotateAngleZ = 0F;
        lid2.renderWithRotation(rotation);

        lid6.rotateAngleX = 0F;
        lid6.rotateAngleY = 0F;
        lid6.rotateAngleZ = 0F;
        lid6.renderWithRotation(rotation);

        lid4.rotateAngleX = 0F;
        lid4.rotateAngleY = -1.570796F;
        lid4.rotateAngleZ = 0F;
        lid4.renderWithRotation(rotation);

        lid9.rotateAngleX = 0F;
        lid9.rotateAngleY = -1.570796F;
        lid9.rotateAngleZ = 0F;
        lid9.renderWithRotation(rotation);

        lid5.rotateAngleX = 0F;
        lid5.rotateAngleY = -1.570796F;
        lid5.rotateAngleZ = 0F;
        lid5.renderWithRotation(rotation);

        lid8.rotateAngleX = 0F;
        lid8.rotateAngleY = -1.570796F;
        lid8.rotateAngleZ = 0F;
        lid8.renderWithRotation(rotation);

        lever.rotateAngleX = 0F;
        lever.rotateAngleY = 0F;
        lever.rotateAngleZ = 0F;
        lever.renderWithRotation(rotation);

        lever2.rotateAngleX = 0F;
        lever2.rotateAngleY = 0F;
        lever2.rotateAngleZ = 0F;
        lever2.renderWithRotation(rotation);

        lever3.rotateAngleX = 0F;
        lever3.rotateAngleY = 0F;
        lever3.rotateAngleZ = 0F;
        lever3.renderWithRotation(rotation);

        bolt1.rotateAngleX = 0F;
        bolt1.rotateAngleY = 0F;
        bolt1.rotateAngleZ = 0F;
        bolt1.renderWithRotation(rotation);

        bolt2.rotateAngleX = 0F;
        bolt2.rotateAngleY = 0F;
        bolt2.rotateAngleZ = 0F;
        bolt2.renderWithRotation(rotation);

        bolt3.rotateAngleX = 0F;
        bolt3.rotateAngleY = 0F;
        bolt3.rotateAngleZ = 0F;
        bolt3.renderWithRotation(rotation);

        bolt4.rotateAngleX = 0F;
        bolt4.rotateAngleY = 0F;
        bolt4.rotateAngleZ = 0F;
        bolt4.renderWithRotation(rotation);

        lid3.rotateAngleX = 0F;
        lid3.rotateAngleY = 0F;
        lid3.rotateAngleZ = 0F;
        lid3.renderWithRotation(rotation);

        lid7.rotateAngleX = 0F;
        lid7.rotateAngleY = 0F;
        lid7.rotateAngleZ = 0F;
        lid7.renderWithRotation(rotation);

        hscrew1.rotateAngleX = 0F;
        hscrew1.rotateAngleY = -1.570796F;
        hscrew1.rotateAngleZ = 0F;
        hscrew1.renderWithRotation(rotation);

        hscrew2.rotateAngleX = 0F;
        hscrew2.rotateAngleY = -1.570796F;
        hscrew2.rotateAngleZ = 0F;
        hscrew2.renderWithRotation(rotation);

        hscrew3.rotateAngleX = 0F;
        hscrew3.rotateAngleY = -1.570796F;
        hscrew3.rotateAngleZ = 0F;
        hscrew3.renderWithRotation(rotation);

        hscrew4.rotateAngleX = 0F;
        hscrew4.rotateAngleY = -1.570796F;
        hscrew4.rotateAngleZ = 0F;
        hscrew4.renderWithRotation(rotation);

        hscrew5.rotateAngleX = 0F;
        hscrew5.rotateAngleY = -1.570796F;
        hscrew5.rotateAngleZ = 0F;
        hscrew5.renderWithRotation(rotation);

        hscrew6.rotateAngleX = 0F;
        hscrew6.rotateAngleY = -1.570796F;
        hscrew6.rotateAngleZ = 0F;
        hscrew6.renderWithRotation(rotation);

        hscrew7.rotateAngleX = 0F;
        hscrew7.rotateAngleY = -1.570796F;
        hscrew7.rotateAngleZ = 0F;
        hscrew7.renderWithRotation(rotation);

        hscrew8.rotateAngleX = 0F;
        hscrew8.rotateAngleY = -1.570796F;
        hscrew8.rotateAngleZ = 0F;
        hscrew8.renderWithRotation(rotation);

        hinge1.rotateAngleX = 0F;
        hinge1.rotateAngleY = -1.570796F;
        hinge1.rotateAngleZ = 0F;
        hinge1.renderWithRotation(rotation);

        hinge2.rotateAngleX = 0F;
        hinge2.rotateAngleY = -1.570796F;
        hinge2.rotateAngleZ = 0F;
        hinge2.renderWithRotation(rotation);

        hinge3.rotateAngleX = 0F;
        hinge3.rotateAngleY = -1.570796F;
        hinge3.rotateAngleZ = 0F;
        hinge3.renderWithRotation(rotation);

        hinge4.rotateAngleX = 2.016294E-08F;
        hinge4.rotateAngleY = -1.570796F;
        hinge4.rotateAngleZ = 0.254518F;
        hinge4.renderWithRotation(rotation);

        hinge5.rotateAngleX = 0F;
        hinge5.rotateAngleY = -1.570796F;
        hinge5.rotateAngleZ = 0F;
        hinge5.renderWithRotation(rotation);

        Base.rotateAngleX = 0F;
        Base.rotateAngleY = 0F;
        Base.rotateAngleZ = 0F;
        Base.renderWithRotation(rotation);

        Body.rotateAngleX = 0F;
        Body.rotateAngleY = 0F;
        Body.rotateAngleZ = 0F;
        Body.renderWithRotation(rotation);

        body13.rotateAngleX = 0F;
        body13.rotateAngleY = 0F;
        body13.rotateAngleZ = 0F;
        body13.renderWithRotation(rotation);

        body12.rotateAngleX = 0F;
        body12.rotateAngleY = 0F;
        body12.rotateAngleZ = 0F;
        body12.renderWithRotation(rotation);

        body10.rotateAngleX = 0F;
        body10.rotateAngleY = 0F;
        body10.rotateAngleZ = 0F;
        body10.renderWithRotation(rotation);

        body11.rotateAngleX = 0F;
        body11.rotateAngleY = 0F;
        body11.rotateAngleZ = 0F;
        body11.renderWithRotation(rotation);

        body8.rotateAngleX = 0F;
        body8.rotateAngleY = -1.570796F;
        body8.rotateAngleZ = 0F;
        body8.renderWithRotation(rotation);

        body9.rotateAngleX = 0F;
        body9.rotateAngleY = -1.570796F;
        body9.rotateAngleZ = 0F;
        body9.renderWithRotation(rotation);

        body6.rotateAngleX = 0F;
        body6.rotateAngleY = -1.570796F;
        body6.rotateAngleZ = 0F;
        body6.renderWithRotation(rotation);

        body7.rotateAngleX = 0F;
        body7.rotateAngleY = -1.570796F;
        body7.rotateAngleZ = 0F;
        body7.renderWithRotation(rotation);

        body4.rotateAngleX = 0F;
        body4.rotateAngleY = 0F;
        body4.rotateAngleZ = 0F;
        body4.renderWithRotation(rotation);

        body5.rotateAngleX = 0F;
        body5.rotateAngleY = 0F;
        body5.rotateAngleZ = 0F;
        body5.renderWithRotation(rotation);

        body2.rotateAngleX = 0F;
        body2.rotateAngleY = 0F;
        body2.rotateAngleZ = 0F;
        body2.renderWithRotation(rotation);

        body3.rotateAngleX = 0F;
        body3.rotateAngleY = 0F;
        body3.rotateAngleZ = 0F;
        body3.renderWithRotation(rotation);

        leg1.rotateAngleX = 0F;
        leg1.rotateAngleY = 0F;
        leg1.rotateAngleZ = 0F;
        leg1.renderWithRotation(rotation);

        leg2.rotateAngleX = 0F;
        leg2.rotateAngleY = 0F;
        leg2.rotateAngleZ = 0F;
        leg2.renderWithRotation(rotation);

        leg3.rotateAngleX = 0F;
        leg3.rotateAngleY = 0F;
        leg3.rotateAngleZ = 0F;
        leg3.renderWithRotation(rotation);

        leg4.rotateAngleX = 0F;
        leg4.rotateAngleY = 0F;
        leg4.rotateAngleZ = 0F;
        leg4.renderWithRotation(rotation);

        moltenmetal.rotateAngleX = 0F;
        moltenmetal.rotateAngleY = 0F;
        moltenmetal.rotateAngleZ = 0F;
        moltenmetal.renderWithRotation(rotation);

        Base2.rotateAngleX = 0F;
        Base2.rotateAngleY = 0F;
        Base2.rotateAngleZ = 0F;
        Base2.renderWithRotation(rotation);

        screen.rotateAngleX = 0F;
        screen.rotateAngleY = 0F;
        screen.rotateAngleZ = 0F;
        screen.renderWithRotation(rotation);

        button1.rotateAngleX = 0F;
        button1.rotateAngleY = 0F;
        button1.rotateAngleZ = 0F;
        button1.renderWithRotation(rotation);

        button1m1.rotateAngleX = -0.1396263F;
        button1m1.rotateAngleY = 0F;
        button1m1.rotateAngleZ = 0F;
        button1m1.renderWithRotation(rotation);

        button1m2.rotateAngleX = 0.1396264F;
        button1m2.rotateAngleY = 0F;
        button1m2.rotateAngleZ = 0F;
        button1m2.renderWithRotation(rotation);

    }

}
