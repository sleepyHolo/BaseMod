package basemod.interfaces;

import com.megacrit.cardcrawl.rooms.AbstractRoom;

public interface PostBattleSubscriber {
	void receivePostBattle(AbstractRoom battleRoom);
}
