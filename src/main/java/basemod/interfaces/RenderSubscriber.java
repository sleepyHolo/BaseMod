package basemod.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface RenderSubscriber {
    void receiveRender(SpriteBatch sb);
}