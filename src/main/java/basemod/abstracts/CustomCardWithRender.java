package basemod.abstracts;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.helpers.CustomCardRender.CustomCardRenderHelper;

public abstract class CustomCardWithRender extends CustomCard {
public CustomCardWithRender(String id, String name, String img, String bgTexture, String bgTexture_p,
		int cost, String rawDescription, CardType type, CardColor color, 
		CardRarity rarity, CardTarget target, int cardPool) {
		super(id, name, img, cost, rawDescription, type, color, rarity, target, cardPool);
		
		this.bGTexture = this.getTextureFromString(bgTexture);
		this.bGTexture_p = this.getTextureFromString(bgTexture_p);
	}

	private Texture getTextureFromString(String textureString) {
		if (imgMap.containsKey(textureString)) {
			return imgMap.get(textureString);
		}else {
			imgMap.put(textureString, new Texture(Gdx.files.internal(textureString)));
			return imgMap.get(textureString);
		}
	}

	public static HashMap<String, Texture> imgMap;
	
	public static final String PORTRAIT_ENDING = "_p";
	
	public static Texture getPortraitImage(CustomCard card) {
		int endingIndex = card.textureImg.lastIndexOf(".");
		String newPath = card.textureImg.substring(0, endingIndex) + 
				PORTRAIT_ENDING + card.textureImg.substring(endingIndex); 
		System.out.println("Finding texture: " + newPath);
		Texture portraitTexture;
		try {
			portraitTexture = new Texture(newPath);
		} catch (Exception e) {
			portraitTexture = null;
		}
		return portraitTexture;
	}
	
	public Texture getOrbSmallTexture() {
		return orbSmallTexture;
	}
	
	public Texture getOrbPortraitTexture() {
		return orbPortraitTexture;
	}
	
	/**
	 * @param orbSmallString
	 * @param orbLargeString
	 */
	public void setOrbTexture(String orbSmallString, String orbLargeString) {
		imgMap.put(orbSmallString, new Texture(orbSmallString));
		imgMap.put(orbLargeString, new Texture(orbLargeString));
		
		orbSmallTexture = imgMap.get(orbSmallString);
		orbPortraitTexture = imgMap.get(orbLargeString);
	}
	
	static {
		imgMap = new HashMap<>();
	}
	
	public String textureImg;
	
	// loadCardImage - copy of hack here: https://github.com/t-larson/STS-ModLoader/blob/master/modloader/CustomCard.java
	public void loadCardImage(String img) {
		Texture cardTexture;
		if (imgMap.containsKey(img)) {
			cardTexture = imgMap.get(img);
		} else {
			cardTexture = new Texture(img);
			imgMap.put(img, cardTexture);
		}
		cardTexture.setFilter(Texture.TextureFilter.Linear,  Texture.TextureFilter.Linear);
		int tw = cardTexture.getWidth();
		int th = cardTexture.getHeight();
		TextureAtlas.AtlasRegion cardImg = new AtlasRegion(cardTexture, 0, 0, tw, th);
		ReflectionHacks.setPrivateInherited(this, CustomCard.class, "portrait", cardImg);
	}
	
	public void renderCard(SpriteBatch sb, float x, float y) {
		CustomCardRenderHelper.renderCardBG(sb, x, y, this);
	}
	
	public void renderCardPortrait(SpriteBatch sb) {
		CustomCardRenderHelper.renderCardBGPortrait(sb, this);
	}
	
	public Texture orbSmallTexture = null;
	public Texture orbPortraitTexture = null;
	public Texture bGTexture = null;
	public Texture bGTexture_p = null;
}