package dungeon.crawler;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class WorldScreen extends ScreenAdapter {
	private MainGame game;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
    public WorldScreen(MainGame game) {
        this.game = game;
		map = new TmxMapLoader().load("testmap.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		
		// set up camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 400, 200);
		camera.zoom=0.5f;
		camera.update();    
	}
    
	@Override
	public void render(float delta) {
	    Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		handleInput();
		
		camera.update();
		renderer.setView(camera);
		
		renderer.render();
	}

    private void handleInput() {
        float speed = 200 * Gdx.graphics.getDeltaTime(); // movement speed

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.y += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y -= speed;
        }
    }
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
    

	

}
