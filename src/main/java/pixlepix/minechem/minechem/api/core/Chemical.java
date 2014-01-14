package pixlepix.minechem.minechem.api.core;

public class Chemical {

    public int amount;

    public Chemical(int amount) {
        this.amount = amount;
    }

    public Chemical copy(){
    	return new Chemical(amount);
    }
    
    public boolean sameAs(Chemical chemical) {
        return false;
    }

}
