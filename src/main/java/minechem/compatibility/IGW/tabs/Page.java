package minechem.compatibility.IGW.tabs;

import minechem.registry.BlockRegistry;

public enum Page {
	WELCOME("Welcome!", "welcome"),
	FIRST_STEPS("First Steps", "firststeps"),
	CENTRIFUGE("Centrifuge", "centrifuge"),
	ELECTRIC_CRUCIBLE("Electric Crucible", "electric_crucible"),
	OPTICAL_MICROSCOPE("Optical Microscope", "microscope"),
	FOOD_SPIKING("Food Spiking", "food_spiking");
	
	public String name;
	public String fileName;
	public String id;
	private Page(String name, String fileName, String id) {
		this.name = name;
		this.fileName = fileName;
		this.id = id;
	}
	private Page(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
		this.id = fileName;
	}
	private Page(String name) {
		this.name = name;
		this.fileName = name;
		this.id = name;
	}
	public static Page findById(String id) {
		for (Page page : values()) {
			if(page.id.equals(id)) {
				return page;
			}
		}
		return null;
	}
}
