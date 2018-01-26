package basemod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

public class ModImage {
    private Texture t;
    private float x;
    private float y;
    private float w;
    private float h;
    
    public ModImage(float x, float y, String texturePath) {
        this.t = new Texture(Gdx.files.internal(texturePath));
        this.x = x * Settings.scale;
        this.y = y * Settings.scale;
        this.w = t.getWidth() * Settings.scale;
        this.h = t.getHeight() * Settings.scale;
    }
    
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE); 
        sb.draw(t, x, y, w, h);
    }
}