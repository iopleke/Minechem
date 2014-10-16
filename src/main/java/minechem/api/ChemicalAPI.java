package minechem.api;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChemicalAPI {

	private static final Class classMoleculeEnum;
	private static final Class classElementEnum;
	private static final Class classElement;
	private static final Class classMolecule;
	private static final Class classChemicalRoomStateEnum;
	private static final Class classArrayPotionChemical;
	private static final Class classPotionChemical;
	private static final Class classChemicalFluidReactionHandler;
	private static final Class classChemicalFluidReactionOutput;
	private static final Class classChemicalFluidReactionRule;
	private static final Class classMinechemChemicalType;
	private static final boolean isMinechemInstalled;
	
	static{
		Class preClassMoleculeEnum=null;
		Class preClassElementEnum=null;
		Class preClassElement=null;
		Class preClassMolecule=null;
		Class preClassChemicalRoomStateEnum=null;
		Class preClassArrayPotionChemical=null;
		Class preClassPotionChemical=null;
		Class preClassChemicalFluidReactionHandler=null;
		Class preClassChemicalFluidReactionOutput=null;
		Class preClassChemicalFluidReactionRule=null;
		Class preClassMinechemChemicalType=null;
		boolean preIsMinechemInstalled=true;
		
		try {
			preClassMoleculeEnum=Class.forName("minechem.item.molecule.MoleculeEnum");
			preClassElementEnum=Class.forName("minechem.item.element.ElementEnum");
			preClassElement=Class.forName("minechem.item.element.Element");
			preClassMolecule=Class.forName("minechem.item.molecule.Molecule");
			preClassChemicalRoomStateEnum=Class.forName("minechem.item.ChemicalRoomStateEnum");
			preClassPotionChemical=Class.forName("minechem.potion.PotionChemical");
			preClassArrayPotionChemical=Class.forName("[Lminechem.potion.PotionChemical;");
			preClassChemicalFluidReactionHandler=Class.forName("minechem.fluid.reaction.ChemicalFluidReactionHandler");
			preClassChemicalFluidReactionOutput=Class.forName("minechem.fluid.reaction.ChemicalFluidReactionOutput");
			preClassChemicalFluidReactionRule=Class.forName("minechem.fluid.reaction.ChemicalFluidReactionRule");
			preClassMinechemChemicalType=Class.forName("minechem.item.MinechemChemicalType");
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
		classChemicalFluidReactionHandler=preClassChemicalFluidReactionHandler;
		classChemicalFluidReactionOutput=preClassChemicalFluidReactionOutput;
		classChemicalFluidReactionRule=preClassChemicalFluidReactionRule;
		classMinechemChemicalType=preClassMinechemChemicalType;
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
		
		List<Object> chemicals = toChemicalsWithAmount(molecule);
		
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
	
	/**
	 * Adds a molecule with the random color to the game.
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
	 * @param roomStatus the room status of the molecule
	 * @param molecule the compositions the molecule
	 * @return Add the molecule successfully?
	 */
	public static boolean registerMolecule(int id,String name,String roomStatus,Object... molecule){
		if (!isMinechemInstalled){
			return false;
		}
		
		List<Object> chemicals = toChemicalsWithAmount(molecule);
		
		try {
			classMoleculeEnum.getConstructor(String.class,int.class,classChemicalRoomStateEnum,classArrayPotionChemical).newInstance(name,id,getRoomStatus(roomStatus),chemicals.toArray((Object[])Array.newInstance(classPotionChemical, chemicals.size())));
			return true;
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
		
		return false;
	}
	
	/**
	 * Adds a fluid chemical reaction rule.
	 * <p>
	 * If explosionLevel==Float.NaN , it will not explode.<br>
	 * Argument 'outputChemicals' means what fluid should generate
	 * after the reaction. Its value should be like ["water","K"] , ["hcl,water"].
	 * You could not write a number before the chemical, so value like these
	 * ["2water","1K"] , [3,"hcl",2,"water"] are not available.
	 * 
	 * @param chemicalA one of the chemicals involved in the reaction
	 * @param chemicalB another of the chemicals involved in the reaction
	 * @param explosionLevel the level of the explosion
	 * @param outputChemicals the chemicals of it outputs
	 * @return Add the reaction successfully?
	 */
	public static boolean registerFluidChemicalReaction(String chemicalA,String chemicalB,float explosionLevel,String... outputChemicals){
		if (!isMinechemInstalled){
			return false;
		}
		
		try {
			List<Object> chemicals=toChemicals(outputChemicals);
			Object rule=classChemicalFluidReactionRule.getConstructor(classMinechemChemicalType,classMinechemChemicalType).newInstance(getChemicalType(chemicalA),getChemicalType(chemicalB));
			Object output=classChemicalFluidReactionOutput.getConstructor(List.class,float.class).newInstance(chemicals,explosionLevel);
			((Map)classChemicalFluidReactionHandler.getDeclaredField("reactionRules").get(null)).put(rule, output);
			return true;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private static List<Object> toChemicals(String[] chemicalsStr){
		List<Object> chemicals=new ArrayList<Object>();
		for (int i=0;i<chemicalsStr.length;i++){
			chemicals.add(getChemicalType(chemicalsStr[i]));
		}
		return chemicals;
	}
	
	private static List<Object> toChemicalsWithAmount(Object[] chemicalsStr) {
		List<Object> chemicals=new ArrayList<Object>();
		int pos=0;
		while (pos<chemicalsStr.length){
			if (chemicalsStr[pos] instanceof Integer){
				chemicals.add(createChemical(getChemicalType((String) chemicalsStr[pos+1]), (Integer) chemicalsStr[pos]));
				pos+=2;
			}else if(chemicalsStr[pos] instanceof String){
				chemicals.add(createChemical(getChemicalType((String) chemicalsStr[pos+1]),1));
				pos+=1;
			}else{
				throw new IllegalArgumentException("unknown argument");
			}
		}
		return chemicals;
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
				if (molecule!=null&&molecule.toString().equals(name)){
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
