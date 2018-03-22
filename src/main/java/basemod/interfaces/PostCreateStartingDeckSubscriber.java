package basemod.interfaces;

import java.util.ArrayList;

public interface PostCreateStartingDeckSubscriber extends ISubscriber {
	boolean receivePostCreateStartingDeck(ArrayList<String> addCardsToMe);
}
