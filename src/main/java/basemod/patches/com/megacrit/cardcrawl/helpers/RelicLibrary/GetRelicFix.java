package basemod.patches.com.megacrit.cardcrawl.helpers.RelicLibrary;

import java.util.HashMap;
import java.util.Map;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.helpers.RelicLibrary", method="getRelic")
public class GetRelicFix {

	public static AbstractRelic Postfix(AbstractRelic relic, String key) {
		if (relic instanceof Circlet) {
			for (Map.Entry<String, HashMap<String, AbstractRelic>> map : 
				BaseMod.getAllCustomRelics().entrySet()) {
				if (map.getValue().containsKey(key)) {
					return (AbstractRelic) map.getValue().get(key);
				}
			}
		}

		// default to the same circlet
		return relic;
	}
	
}
