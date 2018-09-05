package basemod;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.helpers.Hitbox;

public abstract class TopPanelItem extends ClickableUIElement{

    private static final float WIDTH = 64.0f;
    private static final float HEIGHT = 64.0f;

    public TopPanelItem(Texture image) {
        super(image, 0, 0, WIDTH, HEIGHT);
    }

    public Texture getImage(){
        return this.image;
    }

    public Hitbox getHitbox() {
        return this.hitbox;
    }

}
