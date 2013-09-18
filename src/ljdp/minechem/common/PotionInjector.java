package ljdp.minechem.common;

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
