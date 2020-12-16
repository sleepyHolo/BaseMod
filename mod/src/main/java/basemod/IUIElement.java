package basemod;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IUIElement {
	void render(SpriteBatch sb);
	void update();
	int renderLayer();
	int updateOrder();
	default void move(float xPos, float yPos) {}
	default void moveX(float xPos) {}
	default void moveY(float yPos) {}
	default float getX() {return Integer.MIN_VALUE;}
	default float getY() {return Integer.MIN_VALUE;}
}
