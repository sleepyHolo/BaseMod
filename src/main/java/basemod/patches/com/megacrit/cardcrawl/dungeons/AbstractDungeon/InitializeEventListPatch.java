package basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Map.Entry;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.AbstractEvent;

import basemod.BaseMod;

public class InitializeEventListPatch {
	@SpirePatch(cls = "com.megacrit.cardcrawl.helpers.EventHelper", method = "getEvent")
	public static class GetEvent{
		public static AbstractEvent Postfix(AbstractEvent __result, String key) {
			
			@SuppressWarnings("rawtypes")
			Class event = null;
			
			BaseMod.logger.info("Looking for custom events...");
			
			//hahah this code is really fuckin bad
			
			try {
				event = BaseMod.getEventList(BaseMod.EventPool.ANY).get(key);
				if(event != null)
					return (AbstractEvent) event.newInstance();
				event = BaseMod.getEventList(BaseMod.EventPool.THE_EXORDIUM).get(key);
				if(event != null)
					return (AbstractEvent) event.newInstance();
				event = BaseMod.getEventList(BaseMod.EventPool.THE_CITY).get(key);
				if(event != null)
					return (AbstractEvent) event.newInstance();
				event = BaseMod.getEventList(BaseMod.EventPool.THE_BEYOND).get(key);
				if(event != null)
					return (AbstractEvent) event.newInstance();
				
				
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return __result;
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.Exordium", method = "initializeEventList")
	public static class TheExordiumEventList{
		@SuppressWarnings("static-access")
		public static void Postfix(Exordium dungeon) {
			
			BaseMod.logger.info("BaseMod Adding Custom Events...");
			for(@SuppressWarnings("rawtypes") Entry<String, Class> entry : BaseMod.getEventList(BaseMod.EventPool.THE_EXORDIUM).entrySet()) {
				BaseMod.logger.info("BaseMod added " + entry.getKey() + " to the Exordium eventList.");
				dungeon.eventList.add(entry.getKey());
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.TheCity", method = "initializeEventList")
	public static class TheCityEventList{
		@SuppressWarnings("static-access")
		public static void Postfix(TheCity dungeon) {
			
			BaseMod.logger.info("BaseMod Adding Custom Events...");
			for(@SuppressWarnings("rawtypes") Entry<String, Class> entry : BaseMod.getEventList(BaseMod.EventPool.THE_CITY).entrySet()) {
				BaseMod.logger.info("BaseMod added " + entry.getKey() + " to the Exordium eventList.");
				dungeon.eventList.add(entry.getKey());
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.TheBeyond", method = "initializeEventList")
	public static class TheBeyondEventList{
		@SuppressWarnings("static-access")
		public static void Postfix(TheBeyond dungeon) {
			
			BaseMod.logger.info("BaseMod Adding Custom Events...");
			for(@SuppressWarnings("rawtypes") Entry<String, Class> entry : BaseMod.getEventList(BaseMod.EventPool.THE_BEYOND).entrySet()) {
				BaseMod.logger.info("BaseMod added " + entry.getKey() + " to the Exordium eventList.");
				dungeon.eventList.add(entry.getKey());
			}
		}
	}
	
	@SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "initializeSpecialOneTimeEventList")
	public static class SpecialEventList{
		@SuppressWarnings("static-access")
		public static void Postfix(AbstractDungeon dungeon) {
			BaseMod.logger.info("BaseMod Adding Custom SpecialOneTimeEvents...");
			for(@SuppressWarnings("rawtypes") Entry<String, Class> entry : BaseMod.getEventList(BaseMod.EventPool.ANY).entrySet()) {
				BaseMod.logger.info("BaseMod added " + entry.getKey() + " to the specialOneTimeEventList.");
				dungeon.eventList.add(entry.getKey());
			}
		}
	}
}
