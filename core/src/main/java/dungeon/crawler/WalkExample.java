package dungeon.crawler;

import com.badlogic.gdx.ScreenAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;



public class WalkExample extends ScreenAdapter {
	Texture characterTexture;
	SpriteBatch spriteBatch;
	FitViewport viewport;
	
	AnimatedSprite characterSprite;
	
	float worldWidth;
	float worldHeight;
	
	// constants
	
	// TODO: frame duration for walk anim, make this more configurable and more legible
	float frameDuration = .15f;
	boolean walking = false;
	
	
	private MainGame game;

	public WalkExample(MainGame game) {
		this.game = game;
		
        spriteBatch = new SpriteBatch();
    	viewport = new FitViewport(6,4);
		worldWidth = viewport.getWorldWidth();
		worldHeight = viewport.getWorldHeight();
		float initialX = worldWidth / 2f;
		float initialY = worldHeight / 2f;
    	characterSprite = new AnimatedSprite(
    		buildWalkAnim(),
    		"walkDown",
    		initialX, 
    		initialY,
    		.35f,
    		.5f
    	);
	}
	
    @Override
    public void render(float delta) {
    	input();
    	logic();
    	draw();
    }
    
    private void input() {
    	// TODO: Move out of main class and somewhere else
    	// TODO: switch statement? if else sucks for this
    	// TODO: Configurable keys dammit
		float speed = 4f;
		float delta = Gdx.graphics.getDeltaTime();
		Sprite sprite = characterSprite.getSprite();
		characterSprite.walking = false;


		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			sprite.translateX(speed * delta);
			characterSprite.setState("walkRight");
			characterSprite.walking = true;
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			sprite.translateX(-speed * delta);
			characterSprite.setState("walkLeft");
			characterSprite.walking = true;
		} else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			sprite.translateY(speed * delta);
			characterSprite.setState("walkUp");
			characterSprite.walking = true;

		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			sprite.translateY(-speed * delta);
			characterSprite.setState("walkDown");
			characterSprite.walking = true;
		}		
    }
    private void draw() {
		float delta = Gdx.graphics.getDeltaTime();
    	Sprite sprite = characterSprite.getSprite();
    	ScreenUtils.clear(Color.BLACK);
    	viewport.apply();
    	spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
    	
    	spriteBatch.begin();
    	if (characterSprite.walking) {
        	characterSprite.update(delta);
    	}
        characterSprite.render(spriteBatch);	
    	spriteBatch.end();

	}
    
    private void logic() {
    	Sprite sprite = characterSprite.getSprite();
    	sprite.setX(MathUtils.clamp(sprite.getX(), 0, worldWidth - sprite.getWidth()));
    	sprite.setY(MathUtils.clamp(sprite.getY(), 0, worldHeight - sprite.getHeight()));

    }
    
    
    private Map<String, Animation<TextureRegion>> buildWalkAnim(){
    	Texture fullSheet = new Texture("Sprites/mc_male.png");
    	Map<String, Animation<TextureRegion>> animationMap = new HashMap<String, Animation<TextureRegion>>();
    	
    	animationMap.put("walkDown", AnimationBuilder.createAnimationByRow(fullSheet, 3, 4, 1, frameDuration));
    	animationMap.put("walkLeft", AnimationBuilder.createAnimationByRow(fullSheet, 3, 4, 2, frameDuration));
    	animationMap.put("walkRight", AnimationBuilder.createAnimationByRow(fullSheet, 3, 4, 3, frameDuration));
    	animationMap.put("walkUp", AnimationBuilder.createAnimationByRow(fullSheet, 3, 4, 4, frameDuration));
    	return animationMap;
    	
    }
   

	@Override
    public void resize(int width, int height) {
    	viewport.update(width, height, true);
    }


    @Override
    public void dispose() {

    }

}
