package minechem.apparatus.tier1.centrifuge;

import minechem.apparatus.prefab.model.BasicModel;
import net.minecraft.client.model.ModelRenderer;

public class CentrifugeModel extends BasicModel
{
    ModelRenderer center;
    ModelRenderer screw1;
    ModelRenderer holder1;
    ModelRenderer holder2;
    ModelRenderer holder3;
    ModelRenderer holder4;
    ModelRenderer holder5;
    ModelRenderer holder6;
    ModelRenderer jar1;
    ModelRenderer jar1m1;
    ModelRenderer jar1m2;
    ModelRenderer jar1m3;
    ModelRenderer jar1m4;
    ModelRenderer jar1m5;
    ModelRenderer jar1m6;
    ModelRenderer jar1m7;
    ModelRenderer jar1m8;
    ModelRenderer jar1fluid;
    ModelRenderer jar2;
    ModelRenderer jar2m1;
    ModelRenderer jar2m2;
    ModelRenderer jar2m3;
    ModelRenderer jar2m4;
    ModelRenderer jar2m5;
    ModelRenderer jar2m6;
    ModelRenderer jar2m7;
    ModelRenderer jar2m8;
    ModelRenderer jar2fluid;
    ModelRenderer jar3;
    ModelRenderer jar3m1;
    ModelRenderer jar3m2;
    ModelRenderer jar3m3;
    ModelRenderer jar3m4;
    ModelRenderer jar3m5;
    ModelRenderer jar3m6;
    ModelRenderer jar3m7;
    ModelRenderer jar3m8;
    ModelRenderer jar3fluid;
    ModelRenderer jar4;
    ModelRenderer jar4m1;
    ModelRenderer jar4m2;
    ModelRenderer jar4m3;
    ModelRenderer jar4m4;
    ModelRenderer jar4m5;
    ModelRenderer jar4m6;
    ModelRenderer jar4m7;
    ModelRenderer jar4m8;
    ModelRenderer jar4liquid;
    ModelRenderer jar5;
    ModelRenderer jar5m1;
    ModelRenderer jar5m2;
    ModelRenderer jar5m3;
    ModelRenderer jar5m4;
    ModelRenderer jar5m5;
    ModelRenderer jar5m6;
    ModelRenderer jar5m7;
    ModelRenderer jar5m8;
    ModelRenderer jar5liquid;
    ModelRenderer jar6;
    ModelRenderer jar6m1;
    ModelRenderer jar6m2;
    ModelRenderer jar6m3;
    ModelRenderer jar6m4;
    ModelRenderer jar6m5;
    ModelRenderer jar6m6;
    ModelRenderer jar6m7;
    ModelRenderer jar6m8;
    ModelRenderer jar6liquid;
    ModelRenderer bowl1;
    ModelRenderer bowl2;
    ModelRenderer bowl3;
    ModelRenderer bowl4;
    ModelRenderer bowl5;
    ModelRenderer shell1;
    ModelRenderer shell2;
    ModelRenderer shell3;
    ModelRenderer shell4;
    ModelRenderer button1;
    ModelRenderer button2;
    ModelRenderer button3;
    ModelRenderer button4;
    ModelRenderer lid1;
    ModelRenderer lid2;
    ModelRenderer lid3;
    ModelRenderer lid4;
    ModelRenderer lid5;
    ModelRenderer bisagra1;
    ModelRenderer bisagra2;
    ModelRenderer bisagra3;
    ModelRenderer bisagra4;
    ModelRenderer bottomness;

    public CentrifugeModel()
    {
        center = new ModelRenderer(this, 87, 6);
        center.setTextureSize(256, 256);
        center.addBox(-3.5F, -7.5F, -3.5F, 7, 15, 7);
        center.setRotationPoint(0F, 29F, 0F);
        screw1 = new ModelRenderer(this, 14, 40);
        screw1.setTextureSize(256, 256);
        screw1.addBox(-1F, -0.5F, -1F, 2, 1, 2);
        screw1.setRotationPoint(0F, 21F, 0F);
        holder1 = new ModelRenderer(this, 7, 47);
        holder1.setTextureSize(256, 256);
        holder1.addBox(-5F, -0.5F, -2.5F, 10, 1, 5);
        holder1.setRotationPoint(7.515834F, 20.52452F, 1.573917E-08F);
        holder2 = new ModelRenderer(this, 7, 47);
        holder2.setTextureSize(256, 256);
        holder2.addBox(-5F, -0.5F, -2.5F, 10, 1, 5);
        holder2.setRotationPoint(3.757917F, 20.52452F, 6.508904F);
        holder3 = new ModelRenderer(this, 7, 47);
        holder3.setTextureSize(256, 256);
        holder3.addBox(-5F, -0.5F, -2.5F, 10, 1, 5);
        holder3.setRotationPoint(-3.757918F, 20.52452F, 6.508904F);
        holder4 = new ModelRenderer(this, 7, 47);
        holder4.setTextureSize(256, 256);
        holder4.addBox(-5F, -0.5F, -2.5F, 10, 1, 5);
        holder4.setRotationPoint(3.757917F, 20.52452F, -6.508904F);
        holder5 = new ModelRenderer(this, 7, 47);
        holder5.setTextureSize(256, 256);
        holder5.addBox(-5F, -0.5F, -2.5F, 10, 1, 5);
        holder5.setRotationPoint(-3.757918F, 20.52452F, -6.508904F);
        holder6 = new ModelRenderer(this, 7, 47);
        holder6.setTextureSize(256, 256);
        holder6.addBox(-5F, -0.5F, -2.5F, 10, 1, 5);
        holder6.setRotationPoint(-7.515834F, 20.52452F, 5.677635E-07F);
        jar1 = new ModelRenderer(this, 13, 13);
        jar1.setTextureSize(256, 256);
        jar1.addBox(-2.5F, -6.5F, -1.5F, 1, 1, 3);
        jar1.setRotationPoint(-5F, 24F, -8.660254F);
        jar1m1 = new ModelRenderer(this, 12, 12);
        jar1m1.setTextureSize(256, 256);
        jar1m1.addBox(-0.5F, -7F, -2F, 1, 14, 4);
        jar1m1.setRotationPoint(-5.853854F, 24.5777F, -10.13917F);
        jar1m2 = new ModelRenderer(this, 15, 15);
        jar1m2.setTextureSize(256, 256);
        jar1m2.addBox(-2F, -7F, -0.5F, 4, 14, 1);
        jar1m2.setRotationPoint(-6.428448F, 24.96593F, -8.134397F);
        jar1m3 = new ModelRenderer(this, 15, 15);
        jar1m3.setTextureSize(256, 256);
        jar1m3.addBox(-2F, -7F, -0.5F, 4, 14, 1);
        jar1m3.setRotationPoint(-3.830371F, 24.96593F, -9.634398F);
        jar1m4 = new ModelRenderer(this, 12, 12);
        jar1m4.setTextureSize(256, 256);
        jar1m4.addBox(-0.5F, -7F, -2F, 1, 14, 4);
        jar1m4.setRotationPoint(-4.404965F, 25.35415F, -7.629622F);
        jar1m5 = new ModelRenderer(this, 15, 15);
        jar1m5.setTextureSize(256, 256);
        jar1m5.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar1m5.setRotationPoint(-5.955593F, 18.20444F, -6.31539F);
        jar1m6 = new ModelRenderer(this, 15, 15);
        jar1m6.setTextureSize(256, 256);
        jar1m6.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar1m6.setRotationPoint(-2.491492F, 18.20444F, -8.31539F);
        jar1m7 = new ModelRenderer(this, 13, 13);
        jar1m7.setTextureSize(256, 256);
        jar1m7.addBox(-0.5F, -0.5F, -1.5F, 1, 1, 3);
        jar1m7.setRotationPoint(-3.257617F, 18.72208F, -5.642357F);
        jar1m8 = new ModelRenderer(this, 13, 13);
        jar1m8.setTextureSize(256, 256);
        jar1m8.addBox(-1.5F, -0.5F, -1.5F, 3, 1, 3);
        jar1m8.setRotationPoint(-6.035276F, 31.72741F, -10.4534F);
        jar1fluid = new ModelRenderer(this, 9, 140);
        jar1fluid.setTextureSize(256, 256);
        jar1fluid.addBox(-1F, -5F, -1F, 2, 10, 2);
        jar1fluid.setRotationPoint(-5.388228F, 26.89778F, -9.332685F);
        jar2 = new ModelRenderer(this, 13, 13);
        jar2.setTextureSize(256, 256);
        jar2.addBox(-2.5F, -6.5F, -1.5F, 1, 1, 3);
        jar2.setRotationPoint(-5F, 24F, 8.660254F);
        jar2m1 = new ModelRenderer(this, 12, 12);
        jar2m1.setTextureSize(256, 256);
        jar2m1.addBox(-0.5F, -7F, -2F, 1, 14, 4);
        jar2m1.setRotationPoint(-5.853854F, 24.5777F, 10.13917F);
        jar2m2 = new ModelRenderer(this, 15, 15);
        jar2m2.setTextureSize(256, 256);
        jar2m2.addBox(-2F, -7F, -0.5F, 4, 14, 1);
        jar2m2.setRotationPoint(-3.830371F, 24.96593F, 9.634398F);
        jar2m3 = new ModelRenderer(this, 15, 15);
        jar2m3.setTextureSize(256, 256);
        jar2m3.addBox(-2F, -7F, -0.5F, 4, 14, 1);
        jar2m3.setRotationPoint(-6.428447F, 24.96593F, 8.134398F);
        jar2m4 = new ModelRenderer(this, 12, 12);
        jar2m4.setTextureSize(256, 256);
        jar2m4.addBox(-0.5F, -7F, -2F, 1, 14, 4);
        jar2m4.setRotationPoint(-4.404965F, 25.35415F, 7.629623F);
        jar2m5 = new ModelRenderer(this, 15, 15);
        jar2m5.setTextureSize(256, 256);
        jar2m5.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar2m5.setRotationPoint(-2.491492F, 18.20444F, 8.315391F);
        jar2m6 = new ModelRenderer(this, 15, 15);
        jar2m6.setTextureSize(256, 256);
        jar2m6.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar2m6.setRotationPoint(-5.955593F, 18.20444F, 6.315391F);
        jar2m7 = new ModelRenderer(this, 13, 13);
        jar2m7.setTextureSize(256, 256);
        jar2m7.addBox(-0.5F, -0.5F, -1.5F, 1, 1, 3);
        jar2m7.setRotationPoint(-3.257617F, 18.72208F, 5.642358F);
        jar2m8 = new ModelRenderer(this, 13, 13);
        jar2m8.setTextureSize(256, 256);
        jar2m8.addBox(-1.5F, -0.5F, -1.5F, 3, 1, 3);
        jar2m8.setRotationPoint(-6.035276F, 31.72741F, 10.45341F);
        jar2fluid = new ModelRenderer(this, 19, 140);
        jar2fluid.setTextureSize(256, 256);
        jar2fluid.addBox(-1F, -5F, -1F, 2, 10, 2);
        jar2fluid.setRotationPoint(-5.388228F, 26.89778F, 9.332685F);
        jar3 = new ModelRenderer(this, 13, 13);
        jar3.setTextureSize(256, 256);
        jar3.addBox(-2.5F, -6.5F, -1.5F, 1, 1, 3);
        jar3.setRotationPoint(-10F, 24F, 0F);
        jar3m1 = new ModelRenderer(this, 12, 12);
        jar3m1.setTextureSize(256, 256);
        jar3m1.addBox(-0.5F, -7F, -2F, 1, 14, 4);
        jar3m1.setRotationPoint(-11.70771F, 24.5777F, 0F);
        jar3m2 = new ModelRenderer(this, 15, 15);
        jar3m2.setTextureSize(256, 256);
        jar3m2.addBox(-2F, -7F, -0.5F, 4, 14, 1);
        jar3m2.setRotationPoint(-10.25882F, 24.96593F, 1.5F);
        jar3m3 = new ModelRenderer(this, 15, 15);
        jar3m3.setTextureSize(256, 256);
        jar3m3.addBox(-2F, -7F, -0.5F, 4, 14, 1);
        jar3m3.setRotationPoint(-10.25882F, 24.96593F, -1.5F);
        jar3m4 = new ModelRenderer(this, 12, 12);
        jar3m4.setTextureSize(256, 256);
        jar3m4.addBox(-0.5F, -7F, -2F, 1, 14, 4);
        jar3m4.setRotationPoint(-8.80993F, 25.35415F, 0F);
        jar3m5 = new ModelRenderer(this, 15, 15);
        jar3m5.setTextureSize(256, 256);
        jar3m5.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar3m5.setRotationPoint(-8.447085F, 18.20444F, 2F);
        jar3m6 = new ModelRenderer(this, 15, 15);
        jar3m6.setTextureSize(256, 256);
        jar3m6.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar3m6.setRotationPoint(-8.447085F, 18.20444F, -2F);
        jar3m7 = new ModelRenderer(this, 13, 13);
        jar3m7.setTextureSize(256, 256);
        jar3m7.addBox(-0.5F, -0.5F, -1.5F, 1, 1, 3);
        jar3m7.setRotationPoint(-6.515234F, 18.72208F, 0F);
        jar3m8 = new ModelRenderer(this, 13, 13);
        jar3m8.setTextureSize(256, 256);
        jar3m8.addBox(-1.5F, -0.5F, -1.5F, 3, 1, 3);
        jar3m8.setRotationPoint(-12.07055F, 31.72741F, 0F);
        jar3fluid = new ModelRenderer(this, 29, 140);
        jar3fluid.setTextureSize(256, 256);
        jar3fluid.addBox(-1F, -5F, -1F, 2, 10, 2);
        jar3fluid.setRotationPoint(-10.77646F, 26.89778F, 0F);
        jar4 = new ModelRenderer(this, 13, 13);
        jar4.setTextureSize(256, 256);
        jar4.addBox(-2.5F, -6.5F, -1.5F, 1, 1, 3);
        jar4.setRotationPoint(9.999999F, 24F, -2.341392E-07F);
        jar4m1 = new ModelRenderer(this, 12, 12);
        jar4m1.setTextureSize(256, 256);
        jar4m1.addBox(-0.5F, -7F, -2F, 1, 14, 4);
        jar4m1.setRotationPoint(11.70771F, 24.5777F, -3.932577E-07F);
        jar4m2 = new ModelRenderer(this, 15, 15);
        jar4m2.setTextureSize(256, 256);
        jar4m2.addBox(-2F, -7F, -0.5F, 4, 14, 1);
        jar4m2.setRotationPoint(10.25882F, 24.96593F, -1.5F);
        jar4m3 = new ModelRenderer(this, 15, 15);
        jar4m3.setTextureSize(256, 256);
        jar4m3.addBox(-2F, -7F, -0.5F, 4, 14, 1);
        jar4m3.setRotationPoint(10.25882F, 24.96593F, 1.5F);
        jar4m4 = new ModelRenderer(this, 12, 12);
        jar4m4.setTextureSize(256, 256);
        jar4m4.addBox(-0.5F, -7F, -2F, 1, 14, 4);
        jar4m4.setRotationPoint(8.80993F, 25.35415F, -1.355271E-07F);
        jar4m5 = new ModelRenderer(this, 15, 15);
        jar4m5.setTextureSize(256, 256);
        jar4m5.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar4m5.setRotationPoint(8.447084F, 18.20444F, -2F);
        jar4m6 = new ModelRenderer(this, 15, 15);
        jar4m6.setTextureSize(256, 256);
        jar4m6.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar4m6.setRotationPoint(8.447085F, 18.20444F, 2F);
        jar4m7 = new ModelRenderer(this, 13, 13);
        jar4m7.setTextureSize(256, 256);
        jar4m7.addBox(-0.5F, -0.5F, -1.5F, 1, 1, 3);
        jar4m7.setRotationPoint(6.515233F, 18.72208F, 1.192007E-07F);
        jar4m8 = new ModelRenderer(this, 13, 13);
        jar4m8.setTextureSize(256, 256);
        jar4m8.addBox(-1.5F, -0.5F, -1.5F, 3, 1, 3);
        jar4m8.setRotationPoint(12.07055F, 31.72741F, -4.761651E-07F);
        jar4liquid = new ModelRenderer(this, 39, 140);
        jar4liquid.setTextureSize(256, 256);
        jar4liquid.addBox(-1F, -5F, -1F, 2, 10, 2);
        jar4liquid.setRotationPoint(10.77646F, 26.89778F, -3.248989E-07F);
        jar5 = new ModelRenderer(this, 13, 13);
        jar5.setTextureSize(256, 256);
        jar5.addBox(-2.5F, -6.5F, -1.5F, 1, 1, 3);
        jar5.setRotationPoint(5.000001F, 24F, 8.660254F);
        jar5m1 = new ModelRenderer(this, 12, 12);
        jar5m1.setTextureSize(256, 256);
        jar5m1.addBox(-0.5F, -7F, -2F, 1, 14, 4);
        jar5m1.setRotationPoint(5.853855F, 24.5777F, 10.13917F);
        jar5m2 = new ModelRenderer(this, 15, 15);
        jar5m2.setTextureSize(256, 256);
        jar5m2.addBox(-2F, -7F, -0.5F, 4, 14, 1);
        jar5m2.setRotationPoint(6.428449F, 24.96593F, 8.134397F);
        jar5m3 = new ModelRenderer(this, 15, 15);
        jar5m3.setTextureSize(256, 256);
        jar5m3.addBox(-2F, -7F, -0.5F, 4, 14, 1);
        jar5m3.setRotationPoint(3.830373F, 24.96593F, 9.634398F);
        jar5m4 = new ModelRenderer(this, 12, 12);
        jar5m4.setTextureSize(256, 256);
        jar5m4.addBox(-0.5F, -7F, -2F, 1, 14, 4);
        jar5m4.setRotationPoint(4.404966F, 25.35415F, 7.629623F);
        jar5m5 = new ModelRenderer(this, 15, 15);
        jar5m5.setTextureSize(256, 256);
        jar5m5.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar5m5.setRotationPoint(5.955595F, 18.20444F, 6.31539F);
        jar5m6 = new ModelRenderer(this, 15, 15);
        jar5m6.setTextureSize(256, 256);
        jar5m6.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar5m6.setRotationPoint(2.491493F, 18.20444F, 8.315391F);
        jar5m7 = new ModelRenderer(this, 13, 13);
        jar5m7.setTextureSize(256, 256);
        jar5m7.addBox(-0.5F, -0.5F, -1.5F, 1, 1, 3);
        jar5m7.setRotationPoint(3.257617F, 18.72208F, 5.642357F);
        jar5m8 = new ModelRenderer(this, 13, 13);
        jar5m8.setTextureSize(256, 256);
        jar5m8.addBox(-1.5F, -0.5F, -1.5F, 3, 1, 3);
        jar5m8.setRotationPoint(6.035278F, 31.72741F, 10.4534F);
        jar5liquid = new ModelRenderer(this, 49, 140);
        jar5liquid.setTextureSize(256, 256);
        jar5liquid.addBox(-1F, -5F, -1F, 2, 10, 2);
        jar5liquid.setRotationPoint(5.38823F, 26.89778F, 9.332685F);
        jar6 = new ModelRenderer(this, 13, 13);
        jar6.setTextureSize(256, 256);
        jar6.addBox(-2.5F, -6.5F, -1.5F, 1, 1, 3);
        jar6.setRotationPoint(5F, 24F, -8.660253F);
        jar6m1 = new ModelRenderer(this, 12, 12);
        jar6m1.setTextureSize(256, 256);
        jar6m1.addBox(-0.5F, -7F, -2F, 1, 14, 4);
        jar6m1.setRotationPoint(5.853855F, 24.5777F, -10.13917F);
        jar6m2 = new ModelRenderer(this, 15, 15);
        jar6m2.setTextureSize(256, 256);
        jar6m2.addBox(-2F, -7F, -0.5F, 4, 14, 1);
        jar6m2.setRotationPoint(3.830372F, 24.96593F, -9.634397F);
        jar6m3 = new ModelRenderer(this, 15, 15);
        jar6m3.setTextureSize(256, 256);
        jar6m3.addBox(-2F, -7F, -0.5F, 4, 14, 1);
        jar6m3.setRotationPoint(6.428448F, 24.96593F, -8.134396F);
        jar6m4 = new ModelRenderer(this, 12, 12);
        jar6m4.setTextureSize(256, 256);
        jar6m4.addBox(-0.5F, -7F, -2F, 1, 14, 4);
        jar6m4.setRotationPoint(4.404965F, 25.35415F, -7.629622F);
        jar6m5 = new ModelRenderer(this, 15, 15);
        jar6m5.setTextureSize(256, 256);
        jar6m5.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar6m5.setRotationPoint(2.491493F, 18.20444F, -8.31539F);
        jar6m6 = new ModelRenderer(this, 15, 15);
        jar6m6.setTextureSize(256, 256);
        jar6m6.addBox(-2.5F, -0.5F, -0.5F, 5, 1, 1);
        jar6m6.setRotationPoint(5.955594F, 18.20444F, -6.315389F);
        jar6m7 = new ModelRenderer(this, 13, 13);
        jar6m7.setTextureSize(256, 256);
        jar6m7.addBox(-0.5F, -0.5F, -1.5F, 1, 1, 3);
        jar6m7.setRotationPoint(3.257617F, 18.72208F, -5.642357F);
        jar6m8 = new ModelRenderer(this, 13, 13);
        jar6m8.setTextureSize(256, 256);
        jar6m8.addBox(-1.5F, -0.5F, -1.5F, 3, 1, 3);
        jar6m8.setRotationPoint(6.035277F, 31.72741F, -10.4534F);
        jar6liquid = new ModelRenderer(this, 59, 140);
        jar6liquid.setTextureSize(256, 256);
        jar6liquid.addBox(-1F, -5F, -1F, 2, 10, 2);
        jar6liquid.setRotationPoint(5.388229F, 26.89778F, -9.332684F);
        bowl1 = new ModelRenderer(this, 146, 108);
        bowl1.setTextureSize(256, 256);
        bowl1.addBox(-15F, -7.5F, -0.5F, 30, 15, 1);
        bowl1.setRotationPoint(0F, 29F, 14.5F);
        bowl2 = new ModelRenderer(this, 146, 108);
        bowl2.setTextureSize(256, 256);
        bowl2.addBox(-15F, -7.5F, -0.5F, 30, 15, 1);
        bowl2.setRotationPoint(0F, 29F, -14.5F);
        bowl3 = new ModelRenderer(this, 148, 81);
        bowl3.setTextureSize(256, 256);
        bowl3.addBox(-0.5F, -7.5F, -14F, 1, 15, 28);
        bowl3.setRotationPoint(14.5F, 29F, 0F);
        bowl4 = new ModelRenderer(this, 148, 81);
        bowl4.setTextureSize(256, 256);
        bowl4.addBox(-0.5F, -7.5F, -14F, 1, 15, 28);
        bowl4.setRotationPoint(-14.5F, 29F, 0F);
        bowl5 = new ModelRenderer(this, 8, 33);
        bowl5.setTextureSize(256, 256);
        bowl5.addBox(-15F, -0.5F, -15F, 30, 1, 30);
        bowl5.setRotationPoint(0F, 37F, 0F);
        shell1 = new ModelRenderer(this, 139, 33);
        shell1.setTextureSize(256, 256);
        shell1.addBox(-15F, -8F, -3.5F, 30, 16, 7);
        shell1.setRotationPoint(0F, 29.5F, -18.5F);
        shell2 = new ModelRenderer(this, 141, 7);
        shell2.setTextureSize(256, 256);
        shell2.addBox(-15F, -8F, -2F, 30, 16, 4);
        shell2.setRotationPoint(0F, 29.5F, 17F);
        shell3 = new ModelRenderer(this, 23, 67);
        shell3.setTextureSize(256, 256);
        shell3.addBox(-2F, -8F, -20.5F, 4, 16, 41);
        shell3.setRotationPoint(-17F, 29.5F, -1.5F);
        shell4 = new ModelRenderer(this, 23, 67);
        shell4.setTextureSize(256, 256);
        shell4.addBox(-2F, -8F, -20.5F, 4, 16, 41);
        shell4.setRotationPoint(17F, 29.5F, -1.5F);
        button1 = new ModelRenderer(this, 17, 75);
        button1.setTextureSize(256, 256);
        button1.addBox(-2F, -3F, -0.5F, 4, 6, 1);
        button1.setRotationPoint(-12F, 29F, -22F);
        button2 = new ModelRenderer(this, 32, 74);
        button2.setTextureSize(256, 256);
        button2.addBox(-1.5F, -1.5F, -0.5F, 3, 3, 1);
        button2.setRotationPoint(-12F, 28F, -22.5F);
        button3 = new ModelRenderer(this, 32, 74);
        button3.setTextureSize(256, 256);
        button3.addBox(-1.5F, -1.5F, -0.5F, 3, 3, 1);
        button3.setRotationPoint(-12F, 30F, -22.5F);
        button4 = new ModelRenderer(this, 16, 93);
        button4.setTextureSize(256, 256);
        button4.addBox(-11.5F, -4F, -0.5F, 23, 8, 1);
        button4.setRotationPoint(3F, 29F, -22F);
        lid1 = new ModelRenderer(this, 90, 50);
        lid1.setTextureSize(256, 256);
        lid1.addBox(-1F, -5F, -20.5F, 2, 10, 41);
        lid1.setRotationPoint(18F, 16.5F, -1.5F);
        lid2 = new ModelRenderer(this, 90, 50);
        lid2.setTextureSize(256, 256);
        lid2.addBox(-1F, -5F, -20.5F, 2, 10, 41);
        lid2.setRotationPoint(-18F, 16.5F, -1.5F);
        lid3 = new ModelRenderer(this, 97, 57);
        lid3.setTextureSize(256, 256);
        lid3.addBox(-1F, -5F, -17F, 2, 10, 34);
        lid3.setRotationPoint(0F, 16.5F, 18F);
        lid4 = new ModelRenderer(this, 97, 57);
        lid4.setTextureSize(256, 256);
        lid4.addBox(-1F, -5F, -17F, 2, 10, 34);
        lid4.setRotationPoint(0F, 16.5F, -21F);
        lid5 = new ModelRenderer(this, 53, 139);
        lid5.setTextureSize(256, 256);
        lid5.addBox(-18.5F, -1F, -17F, 37, 2, 34);
        lid5.setRotationPoint(0F, 12.5F, -1.5F);
        bisagra1 = new ModelRenderer(this, 93, 76);
        bisagra1.setTextureSize(256, 256);
        bisagra1.addBox(-0.5F, -1.5F, -1.5F, 1, 3, 3);
        bisagra1.setRotationPoint(14F, 23F, 19.5F);
        bisagra2 = new ModelRenderer(this, 93, 76);
        bisagra2.setTextureSize(256, 256);
        bisagra2.addBox(-0.5F, -1.5F, -1.5F, 1, 3, 3);
        bisagra2.setRotationPoint(-14F, 23F, 19.5F);
        bisagra3 = new ModelRenderer(this, 93, 68);
        bisagra3.setTextureSize(256, 256);
        bisagra3.addBox(-0.5F, -1.5F, -1.5F, 1, 3, 3);
        bisagra3.setRotationPoint(-14F, 20F, 19.5F);
        bisagra4 = new ModelRenderer(this, 93, 68);
        bisagra4.setTextureSize(256, 256);
        bisagra4.addBox(-0.5F, -1.5F, -1.5F, 1, 3, 3);
        bisagra4.setRotationPoint(14F, 20F, 19.5F);
        bottomness = new ModelRenderer(this, 56, 181);
        bottomness.setTextureSize(256, 256);
        bottomness.addBox(-15.5F, -2F, -17.5F, 31, 4, 35);
        bottomness.setRotationPoint(0F, 39F, -1.5F);

    }

    @Override
    public void render(float rotation)
    {

        center.rotateAngleX = 0F;
        center.rotateAngleY = 0F;
        center.rotateAngleZ = 0F;
        center.renderWithRotation(rotation);

        screw1.rotateAngleX = 0F;
        screw1.rotateAngleY = -3.141593F;
        screw1.rotateAngleZ = 0F;
        screw1.renderWithRotation(rotation);

        holder1.rotateAngleX = 6.987182E-09F;
        holder1.rotateAngleY = -3.141593F;
        holder1.rotateAngleZ = 0.2617995F;
        holder1.renderWithRotation(rotation);

        holder2.rotateAngleX = 1.111629E-08F;
        holder2.rotateAngleY = 2.094395F;
        holder2.rotateAngleZ = 0.2617995F;
        holder2.renderWithRotation(rotation);

        holder3.rotateAngleX = 1.934149E-09F;
        holder3.rotateAngleY = 1.047197F;
        holder3.rotateAngleZ = 0.2617994F;
        holder3.renderWithRotation(rotation);

        holder4.rotateAngleX = -1.154115E-08F;
        holder4.rotateAngleY = -2.094395F;
        holder4.rotateAngleZ = 0.2617994F;
        holder4.renderWithRotation(rotation);

        holder5.rotateAngleX = 1.111629E-08F;
        holder5.rotateAngleY = -1.047197F;
        holder5.rotateAngleZ = 0.2617995F;
        holder5.renderWithRotation(rotation);

        holder6.rotateAngleX = 1.438055E-08F;
        holder6.rotateAngleY = 9.781585E-08F;
        holder6.rotateAngleZ = 0.2617995F;
        holder6.renderWithRotation(rotation);

        jar1.rotateAngleX = 1.785394E-08F;
        jar1.rotateAngleY = -1.047197F;
        jar1.rotateAngleZ = 0.2617995F;
        jar1.renderWithRotation(rotation);

        jar1m1.rotateAngleX = 1.785394E-08F;
        jar1m1.rotateAngleY = -1.047197F;
        jar1m1.rotateAngleZ = 0.2617995F;
        jar1m1.renderWithRotation(rotation);

        jar1m2.rotateAngleX = 1.785394E-08F;
        jar1m2.rotateAngleY = -1.047197F;
        jar1m2.rotateAngleZ = 0.2617995F;
        jar1m2.renderWithRotation(rotation);

        jar1m3.rotateAngleX = 1.785394E-08F;
        jar1m3.rotateAngleY = -1.047197F;
        jar1m3.rotateAngleZ = 0.2617995F;
        jar1m3.renderWithRotation(rotation);

        jar1m4.rotateAngleX = 1.785394E-08F;
        jar1m4.rotateAngleY = -1.047197F;
        jar1m4.rotateAngleZ = 0.2617995F;
        jar1m4.renderWithRotation(rotation);

        jar1m5.rotateAngleX = 1.785394E-08F;
        jar1m5.rotateAngleY = -1.047197F;
        jar1m5.rotateAngleZ = 0.2617995F;
        jar1m5.renderWithRotation(rotation);

        jar1m6.rotateAngleX = 1.785394E-08F;
        jar1m6.rotateAngleY = -1.047197F;
        jar1m6.rotateAngleZ = 0.2617995F;
        jar1m6.renderWithRotation(rotation);

        jar1m7.rotateAngleX = 1.785394E-08F;
        jar1m7.rotateAngleY = -1.047197F;
        jar1m7.rotateAngleZ = 0.2617995F;
        jar1m7.renderWithRotation(rotation);

        jar1m8.rotateAngleX = 1.785394E-08F;
        jar1m8.rotateAngleY = -1.047197F;
        jar1m8.rotateAngleZ = 0.2617995F;
        jar1m8.renderWithRotation(rotation);

        jar1fluid.rotateAngleX = 3.064833E-08F;
        jar1fluid.rotateAngleY = -1.047197F;
        jar1fluid.rotateAngleZ = 0.2617995F;
        jar1fluid.renderWithRotation(rotation);

        jar2.rotateAngleX = 1.169341E-08F;
        jar2.rotateAngleY = 1.047197F;
        jar2.rotateAngleZ = 0.2617994F;
        jar2.renderWithRotation(rotation);

        jar2m1.rotateAngleX = 1.169341E-08F;
        jar2m1.rotateAngleY = 1.047197F;
        jar2m1.rotateAngleZ = 0.2617994F;
        jar2m1.renderWithRotation(rotation);

        jar2m2.rotateAngleX = 1.169341E-08F;
        jar2m2.rotateAngleY = 1.047197F;
        jar2m2.rotateAngleZ = 0.2617994F;
        jar2m2.renderWithRotation(rotation);

        jar2m3.rotateAngleX = 1.169341E-08F;
        jar2m3.rotateAngleY = 1.047197F;
        jar2m3.rotateAngleZ = 0.2617994F;
        jar2m3.renderWithRotation(rotation);

        jar2m4.rotateAngleX = 1.169341E-08F;
        jar2m4.rotateAngleY = 1.047197F;
        jar2m4.rotateAngleZ = 0.2617994F;
        jar2m4.renderWithRotation(rotation);

        jar2m5.rotateAngleX = 1.169341E-08F;
        jar2m5.rotateAngleY = 1.047197F;
        jar2m5.rotateAngleZ = 0.2617994F;
        jar2m5.renderWithRotation(rotation);

        jar2m6.rotateAngleX = 1.169341E-08F;
        jar2m6.rotateAngleY = 1.047197F;
        jar2m6.rotateAngleZ = 0.2617994F;
        jar2m6.renderWithRotation(rotation);

        jar2m7.rotateAngleX = 1.169341E-08F;
        jar2m7.rotateAngleY = 1.047197F;
        jar2m7.rotateAngleZ = 0.2617994F;
        jar2m7.renderWithRotation(rotation);

        jar2m8.rotateAngleX = 1.169341E-08F;
        jar2m8.rotateAngleY = 1.047197F;
        jar2m8.rotateAngleZ = 0.2617994F;
        jar2m8.renderWithRotation(rotation);

        jar2fluid.rotateAngleX = -1.100968E-09F;
        jar2fluid.rotateAngleY = 1.047197F;
        jar2fluid.rotateAngleZ = 0.2617994F;
        jar2fluid.renderWithRotation(rotation);

        jar3.rotateAngleX = 0F;
        jar3.rotateAngleY = 0F;
        jar3.rotateAngleZ = 0.2617994F;
        jar3.renderWithRotation(rotation);

        jar3m1.rotateAngleX = 0F;
        jar3m1.rotateAngleY = 0F;
        jar3m1.rotateAngleZ = 0.2617994F;
        jar3m1.renderWithRotation(rotation);

        jar3m2.rotateAngleX = 0F;
        jar3m2.rotateAngleY = 0F;
        jar3m2.rotateAngleZ = 0.2617994F;
        jar3m2.renderWithRotation(rotation);

        jar3m3.rotateAngleX = 0F;
        jar3m3.rotateAngleY = 0F;
        jar3m3.rotateAngleZ = 0.2617994F;
        jar3m3.renderWithRotation(rotation);

        jar3m4.rotateAngleX = 0F;
        jar3m4.rotateAngleY = 0F;
        jar3m4.rotateAngleZ = 0.2617994F;
        jar3m4.renderWithRotation(rotation);

        jar3m5.rotateAngleX = 0F;
        jar3m5.rotateAngleY = 0F;
        jar3m5.rotateAngleZ = 0.2617994F;
        jar3m5.renderWithRotation(rotation);

        jar3m6.rotateAngleX = 0F;
        jar3m6.rotateAngleY = 0F;
        jar3m6.rotateAngleZ = 0.2617994F;
        jar3m6.renderWithRotation(rotation);

        jar3m7.rotateAngleX = 0F;
        jar3m7.rotateAngleY = 0F;
        jar3m7.rotateAngleZ = 0.2617994F;
        jar3m7.renderWithRotation(rotation);

        jar3m8.rotateAngleX = 0F;
        jar3m8.rotateAngleY = 0F;
        jar3m8.rotateAngleZ = 0.2617994F;
        jar3m8.renderWithRotation(rotation);

        jar3fluid.rotateAngleX = 0F;
        jar3fluid.rotateAngleY = 0F;
        jar3fluid.rotateAngleZ = 0.2617994F;
        jar3fluid.renderWithRotation(rotation);

        jar4.rotateAngleX = 6.987181E-09F;
        jar4.rotateAngleY = -3.141593F;
        jar4.rotateAngleZ = 0.2617995F;
        jar4.renderWithRotation(rotation);

        jar4m1.rotateAngleX = 6.987181E-09F;
        jar4m1.rotateAngleY = -3.141593F;
        jar4m1.rotateAngleZ = 0.2617995F;
        jar4m1.renderWithRotation(rotation);

        jar4m2.rotateAngleX = 6.987181E-09F;
        jar4m2.rotateAngleY = -3.141593F;
        jar4m2.rotateAngleZ = 0.2617995F;
        jar4m2.renderWithRotation(rotation);

        jar4m3.rotateAngleX = 6.987181E-09F;
        jar4m3.rotateAngleY = -3.141593F;
        jar4m3.rotateAngleZ = 0.2617995F;
        jar4m3.renderWithRotation(rotation);

        jar4m4.rotateAngleX = 6.987181E-09F;
        jar4m4.rotateAngleY = -3.141593F;
        jar4m4.rotateAngleZ = 0.2617995F;
        jar4m4.renderWithRotation(rotation);

        jar4m5.rotateAngleX = 6.987181E-09F;
        jar4m5.rotateAngleY = -3.141593F;
        jar4m5.rotateAngleZ = 0.2617995F;
        jar4m5.renderWithRotation(rotation);

        jar4m6.rotateAngleX = 6.987181E-09F;
        jar4m6.rotateAngleY = -3.141593F;
        jar4m6.rotateAngleZ = 0.2617995F;
        jar4m6.renderWithRotation(rotation);

        jar4m7.rotateAngleX = 6.987181E-09F;
        jar4m7.rotateAngleY = -3.141593F;
        jar4m7.rotateAngleZ = 0.2617995F;
        jar4m7.renderWithRotation(rotation);

        jar4m8.rotateAngleX = 6.987181E-09F;
        jar4m8.rotateAngleY = -3.141593F;
        jar4m8.rotateAngleZ = 0.2617995F;
        jar4m8.renderWithRotation(rotation);

        jar4liquid.rotateAngleX = 6.987182E-09F;
        jar4liquid.rotateAngleY = -3.141593F;
        jar4liquid.rotateAngleZ = 0.2617994F;
        jar4liquid.renderWithRotation(rotation);

        jar5.rotateAngleX = -9.064928E-09F;
        jar5.rotateAngleY = 2.094395F;
        jar5.rotateAngleZ = 0.2617995F;
        jar5.renderWithRotation(rotation);

        jar5m1.rotateAngleX = -9.064928E-09F;
        jar5m1.rotateAngleY = 2.094395F;
        jar5m1.rotateAngleZ = 0.2617995F;
        jar5m1.renderWithRotation(rotation);

        jar5m2.rotateAngleX = -9.064928E-09F;
        jar5m2.rotateAngleY = 2.094395F;
        jar5m2.rotateAngleZ = 0.2617995F;
        jar5m2.renderWithRotation(rotation);

        jar5m3.rotateAngleX = -9.064928E-09F;
        jar5m3.rotateAngleY = 2.094395F;
        jar5m3.rotateAngleZ = 0.2617995F;
        jar5m3.renderWithRotation(rotation);

        jar5m4.rotateAngleX = -9.064928E-09F;
        jar5m4.rotateAngleY = 2.094395F;
        jar5m4.rotateAngleZ = 0.2617995F;
        jar5m4.renderWithRotation(rotation);

        jar5m5.rotateAngleX = -9.064928E-09F;
        jar5m5.rotateAngleY = 2.094395F;
        jar5m5.rotateAngleZ = 0.2617995F;
        jar5m5.renderWithRotation(rotation);

        jar5m6.rotateAngleX = -9.064928E-09F;
        jar5m6.rotateAngleY = 2.094395F;
        jar5m6.rotateAngleZ = 0.2617995F;
        jar5m6.renderWithRotation(rotation);

        jar5m7.rotateAngleX = -9.064928E-09F;
        jar5m7.rotateAngleY = 2.094395F;
        jar5m7.rotateAngleZ = 0.2617995F;
        jar5m7.renderWithRotation(rotation);

        jar5m8.rotateAngleX = -9.064928E-09F;
        jar5m8.rotateAngleY = 2.094395F;
        jar5m8.rotateAngleZ = 0.2617995F;
        jar5m8.renderWithRotation(rotation);

        jar5liquid.rotateAngleX = -9.064928E-09F;
        jar5liquid.rotateAngleY = 2.094395F;
        jar5liquid.rotateAngleZ = 0.2617995F;
        jar5liquid.renderWithRotation(rotation);

        jar6.rotateAngleX = 6.187857E-08F;
        jar6.rotateAngleY = -2.094395F;
        jar6.rotateAngleZ = 0.2617994F;
        jar6.renderWithRotation(rotation);

        jar6m1.rotateAngleX = 6.187857E-08F;
        jar6m1.rotateAngleY = -2.094395F;
        jar6m1.rotateAngleZ = 0.2617994F;
        jar6m1.renderWithRotation(rotation);

        jar6m2.rotateAngleX = 6.187857E-08F;
        jar6m2.rotateAngleY = -2.094395F;
        jar6m2.rotateAngleZ = 0.2617994F;
        jar6m2.renderWithRotation(rotation);

        jar6m3.rotateAngleX = 6.187857E-08F;
        jar6m3.rotateAngleY = -2.094395F;
        jar6m3.rotateAngleZ = 0.2617994F;
        jar6m3.renderWithRotation(rotation);

        jar6m4.rotateAngleX = 6.187857E-08F;
        jar6m4.rotateAngleY = -2.094395F;
        jar6m4.rotateAngleZ = 0.2617994F;
        jar6m4.renderWithRotation(rotation);

        jar6m5.rotateAngleX = 6.187857E-08F;
        jar6m5.rotateAngleY = -2.094395F;
        jar6m5.rotateAngleZ = 0.2617994F;
        jar6m5.renderWithRotation(rotation);

        jar6m6.rotateAngleX = 6.187857E-08F;
        jar6m6.rotateAngleY = -2.094395F;
        jar6m6.rotateAngleZ = 0.2617994F;
        jar6m6.renderWithRotation(rotation);

        jar6m7.rotateAngleX = 6.187857E-08F;
        jar6m7.rotateAngleY = -2.094395F;
        jar6m7.rotateAngleZ = 0.2617994F;
        jar6m7.renderWithRotation(rotation);

        jar6m8.rotateAngleX = 6.187857E-08F;
        jar6m8.rotateAngleY = -2.094395F;
        jar6m8.rotateAngleZ = 0.2617994F;
        jar6m8.renderWithRotation(rotation);

        jar6liquid.rotateAngleX = 7.467295E-08F;
        jar6liquid.rotateAngleY = -2.094395F;
        jar6liquid.rotateAngleZ = 0.2617994F;
        jar6liquid.renderWithRotation(rotation);

        bowl1.rotateAngleX = 0F;
        bowl1.rotateAngleY = 0F;
        bowl1.rotateAngleZ = 0F;
        bowl1.renderWithRotation(rotation);

        bowl2.rotateAngleX = 0F;
        bowl2.rotateAngleY = 0F;
        bowl2.rotateAngleZ = 0F;
        bowl2.renderWithRotation(rotation);

        bowl3.rotateAngleX = 0F;
        bowl3.rotateAngleY = 0F;
        bowl3.rotateAngleZ = 0F;
        bowl3.renderWithRotation(rotation);

        bowl4.rotateAngleX = 0F;
        bowl4.rotateAngleY = 0F;
        bowl4.rotateAngleZ = 0F;
        bowl4.renderWithRotation(rotation);

        bowl5.rotateAngleX = 0F;
        bowl5.rotateAngleY = 0F;
        bowl5.rotateAngleZ = 0F;
        bowl5.renderWithRotation(rotation);

        shell1.rotateAngleX = 0F;
        shell1.rotateAngleY = 0F;
        shell1.rotateAngleZ = 0F;
        shell1.renderWithRotation(rotation);

        shell2.rotateAngleX = 0F;
        shell2.rotateAngleY = 0F;
        shell2.rotateAngleZ = 0F;
        shell2.renderWithRotation(rotation);

        shell3.rotateAngleX = 0F;
        shell3.rotateAngleY = 0F;
        shell3.rotateAngleZ = 0F;
        shell3.renderWithRotation(rotation);

        shell4.rotateAngleX = 0F;
        shell4.rotateAngleY = 0F;
        shell4.rotateAngleZ = 0F;
        shell4.renderWithRotation(rotation);

        button1.rotateAngleX = 0F;
        button1.rotateAngleY = 0F;
        button1.rotateAngleZ = 0F;
        button1.renderWithRotation(rotation);

        button2.rotateAngleX = 0.1188179F;
        button2.rotateAngleY = 0F;
        button2.rotateAngleZ = 0F;
        button2.renderWithRotation(rotation);

        button3.rotateAngleX = -0.1047198F;
        button3.rotateAngleY = 0F;
        button3.rotateAngleZ = 0F;
        button3.renderWithRotation(rotation);

        button4.rotateAngleX = 0F;
        button4.rotateAngleY = 0F;
        button4.rotateAngleZ = 0F;
        button4.renderWithRotation(rotation);

        lid1.rotateAngleX = 0F;
        lid1.rotateAngleY = 0F;
        lid1.rotateAngleZ = 0F;
        lid1.renderWithRotation(rotation);

        lid2.rotateAngleX = 0F;
        lid2.rotateAngleY = 0F;
        lid2.rotateAngleZ = 0F;
        lid2.renderWithRotation(rotation);

        lid3.rotateAngleX = 0F;
        lid3.rotateAngleY = -1.570796F;
        lid3.rotateAngleZ = 0F;
        lid3.renderWithRotation(rotation);

        lid4.rotateAngleX = 0F;
        lid4.rotateAngleY = -1.570796F;
        lid4.rotateAngleZ = 0F;
        lid4.renderWithRotation(rotation);

        lid5.rotateAngleX = 0F;
        lid5.rotateAngleY = -1.570796F;
        lid5.rotateAngleZ = 0F;
        lid5.renderWithRotation(rotation);

        bisagra1.rotateAngleX = 0F;
        bisagra1.rotateAngleY = -1.570796F;
        bisagra1.rotateAngleZ = 0F;
        bisagra1.renderWithRotation(rotation);

        bisagra2.rotateAngleX = 0F;
        bisagra2.rotateAngleY = -1.570796F;
        bisagra2.rotateAngleZ = 0F;
        bisagra2.renderWithRotation(rotation);

        bisagra3.rotateAngleX = 0F;
        bisagra3.rotateAngleY = -1.570796F;
        bisagra3.rotateAngleZ = 0F;
        bisagra3.renderWithRotation(rotation);

        bisagra4.rotateAngleX = 0F;
        bisagra4.rotateAngleY = -1.570796F;
        bisagra4.rotateAngleZ = 0F;
        bisagra4.renderWithRotation(rotation);

        bottomness.rotateAngleX = 0F;
        bottomness.rotateAngleY = 0F;
        bottomness.rotateAngleZ = 0F;
        bottomness.renderWithRotation(rotation);

    }

}
