package basemod.abstracts;

import java.util.ArrayList;

public class CustomUnlockBundle {
	private ArrayList<CustomUnlock> unlocks;
	
	public CustomUnlockBundle(CustomUnlock unlock1, CustomUnlock unlock2, CustomUnlock unlock3) {
		unlocks = new ArrayList<>();
		unlocks.add(unlock1);
		unlocks.add(unlock2);
		unlocks.add(unlock3);
	}
	
	public ArrayList<CustomUnlock> getUnlocks() {
		return unlocks;
	}
}
