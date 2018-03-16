package basemod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

public class ModImage implements IUIElement {
	private Texture texture;
	private float x;
	private float y;
	private float w;
	private float h;

	public ModImage(float x, float y, String texturePath) {
		this.texture = new Texture(Gdx.files.internal(texturePath));
		this.x = x * Settings.scale;
		this.y = y * Settings.scale;
		this.w = texture.getWidth() * Settings.scale;
		this.h = texture.getHeight() * Settings.scale;
	}

	public ModImage(float x, float y, Texture tex) {
		this.texture = tex;
		this.x = x * Settings.scale;
		this.y = y * Settings.scale;
		this.w = texture.getWidth() * Settings.scale;
		this.h = texture.getHeight() * Settings.scale;
	}

	public void render(SpriteBatch sb) {
		sb.setColor(Color.WHITE);
		sb.draw(texture, x, y, w, h);
	}
	
	public void update() {}
	
	@Override
	public int renderLayer() {
		return ModPanel.BACKGROUND_LAYER;
	}

	@Override
	public int updateOrder() {
		return ModPanel.DEFAULT_UPDATE;
	}
}