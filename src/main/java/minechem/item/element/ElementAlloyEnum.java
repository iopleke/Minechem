package minechem.item.element;


public enum ElementAlloyEnum
{
    // Axe-Shovel-Pickaxe-Stone-Sword
    // All of these are multiplied by 2
    Aluminium(ElementEnum.Al, 4F, -2F, -2F, 0F, 0F), Scandium(ElementEnum.Sc, 4F, 0F, 0F, -2F, -2F), Vandium(ElementEnum.V, 4F, -1F, -1F, -1F, -1F),

    Manganese(ElementEnum.Mn, -2F, 4F, -2F, 0F, 0F), Cobalt(ElementEnum.Co, 0F, 4F, 0F, -2F, -2F), Cadmium(ElementEnum.Cd, -1F, 4F, -1F, -1F, -1F),

    Gallium(ElementEnum.Ga, -2F, -2F, 4F, 0F, 0F), Selenium(ElementEnum.Se, 0F, 0F, 4F, -2F, -2F), Strontium(ElementEnum.Sr, -1F, -1F, 4F, -1F, -1F),

    Yttrium(ElementEnum.Y, -2F, 0F, -2F, 4F, 0F), Niobium(ElementEnum.Nb, 0F, -2F, 0F, 4F, -2F), Molybdeum(ElementEnum.Mo, -1F, -1F, -1F, 4F, -1F),

    Ruthenium(ElementEnum.Ru, -2F, 0F, -2F, 0F, 4F), Rhodium(ElementEnum.Rh, 0F, -2F, 0F, -2F, 4F), Palladium(ElementEnum.Pd, -1F, -1F, -1F, -1F, 4F);

    public final float axe;
    public final float shovel;
    public final float pickaxe;
    public final float stone;
    public final float sword;
    public final ElementEnum element;

    private ElementAlloyEnum(ElementEnum element, float axe, float shovel, float pickaxe, float stone, float sword)
    {
        this.element = element;
        this.axe = 2 * axe;
        this.shovel = 2 * shovel;
        this.pickaxe = 2 * pickaxe;
        this.stone = 2 * stone;
        this.sword = 2 * sword;
    }

}
