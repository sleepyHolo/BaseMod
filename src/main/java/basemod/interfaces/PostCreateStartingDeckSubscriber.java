package basemod.interfaces;

import java.util.ArrayList;

public interface PostCreateStartingDeckSubscriber extends ISubscriber {
	@Deprecated
	boolean receivePostCreateStartingDeck(ArrayList<String> addCardsToMe);
}
