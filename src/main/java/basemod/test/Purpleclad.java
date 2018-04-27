package basemod.test;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class Purpleclad extends AbstractPlayer {

	public Purpleclad(String name, PlayerClass setClass) {
		super(name, setClass);
		
		this.dialogX = (this.drawX + 0.0F * Settings.scale);
		this.dialogY = (this.drawY + 220.0F * Settings.scale);
		
		initializeClass(null, "images/characters/ironclad/shoulder2.png", "images/characters/ironclad/shoulder.png", "images/characters/ironclad/corpse.png", 
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(3));
		
		loadAnimation("images/characters/ironclad/idle/skeleton.atlas", "images/characters/ironclad/idle/skeleton.json", 1.0F);
		
		AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
		e.setTime(e.getEndTime() * MathUtils.random());
	}

	public static ArrayList<String> getStartingDeck() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Strike_P");
		retVal.add("Strike_P");
		retVal.add("Strike_P");
		retVal.add("Strike_P");
		retVal.add("Defend_P");
		retVal.add("Defend_P");
		retVal.add("Defend_P");
		retVal.add("Defend_P");
		return retVal;
	}
	
	public static ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Arcanosphere");
		UnlockTracker.markRelicAsSeen("Arcanosphere");
		return retVal;
	}
	
	public static CharSelectInfo getLoadout() {
		return new CharSelectInfo("The Purpleclad", "A long-lost survivor of the Ironclads. Decided to dye himself purple.",
				75, 75, 0, 99, 5,
			CharacterEnumPatch.THE_PURPLECLAD, getStartingRelics(), getStartingDeck(), false);
	}
	
	
}
