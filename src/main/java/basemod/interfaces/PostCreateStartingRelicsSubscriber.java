package basemod.interfaces;

import java.util.ArrayList;

public interface PostCreateStartingRelicsSubscriber extends ISubscriber {
	boolean receivePostCreateStartingRelics(ArrayList<String> addRelicsToMe);
}
