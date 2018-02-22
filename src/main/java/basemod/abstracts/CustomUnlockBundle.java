package basemod.abstracts;

import java.util.ArrayList;

public class CustomUnlockBundle {
	private ArrayList<String> unlocks;
	private ArrayList<CustomUnlock> actualUnlocks;
	private boolean initialized = false;
	
	public CustomUnlockBundle(String unlock1, String unlock2, String unlock3) {
		unlocks = new ArrayList<>();
		actualUnlocks = new ArrayList<>();
		unlocks.add(unlock1);
		unlocks.add(unlock2);
		unlocks.add(unlock3);
	}
	
	public ArrayList<CustomUnlock> getUnlocks() {
		if (!initialized) {
			actualUnlocks.add(new CustomUnlock(unlocks.remove(0)));
			actualUnlocks.add(new CustomUnlock(unlocks.remove(0)));
			actualUnlocks.add(new CustomUnlock(unlocks.remove(0)));
			initialized = true;
		}

		return actualUnlocks;
	}
}
