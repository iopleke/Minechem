
package pixlepix.minechem.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelPrinter extends ModelBase
{
  //fields
    ModelRenderer Base;
    ModelRenderer RightWall;
    ModelRenderer LeftWall;
    ModelRenderer MiddleComp;
    ModelRenderer WholeBase;
    ModelRenderer Rack;
    ModelRenderer Back;
    ModelRenderer TopLeftNobble;
    ModelRenderer TopRightNobble;
    ModelRenderer TopMiddleNobble;
    ModelRenderer LeftLine;
    ModelRenderer RightLine;
  
  public ModelPrinter()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(0F, 0F, 0F, 16, 1, 16);
      Base.setRotationPoint(-8F, 23F, -8F);
      Base.setTextureSize(128, 128);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      RightWall = new ModelRenderer(this, 0, 19);
      RightWall.addBox(0F, 0F, 0F, 1, 5, 14);
      RightWall.setRotationPoint(6F, 18F, -7F);
      RightWall.setTextureSize(128, 128);
      RightWall.mirror = true;
      setRotation(RightWall, 0F, 0F, 0F);
      LeftWall = new ModelRenderer(this, 0, 19);
      LeftWall.addBox(0F, 0F, 0F, 1, 5, 14);
      LeftWall.setRotationPoint(-7F, 18F, -7F);
      LeftWall.setTextureSize(128, 128);
      LeftWall.mirror = true;
      setRotation(LeftWall, 0F, 0F, 0F);
      MiddleComp = new ModelRenderer(this, 31, 19);
      MiddleComp.addBox(0F, 0F, 0F, 8, 4, 11);
      MiddleComp.setRotationPoint(-4F, 17.5F, -7F);
      MiddleComp.setTextureSize(128, 128);
      MiddleComp.mirror = true;
      setRotation(MiddleComp, 0F, 0F, 0F);
      WholeBase = new ModelRenderer(this, 65, 0);
      WholeBase.addBox(0F, 0F, 0F, 12, 5, 11);
      WholeBase.setRotationPoint(-6F, 17F, -6.5F);
      WholeBase.setTextureSize(128, 128);
      WholeBase.mirror = true;
      setRotation(WholeBase, 0F, 0F, 0F);
      Rack = new ModelRenderer(this, 0, 40);
      Rack.addBox(0F, -1F, 0F, 12, 8, 1);
      Rack.setRotationPoint(-6F, 13F, 7F);
      Rack.setTextureSize(128, 128);
      Rack.mirror = true;
      setRotation(Rack, -0.3346075F, 0F, 0F);
      Back = new ModelRenderer(this, 0, 50);
      Back.addBox(0F, 0F, 0F, 12, 4, 1);
      Back.setRotationPoint(-6F, 19F, 5.8F);
      Back.setTextureSize(128, 128);
      Back.mirror = true;
      setRotation(Back, 0F, 0F, 0F);
      TopLeftNobble = new ModelRenderer(this, 0, 58);
      TopLeftNobble.addBox(0F, 0F, 0F, 3, 1, 3);
      TopLeftNobble.setRotationPoint(-5F, 16F, 0F);
      TopLeftNobble.setTextureSize(128, 128);
      TopLeftNobble.mirror = true;
      setRotation(TopLeftNobble, 0F, 0F, 0F);
      TopRightNobble = new ModelRenderer(this, 0, 58);
      TopRightNobble.addBox(0F, 0F, 0F, 3, 1, 3);
      TopRightNobble.setRotationPoint(2F, 16F, 0F);
      TopRightNobble.setTextureSize(128, 128);
      TopRightNobble.mirror = true;
      setRotation(TopRightNobble, 0F, 0F, 0F);
      TopMiddleNobble = new ModelRenderer(this, 13, 58);
      TopMiddleNobble.addBox(0F, 0F, 0F, 4, 1, 6);
      TopMiddleNobble.setRotationPoint(-2F, 16.5F, -3F);
      TopMiddleNobble.setTextureSize(128, 128);
      TopMiddleNobble.mirror = true;
      setRotation(TopMiddleNobble, 0F, 0F, 0F);
      LeftLine = new ModelRenderer(this, 28, 42);
      LeftLine.addBox(0F, 0F, 0F, 1, 1, 9);
      LeftLine.setRotationPoint(-4F, 16.5F, -7F);
      LeftLine.setTextureSize(128, 128);
      LeftLine.mirror = true;
      setRotation(LeftLine, 0F, 0F, 0F);
      RightLine = new ModelRenderer(this, 28, 42);
      RightLine.addBox(0F, 0F, 0F, 1, 1, 9);
      RightLine.setRotationPoint(3F, 16.5F, -7F);
      RightLine.setTextureSize(128, 128);
      RightLine.mirror = true;
      setRotation(RightLine, 0F, 0F, 0F);
  }
  
  public void render(float f5)
  {
    Base.render(f5);
    RightWall.render(f5);
    LeftWall.render(f5);
    MiddleComp.render(f5);
    WholeBase.render(f5);
    Rack.render(f5);
    Back.render(f5);
    TopLeftNobble.render(f5);
    TopRightNobble.render(f5);
    TopMiddleNobble.render(f5);
    LeftLine.render(f5);
    RightLine.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
  }
  

}
