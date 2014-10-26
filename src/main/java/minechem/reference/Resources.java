package minechem.reference;

import net.minecraft.util.ResourceLocation;

public class Resources
{
    public static final ResourceLocation SPRITES =new ResourceLocation(Reference.ID, Textures.Gui.SPRITES);

    public static final class Icon
    {
        public static final ResourceLocation ENERGY = new ResourceLocation(Reference.ID, Textures.Icon.POWER);
        public static final ResourceLocation FULL_ENERGY = new ResourceLocation(Reference.ID, Textures.Icon.FULL_POWER);
        public static final ResourceLocation HELP = new ResourceLocation(Reference.ID, Textures.Icon.HELP);
        public static final ResourceLocation JAMMED = new ResourceLocation(Reference.ID, Textures.Icon.JAMMED);
        public static final ResourceLocation NO_BOTTLES = new ResourceLocation(Reference.ID, Textures.Icon.NO_BOTTLES);
        public static final ResourceLocation NO_RECIPE = new ResourceLocation(Reference.ID, Textures.Icon.NO_RECIPE);
        public static final ResourceLocation NO_ENERGY = new ResourceLocation(Reference.ID, Textures.Icon.UNPOWERED);
    }

    public static final class Tab
    {
        public static final ResourceLocation RIGHT = new ResourceLocation(Reference.ID, Textures.Gui.TAB_RIGHT);
        public static final ResourceLocation LEFT = new ResourceLocation(Reference.ID, Textures.Gui.TAB_LEFT);
        public static final ResourceLocation TABLE_HEX = new ResourceLocation(Reference.ID, Textures.Gui.TABLE_HEX);
    }

    public static final class Gui
    {
        public static final ResourceLocation JOURNAL = new ResourceLocation(Reference.ID, Textures.Gui.JOURNAL);
        public static final ResourceLocation POLYTOOL = new ResourceLocation(Reference.ID, Textures.Gui.POLYTOOL);
        public static final ResourceLocation DECOMPOSER = new ResourceLocation(Reference.ID, Textures.Gui.DECOMPOSER);
        public static final ResourceLocation NEI_DECOMPOSER = new ResourceLocation(Reference.ID, Textures.Gui.DECOMPOSER_NEI);
        public static final ResourceLocation SYNTHESIS = new ResourceLocation(Reference.ID, Textures.Gui.SYNTHESIS);
        public static final ResourceLocation PROJECTOR = new ResourceLocation(Reference.ID, Textures.Gui.PROJECTOR);
        public static final ResourceLocation LEADED_CHEST = new ResourceLocation(Reference.ID, Textures.Gui.LEADED_CHEST);
        public static final ResourceLocation MICROSCOPE = new ResourceLocation(Reference.ID, Textures.Gui.MICROSCOPE);
        public static final ResourceLocation FISSION = new ResourceLocation(Reference.ID, Textures.Gui.FISSION);
        public static final ResourceLocation FUSION = new ResourceLocation(Reference.ID, Textures.Gui.FUSION);
    }

    public static final class Model
    {
        public static final ResourceLocation PROJECTOR_OFF = new ResourceLocation(Reference.ID, Textures.Model.PROJECTOR_OFF);
        public static final ResourceLocation PROJECTOR_ON = new ResourceLocation(Reference.ID, Textures.Model.PROJECTOR_ON);
        public static final ResourceLocation DECOMPOSER_OFF = new ResourceLocation(Reference.ID, Textures.Model.DECOMPOSER_OFF);
        public static final ResourceLocation DECOMPOSER_ON = new ResourceLocation(Reference.ID, Textures.Model.DECOMPOSER_ON);
        public static final ResourceLocation LEADED_CHEST = new ResourceLocation(Reference.ID, Textures.Model.LEADED_CHEST);
        public static final ResourceLocation MICROSCOPE = new ResourceLocation(Reference.ID, Textures.Model.MICROSCOPE);
        public static final ResourceLocation SYNTHESIS = new ResourceLocation(Reference.ID, Textures.Model.SYNTHESIS);
    }

}
