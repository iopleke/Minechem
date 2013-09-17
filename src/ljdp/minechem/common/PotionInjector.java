package ljdp.minechem.common;

/*
[08:23.52] <@Forstride> we have an api

[08:23.55] <Mandrake> I needed the potion imjector
[08:24.14] <@Forstride> oh
[08:24.17] <@Amnet> I'm ok with you using that
[08:24.32] <Mandrake> Heh, I have the API thats how I am registering the plants to be－
useable
[08:24.36] <Mandrake> And ok
[08:24.54] <@Amnet> I actually think that's the way people should make new potions effect 
to not collide with each other
[08:24.59] <Mandrake> Could I also use the wildcarrot texture for a potion effect (It looks－
like a mandrake root)
[08:25.24] <Mandrake> (And yes my name comes from the mandrake plant aka the dream－
plant as I daydream too mutch)
[08:25.28] <Mandrake> Like wayy to much
[08:26.03] <@Amnet> with that question I point you to Forsride, as he is making all the－
textures :P
[08:26.24] <@Forstride> you can
[08:26.34] <@Forstride> it's just the old vanilla carrot texture with the carrot part recolored
[08:32.49] * stardustrider (~theStardu@176.26.254.125) Quit ()
[08:33.19] <Mandrake> ok
*/ 
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;

public class PotionInjector {
	public static Potion atropineHigh;

	public static void inject() {
		int potionTotal = Potion.potionTypes.length;
		Potion[] effectAray = new Potion[potionTotal + 2];
		System.arraycopy(Potion.potionTypes, 0, effectAray, 0, potionTotal);
		Field field = null;
		Field[] fields = Potion.class.getDeclaredFields();

		for (Field f : fields)
			if (f.getName().equals("potionTypes")
					|| f.getName().equals("field_76425_a")) {
				field = f;
				break;
			}

		try {
			field.setAccessible(true);

			Field modfield = Field.class.getDeclaredField("modifiers");
			modfield.setAccessible(true);
			modfield.setInt(field, field.getModifiers() & ~Modifier.FINAL);

			field.set(null, effectAray);
		} catch (Exception e) {
			System.err.println("He's Dead Jim" + " " + e);
		}
		atropineHigh = new PotionProvider(potionTotal, true, 0x00FF6E).setPotionName("Delirium").setIconIndex(2, 1); // That icon is a refrence to alice in wonderland :P
	}
}
