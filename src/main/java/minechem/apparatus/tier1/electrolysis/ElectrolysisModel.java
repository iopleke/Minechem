package minechem.apparatus.tier1.electrolysis;

import minechem.apparatus.prefab.model.BasicModel;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ElectrolysisModel extends BasicModel
{

    private byte tubeCount;
    private boolean leftTube;
    private boolean rightTube;

    ModelRenderer plank;
    ModelRenderer plank2;
    ModelRenderer plank3;
    ModelRenderer plank4;
    ModelRenderer screw;
    ModelRenderer screw2;
    ModelRenderer plank5;
    ModelRenderer plank6;
    ModelRenderer plank7;
    ModelRenderer red;
    ModelRenderer red2;
    ModelRenderer red3;
    ModelRenderer cable1;
    ModelRenderer cable1m1;
    ModelRenderer cable1m2;
    ModelRenderer black;
    ModelRenderer black2;
    ModelRenderer black3;
    ModelRenderer cable2;
    ModelRenderer cable2m1;
    ModelRenderer cable2m2;
    ModelRenderer jar2;
    ModelRenderer jar2m1;
    ModelRenderer jar2m2;
    ModelRenderer jar2m3;
    ModelRenderer jar2m4;
    ModelRenderer jar2m5;
    ModelRenderer jar2m6;
    ModelRenderer jar2m7;
    ModelRenderer jar2m8;
    ModelRenderer jar3;
    ModelRenderer jar3m1;
    ModelRenderer jar3m2;
    ModelRenderer jar3m3;
    ModelRenderer jar3m4;
    ModelRenderer jar3m5;
    ModelRenderer jar3m6;
    ModelRenderer jar3m7;
    ModelRenderer jar3m8;
    ModelRenderer jar1;
    ModelRenderer jar1m1;
    ModelRenderer jar1m2;
    ModelRenderer jar1m3;
    ModelRenderer jar1m4;
    ModelRenderer jar1m5;
    ModelRenderer jar1m6;
    ModelRenderer jar1m7;
    ModelRenderer jar1m8;
    ModelRenderer jar1m9;
    ModelRenderer liquid;

    public ElectrolysisModel()
    {
        tubeCount = 0;

        plank = new ModelRenderer(this, 4, 114);
        plank.setTextureSize(128, 128);
        plank.addBox(-14.5F, -1F, -2.5F, 29, 2, 5);
        plank.setRotationPoint(0F, -5.5F, 3.5F);
        plank2 = new ModelRenderer(this, 9, 109);
        plank2.setTextureSize(128, 128);
        plank2.addBox(-3F, -1F, -1.5F, 6, 2, 3);
        plank2.setRotationPoint(-11.5F, -5.5F, -0.5F);
        plank3 = new ModelRenderer(this, 27, 109);
        plank3.setTextureSize(128, 128);
        plank3.addBox(-3F, -1F, -1.5F, 6, 2, 3);
        plank3.setRotationPoint(11.5F, -5.5F, -0.5F);
        plank4 = new ModelRenderer(this, 45, 109);
        plank4.setTextureSize(128, 128);
        plank4.addBox(-3.5F, -1F, -1.5F, 7, 2, 3);
        plank4.setRotationPoint(0F, -5.5F, -0.5F);
        screw = new ModelRenderer(this, 36, 101);
        screw.setTextureSize(128, 128);
        screw.addBox(-0.5F, -0.5F, -1.5F, 1, 1, 3);
        screw.setRotationPoint(0F, -5.5F, -3F);
        screw2 = new ModelRenderer(this, 36, 101);
        screw2.setTextureSize(128, 128);
        screw2.addBox(-0.5F, -0.5F, -1.5F, 1, 1, 3);
        screw2.setRotationPoint(0F, -5.5F, -3F);
        plank5 = new ModelRenderer(this, 47, 111);
        plank5.setTextureSize(128, 128);
        plank5.addBox(-2.5F, -1F, -0.5F, 5, 2, 1);
        plank5.setRotationPoint(0F, -5.5F, -3F);
        plank6 = new ModelRenderer(this, 47, 111);
        plank6.setTextureSize(128, 128);
        plank6.addBox(-3.5F, -1F, -0.5F, 7, 2, 1);
        plank6.setRotationPoint(6F, -5.5F, -3.5F);
        plank7 = new ModelRenderer(this, 47, 111);
        plank7.setTextureSize(128, 128);
        plank7.addBox(-3.5F, -1F, -0.5F, 7, 2, 1);
        plank7.setRotationPoint(-6F, -5.5F, -3.5F);
        red = new ModelRenderer(this, 4, 100);
        red.setTextureSize(128, 128);
        red.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        red.setRotationPoint(-2F, -7.5F, 2.5F);
        red2 = new ModelRenderer(this, 4, 103);
        red2.setTextureSize(128, 128);
        red2.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        red2.setRotationPoint(-2F, -6.5F, 2.5F);
        red3 = new ModelRenderer(this, 4, 100);
        red3.setTextureSize(128, 128);
        red3.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        red3.setRotationPoint(-2F, -7.5F, 2.5F);
        cable1 = new ModelRenderer(this, 10, 83);
        cable1.setTextureSize(128, 128);
        cable1.addBox(-0.5F, -12F, -0.5F, 1, 24, 1);
        cable1.setRotationPoint(-2F, 7.5F, 2.5F);
        cable1m1 = new ModelRenderer(this, 10, 88);
        cable1m1.setTextureSize(128, 128);
        cable1m1.addBox(-0.5F, -2F, -0.5F, 1, 4, 1);
        cable1m1.setRotationPoint(-5.535533F, 16.5F, -1.035534F);
        cable1m2 = new ModelRenderer(this, 15, 103);
        cable1m2.setTextureSize(128, 128);
        cable1m2.addBox(-3F, -0.5F, -0.5F, 6, 1, 1);
        cable1m2.setRotationPoint(-3.767766F, 19F, 0.7322322F);
        black = new ModelRenderer(this, 4, 97);
        black.setTextureSize(128, 128);
        black.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        black.setRotationPoint(2F, -7.5F, 2.5F);
        black2 = new ModelRenderer(this, 4, 103);
        black2.setTextureSize(128, 128);
        black2.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        black2.setRotationPoint(2F, -6.5F, 2.5F);
        black3 = new ModelRenderer(this, 4, 97);
        black3.setTextureSize(128, 128);
        black3.addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1);
        black3.setRotationPoint(2F, -7.5F, 2.5F);
        cable2 = new ModelRenderer(this, 10, 83);
        cable2.setTextureSize(128, 128);
        cable2.addBox(-0.5F, -12F, -0.5F, 1, 24, 1);
        cable2.setRotationPoint(2F, 7.5F, 2.5F);
        cable2m1 = new ModelRenderer(this, 10, 88);
        cable2m1.setTextureSize(128, 128);
        cable2m1.addBox(-0.5F, -2F, -0.5F, 1, 4, 1);
        cable2m1.setRotationPoint(5.535533F, 16.5F, -1.035534F);
        cable2m2 = new ModelRenderer(this, 15, 103);
        cable2m2.setTextureSize(128, 128);
        cable2m2.addBox(-3F, -0.5F, -0.5F, 6, 1, 1);
        cable2m2.setRotationPoint(3.767766F, 19F, 0.732233F);
        jar2 = new ModelRenderer(this, 13, 13);
        jar2.setTextureSize(128, 128);
        jar2.addBox(-0.5F, -0.5F, -1.5F, 1, 1, 3);
        jar2.setRotationPoint(-4.000002F, 15F, -1F);
        jar2m1 = new ModelRenderer(this, 12, 12);
        jar2m1.setTextureSize(128, 128);
        jar2m1.addBox(-0.5F, -12F, -2F, 1, 24, 4);
        jar2m1.setRotationPoint(-4.499998F, 2.5F, -1F);
        jar2m2 = new ModelRenderer(this, 15, 15);
        jar2m2.setTextureSize(128, 128);
        jar2m2.addBox(-2F, -12F, -0.5F, 4, 24, 1);
        jar2m2.setRotationPoint(-6F, 2.5F, 0.5F);
        jar2m3 = new ModelRenderer(this, 15, 15);
        jar2m3.setTextureSize(128, 128);
        jar2m3.addBox(-2F, -12F, -0.5F, 4, 24, 1);
        jar2m3.setRotationPoint(-6F, 2.5F, -2.5F);
        jar2m4 = new ModelRenderer(this, 12, 12);
        jar2m4.setTextureSize(128, 128);
        jar2m4.addBox(-0.5F, -12F, -2F, 1, 24, 4);
        jar2m4.setRotationPoint(-7.5F, 2.5F, -1F);
        jar2m5 = new ModelRenderer(this, 15, 15);
        jar2m5.setTextureSize(128, 128);
        jar2m5.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar2m5.setRotationPoint(-6F, 15F, 1F);
        jar2m6 = new ModelRenderer(this, 15, 15);
        jar2m6.setTextureSize(128, 128);
        jar2m6.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar2m6.setRotationPoint(-6F, 15F, -3F);
        jar2m7 = new ModelRenderer(this, 13, 13);
        jar2m7.setTextureSize(128, 128);
        jar2m7.addBox(-0.5F, -0.5F, -1.5F, 1, 1, 3);
        jar2m7.setRotationPoint(-8F, 15F, -1F);
        jar2m8 = new ModelRenderer(this, 13, 13);
        jar2m8.setTextureSize(128, 128);
        jar2m8.addBox(-1.5F, -0.5F, -1.5F, 3, 1, 3);
        jar2m8.setRotationPoint(-6F, -9.5F, -1F);
        jar3 = new ModelRenderer(this, 13, 13);
        jar3.setTextureSize(128, 128);
        jar3.addBox(-0.5F, -0.5F, -1.5F, 1, 1, 3);
        jar3.setRotationPoint(8F, 15F, -1F);
        jar3m1 = new ModelRenderer(this, 12, 12);
        jar3m1.setTextureSize(128, 128);
        jar3m1.addBox(-0.5F, -12F, -2F, 1, 24, 4);
        jar3m1.setRotationPoint(7.500004F, 2.5F, -1F);
        jar3m2 = new ModelRenderer(this, 15, 15);
        jar3m2.setTextureSize(128, 128);
        jar3m2.addBox(-2F, -12F, -0.5F, 4, 24, 1);
        jar3m2.setRotationPoint(6.000002F, 2.5F, 0.5F);
        jar3m3 = new ModelRenderer(this, 15, 15);
        jar3m3.setTextureSize(128, 128);
        jar3m3.addBox(-2F, -12F, -0.5F, 4, 24, 1);
        jar3m3.setRotationPoint(6.000002F, 2.5F, -2.5F);
        jar3m4 = new ModelRenderer(this, 12, 12);
        jar3m4.setTextureSize(128, 128);
        jar3m4.addBox(-0.5F, -12F, -2F, 1, 24, 4);
        jar3m4.setRotationPoint(4.500002F, 2.5F, -1F);
        jar3m5 = new ModelRenderer(this, 15, 15);
        jar3m5.setTextureSize(128, 128);
        jar3m5.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar3m5.setRotationPoint(6.000002F, 15F, 1F);
        jar3m6 = new ModelRenderer(this, 15, 15);
        jar3m6.setTextureSize(128, 128);
        jar3m6.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar3m6.setRotationPoint(6.000002F, 15F, -3F);
        jar3m7 = new ModelRenderer(this, 13, 13);
        jar3m7.setTextureSize(128, 128);
        jar3m7.addBox(-0.5F, -0.5F, -1.5F, 1, 1, 3);
        jar3m7.setRotationPoint(4.000002F, 15F, -1F);
        jar3m8 = new ModelRenderer(this, 13, 13);
        jar3m8.setTextureSize(128, 128);
        jar3m8.addBox(-1.5F, -0.5F, -1.5F, 3, 1, 3);
        jar3m8.setRotationPoint(6.000002F, -9.5F, -1F);
        jar1 = new ModelRenderer(this, 39, 0);
        jar1.setTextureSize(128, 128);
        jar1.addBox(-11F, -0.5F, -11F, 22, 1, 22);
        jar1.setRotationPoint(0F, 23F, 0F);
        jar1m1 = new ModelRenderer(this, 41, 2);
        jar1m1.setTextureSize(128, 128);
        jar1m1.addBox(-10F, -0.5F, -10F, 20, 1, 20);
        jar1m1.setRotationPoint(0F, 24F, 0F);
        jar1m2 = new ModelRenderer(this, 41, 2);
        jar1m2.setTextureSize(128, 128);
        jar1m2.addBox(-0.5F, -13.5F, -10F, 1, 27, 20);
        jar1m2.setRotationPoint(-10.5F, 9F, 0F);
        jar1m3 = new ModelRenderer(this, 41, 2);
        jar1m3.setTextureSize(128, 128);
        jar1m3.addBox(-0.5F, -13.5F, -10F, 1, 27, 20);
        jar1m3.setRotationPoint(10.5F, 9F, 0F);
        jar1m4 = new ModelRenderer(this, 39, 2);
        jar1m4.setTextureSize(128, 128);
        jar1m4.addBox(-0.5F, -13.5F, -11F, 1, 27, 22);
        jar1m4.setRotationPoint(0F, 9F, -10.5F);
        jar1m5 = new ModelRenderer(this, 39, 0);
        jar1m5.setTextureSize(128, 128);
        jar1m5.addBox(-0.5F, -13.5F, -11F, 1, 27, 22);
        jar1m5.setRotationPoint(3.755093E-06F, 9F, 10.5F);
        jar1m6 = new ModelRenderer(this, 39, 0);
        jar1m6.setTextureSize(128, 128);
        jar1m6.addBox(-0.5F, -0.5F, -11F, 1, 1, 22);
        jar1m6.setRotationPoint(3.933906E-06F, -4F, 11.5F);
        jar1m7 = new ModelRenderer(this, 39, 0);
        jar1m7.setTextureSize(128, 128);
        jar1m7.addBox(-0.5F, -0.5F, -11F, 1, 1, 22);
        jar1m7.setRotationPoint(-1.788142E-07F, -4.000004F, -11.5F);
        jar1m8 = new ModelRenderer(this, 39, 6);
        jar1m8.setTextureSize(128, 128);
        jar1m8.addBox(-0.5F, -0.5F, -12F, 1, 1, 24);
        jar1m8.setRotationPoint(-11.5F, -4F, 1.430511E-06F);
        jar1m9 = new ModelRenderer(this, 37, 1);
        jar1m9.setTextureSize(128, 128);
        jar1m9.addBox(-0.5F, -0.5F, -12F, 1, 1, 24);
        jar1m9.setRotationPoint(11.5F, -4.000004F, -4.053115E-06F);
        liquid = new ModelRenderer(this, 40, 58);
        liquid.setTextureSize(128, 128);
        liquid.addBox(-10F, -8.5F, -10F, 20, 17, 20);
        liquid.setRotationPoint(0F, 14F, 0F);

    }

    @Override
    public void render(float rotation)
    {

        plank.rotateAngleX = 0F;
        plank.rotateAngleY = 0F;
        plank.rotateAngleZ = 0F;
        plank.renderWithRotation(rotation);

        plank2.rotateAngleX = 0F;
        plank2.rotateAngleY = 0F;
        plank2.rotateAngleZ = 0F;
        plank2.renderWithRotation(rotation);

        plank3.rotateAngleX = 0F;
        plank3.rotateAngleY = 0F;
        plank3.rotateAngleZ = 0F;
        plank3.renderWithRotation(rotation);

        plank4.rotateAngleX = 0F;
        plank4.rotateAngleY = 0F;
        plank4.rotateAngleZ = 0F;
        plank4.renderWithRotation(rotation);

        screw.rotateAngleX = 0F;
        screw.rotateAngleY = 0F;
        screw.rotateAngleZ = 0F;
        screw.renderWithRotation(rotation);

        screw2.rotateAngleX = 0F;
        screw2.rotateAngleY = 0F;
        screw2.rotateAngleZ = -0.7853982F;
        screw2.renderWithRotation(rotation);

        plank5.rotateAngleX = 0F;
        plank5.rotateAngleY = 0F;
        plank5.rotateAngleZ = 0F;
        plank5.renderWithRotation(rotation);

        plank6.rotateAngleX = 0F;
        plank6.rotateAngleY = 0F;
        plank6.rotateAngleZ = 0F;
        plank6.renderWithRotation(rotation);

        plank7.rotateAngleX = 0F;
        plank7.rotateAngleY = 0F;
        plank7.rotateAngleZ = 0F;
        plank7.renderWithRotation(rotation);

        red.rotateAngleX = 0F;
        red.rotateAngleY = 0F;
        red.rotateAngleZ = 0F;
        red.renderWithRotation(rotation);

        red2.rotateAngleX = 0F;
        red2.rotateAngleY = 0F;
        red2.rotateAngleZ = 0F;
        red2.renderWithRotation(rotation);

        red3.rotateAngleX = 0F;
        red3.rotateAngleY = -0.7853982F;
        red3.rotateAngleZ = 0F;
        red3.renderWithRotation(rotation);

        cable1.rotateAngleX = 0F;
        cable1.rotateAngleY = -0.7853982F;
        cable1.rotateAngleZ = 0F;
        cable1.renderWithRotation(rotation);

        cable1m1.rotateAngleX = 0F;
        cable1m1.rotateAngleY = -0.7853982F;
        cable1m1.rotateAngleZ = 0F;
        cable1m1.renderWithRotation(rotation);

        cable1m2.rotateAngleX = 0F;
        cable1m2.rotateAngleY = -0.7853982F;
        cable1m2.rotateAngleZ = 0F;
        cable1m2.renderWithRotation(rotation);

        black.rotateAngleX = 0F;
        black.rotateAngleY = 0F;
        black.rotateAngleZ = 0F;
        black.renderWithRotation(rotation);

        black2.rotateAngleX = 0F;
        black2.rotateAngleY = 0F;
        black2.rotateAngleZ = 0F;
        black2.renderWithRotation(rotation);

        black3.rotateAngleX = 0F;
        black3.rotateAngleY = -0.7853982F;
        black3.rotateAngleZ = 0F;
        black3.renderWithRotation(rotation);

        cable2.rotateAngleX = 0F;
        cable2.rotateAngleY = -2.356194F;
        cable2.rotateAngleZ = 0F;
        cable2.renderWithRotation(rotation);

        cable2m1.rotateAngleX = 0F;
        cable2m1.rotateAngleY = -2.356194F;
        cable2m1.rotateAngleZ = 0F;
        cable2m1.renderWithRotation(rotation);

        cable2m2.rotateAngleX = 0F;
        cable2m2.rotateAngleY = -2.356194F;
        cable2m2.rotateAngleZ = 0F;
        cable2m2.renderWithRotation(rotation);

        if (leftTube)
        {
            jar2.rotateAngleX = 0F;
            jar2.rotateAngleY = 0F;
            jar2.rotateAngleZ = -3.141592F;
            jar2.renderWithRotation(rotation);

            jar2m1.rotateAngleX = 0F;
            jar2m1.rotateAngleY = 0F;
            jar2m1.rotateAngleZ = -3.141592F;
            jar2m1.renderWithRotation(rotation);

            jar2m2.rotateAngleX = 0F;
            jar2m2.rotateAngleY = 0F;
            jar2m2.rotateAngleZ = -3.141592F;
            jar2m2.renderWithRotation(rotation);

            jar2m3.rotateAngleX = 0F;
            jar2m3.rotateAngleY = 0F;
            jar2m3.rotateAngleZ = -3.141592F;
            jar2m3.renderWithRotation(rotation);

            jar2m4.rotateAngleX = 0F;
            jar2m4.rotateAngleY = 0F;
            jar2m4.rotateAngleZ = -3.141592F;
            jar2m4.renderWithRotation(rotation);

            jar2m5.rotateAngleX = 0F;
            jar2m5.rotateAngleY = 0F;
            jar2m5.rotateAngleZ = -3.141592F;
            jar2m5.renderWithRotation(rotation);

            jar2m6.rotateAngleX = 0F;
            jar2m6.rotateAngleY = 0F;
            jar2m6.rotateAngleZ = -3.141592F;
            jar2m6.renderWithRotation(rotation);

            jar2m7.rotateAngleX = 0F;
            jar2m7.rotateAngleY = 0F;
            jar2m7.rotateAngleZ = -3.141592F;
            jar2m7.renderWithRotation(rotation);

            jar2m8.rotateAngleX = 0F;
            jar2m8.rotateAngleY = 0F;
            jar2m8.rotateAngleZ = -3.141592F;
            jar2m8.renderWithRotation(rotation);
        }

        if (rightTube)
        {

            jar3.rotateAngleX = 0F;
            jar3.rotateAngleY = 0F;
            jar3.rotateAngleZ = -3.141592F;
            jar3.renderWithRotation(rotation);

            jar3m1.rotateAngleX = 0F;
            jar3m1.rotateAngleY = 0F;
            jar3m1.rotateAngleZ = -3.141592F;
            jar3m1.renderWithRotation(rotation);

            jar3m2.rotateAngleX = 0F;
            jar3m2.rotateAngleY = 0F;
            jar3m2.rotateAngleZ = -3.141592F;
            jar3m2.renderWithRotation(rotation);

            jar3m3.rotateAngleX = 0F;
            jar3m3.rotateAngleY = 0F;
            jar3m3.rotateAngleZ = -3.141592F;
            jar3m3.renderWithRotation(rotation);

            jar3m4.rotateAngleX = 0F;
            jar3m4.rotateAngleY = 0F;
            jar3m4.rotateAngleZ = -3.141592F;
            jar3m4.renderWithRotation(rotation);

            jar3m5.rotateAngleX = 0F;
            jar3m5.rotateAngleY = 0F;
            jar3m5.rotateAngleZ = -3.141592F;
            jar3m5.renderWithRotation(rotation);

            jar3m6.rotateAngleX = 0F;
            jar3m6.rotateAngleY = 0F;
            jar3m6.rotateAngleZ = -3.141592F;
            jar3m6.renderWithRotation(rotation);

            jar3m7.rotateAngleX = 0F;
            jar3m7.rotateAngleY = 0F;
            jar3m7.rotateAngleZ = -3.141592F;
            jar3m7.renderWithRotation(rotation);

            jar3m8.rotateAngleX = 0F;
            jar3m8.rotateAngleY = 0F;
            jar3m8.rotateAngleZ = -3.141592F;
            jar3m8.renderWithRotation(rotation);
        }

        GL11.glColor3f(0.0f, 1.0f, 0.0f);
        liquid.rotateAngleX = 0F;
        liquid.rotateAngleY = 0F;
        liquid.rotateAngleZ = 0F;
        liquid.renderWithRotation(rotation);
        GL11.glColor3f(1f, 1f, 1f);

        jar1.rotateAngleX = 0F;
        jar1.rotateAngleY = 0F;
        jar1.rotateAngleZ = 0F;
        jar1.renderWithRotation(rotation);

        jar1m1.rotateAngleX = 0F;
        jar1m1.rotateAngleY = 0F;
        jar1m1.rotateAngleZ = 0F;
        jar1m1.renderWithRotation(rotation);

        jar1m2.rotateAngleX = 0F;
        jar1m2.rotateAngleY = 0F;
        jar1m2.rotateAngleZ = 0F;
        jar1m2.renderWithRotation(rotation);

        jar1m3.rotateAngleX = 0F;
        jar1m3.rotateAngleY = 0F;
        jar1m3.rotateAngleZ = 0F;
        jar1m3.renderWithRotation(rotation);

        jar1m4.rotateAngleX = 0F;
        jar1m4.rotateAngleY = -1.570796F;
        jar1m4.rotateAngleZ = 0F;
        jar1m4.renderWithRotation(rotation);

        jar1m5.rotateAngleX = 0F;
        jar1m5.rotateAngleY = -1.570796F;
        jar1m5.rotateAngleZ = 0F;
        jar1m5.renderWithRotation(rotation);

        jar1m6.rotateAngleX = 0F;
        jar1m6.rotateAngleY = -1.570796F;
        jar1m6.rotateAngleZ = 0F;
        jar1m6.renderWithRotation(rotation);

        jar1m7.rotateAngleX = 0F;
        jar1m7.rotateAngleY = -1.570796F;
        jar1m7.rotateAngleZ = 0F;
        jar1m7.renderWithRotation(rotation);

        jar1m8.rotateAngleX = 0F;
        jar1m8.rotateAngleY = -3.141592F;
        jar1m8.rotateAngleZ = 0F;
        jar1m8.renderWithRotation(rotation);

        jar1m9.rotateAngleX = 0F;
        jar1m9.rotateAngleY = -3.141592F;
        jar1m9.rotateAngleZ = 0F;
        jar1m9.renderWithRotation(rotation);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
    }

    public byte getTubeCount()
    {

        return tubeCount;
    }

    public boolean getLeftTube()
    {
        return leftTube;
    }

    public boolean getRightTube()
    {
        return rightTube;
    }

    public void setLeftTube(boolean enabled)
    {
        this.leftTube = enabled;
    }

    public void setRightTube(boolean enabled)
    {
        this.rightTube = enabled;
    }

}
