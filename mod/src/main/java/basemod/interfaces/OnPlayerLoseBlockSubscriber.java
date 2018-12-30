package basemod.interfaces;

public interface OnPlayerLoseBlockSubscriber extends ISubscriber {
    int receiveOnPlayerLoseBlock(int amount);
}
