package minechem.api;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ChemicalAPI {

	private static final Class classMoleculeEnum;
	private static final Class classElementEnum;
	private static final Class classElement;
	private static final Class classMolecule;
	private static final Class classChemicalRoomStateEnum;
	private static final Class classArrayPotionChemical;
	private static final Class classPotionChemical;
	private static final boolean isMinechemInstalled;
	
	static{
		Class preClassMoleculeEnum=null;
		Class preClassElementEnum=null;
		Class preClassElement=null;
		Class preClassMolecule=null;
		Class preClassChemicalRoomStateEnum=null;
		Class preClassArrayPotionChemical=null;
		Class preClassPotionChemical=null;
		boolean preIsMinechemInstalled=true;
		
		try {
			preClassMoleculeEnum=Class.forName("minechem.item.molecule.MoleculeEnum");
			preClassElementEnum=Class.forName("minechem.item.element.ElementEnum");
			preClassElement=Class.forName("minechem.item.element.Element");
			preClassMolecule=Class.forName("minechem.item.molecule.Molecule");
			preClassChemicalRoomStateEnum=Class.forName("minechem.item.ChemicalRoomStateEnum");
			preClassPotionChemical=Class.forName("minechem.potion.PotionChemical");
			preClassArrayPotionChemical=Class.forName("[Lminechem.potion.PotionChemical;");
		} catch (ClassNotFoundException e) {
			preIsMinechemInstalled=false;
		}
		
		isMinechemInstalled=preIsMinechemInstalled;
		classElementEnum=preClassElementEnum;
		classMoleculeEnum=preClassMoleculeEnum;
		classElement=preClassElement;
		classMolecule=preClassMolecule;
		classChemicalRoomStateEnum=preClassChemicalRoomStateEnum;
		classArrayPotionChemical=preClassArrayPotionChemical;
		classPotionChemical=preClassPotionChemical;
	}
	
	/**
	 * Adds a molecule to the game.
	 * <p>
	 * Argument 'molecule' means the compositions the molecule.
	 * For example, if argument molecule is ["Fr","hydroxide"],
	 * then the molecular formula of molecule is FrOH.
	 * And if argument molecule is ["Fr",2,"hydroxide"],
	 * then the molecular formula of molecule is Fr(OH)2.
	 * So, you neen not to write '1' in front of the element.
	 * <p>
	 * Argument 'roomStatus' has 3 available value, "solid","liquid","gas".
	 * 
	 * @param id the id of the molecule
	 * @param name the name of the molecule
	 * @param colorRed the red of the first layer's color
	 * @param colorGreen the green of the first layer's color
	 * @param colorBlue the blue of the first layer's color
	 * @param colorRed2 the red of the second layer's color
	 * @param colorGreen2 the green of the second layer's color
	 * @param colorBlue2 the blue of the second layer's color
	 * @param roomStatus the room status of the molecule
	 * @param molecule the compositions the molecule
	 * @return Add the molecule successfully?
	 */
	public static boolean registerMolecule(int id,String name, float colorRed, float colorGreen, float colorBlue, float colorRed2, float colorGreen2, float colorBlue2,String roomStatus,Object... molecule){
		if (!isMinechemInstalled){
			return false;
		}
		
		List<Object> chemicals=new ArrayList<Object>();
		int pos=0;
		while (pos<molecule.length){
			if (molecule[pos] instanceof Integer){
				chemicals.add(createChemical(getChemicalType((String) molecule[pos+1]), (Integer) molecule[pos]));
				pos+=2;
			}else if(molecule[pos] instanceof String){
				chemicals.add(createChemical(getChemicalType((String) molecule[pos+1]),1));
				pos+=1;
			}else{
				throw new IllegalArgumentException("unknown argument");
			}
		}
		
		try {
			classMoleculeEnum.getConstructor(String.class,int.class,float.class,float.class,float.class,float.class,float.class,float.class,classChemicalRoomStateEnum,classArrayPotionChemical).newInstance(name,id,colorRed,colorGreen,colorBlue,colorRed2,colorGreen2,colorBlue2,getRoomStatus(roomStatus),chemicals.toArray((Object[])Array.newInstance(classPotionChemical, chemicals.size())));
		} catch (InstantiationException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return false;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return false;
		} catch (SecurityException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private static Object getChemicalType(String name){
		try{
			Field elementsField=classElementEnum.getField("elements");
			Object[] elements=(Object[]) elementsField.get(null);
			for (Object element:elements){
				if (element.toString().equals(name)){
					return element;
				}
			}
			
			Field moleculesField=classMoleculeEnum.getField("molecules");
			Object[] molecules=(Object[]) moleculesField.get(null);
			for (Object molecule:molecules){
				if (molecule.toString().equals(name)){
					return molecule;
				}
			}
		} catch (SecurityException e){
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static Object createChemical(Object chemical,int amount){
		try{
			if (chemical.getClass()==classElementEnum){
				return classElement.getConstructor(classElementEnum,int.class).newInstance(chemical,amount);
			}else if (chemical.getClass()==classMoleculeEnum){
				return classMolecule.getConstructor(classMoleculeEnum,int.class).newInstance(chemical,amount);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static Object getRoomStatus(String roomStatus){
		try {
			return classChemicalRoomStateEnum.getField(roomStatus).get(null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
