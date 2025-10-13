package dungeon.crawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuInputHandler extends InputAdapter{
	public Stage uiStage;
	public OverworldMenu menuTable;
	public boolean showMenu = false;
	public int menuColumns = 0;
	public int menuRows = 0;
	public int menuColumnIndex = 0;
	public int menuRowIndex = 0;

	public MenuInputHandler(
		Stage uiStage,
		OverworldMenu menuTable
	) {
		this.uiStage = uiStage;
		this.menuTable = menuTable;
	}
	
	@Override
	public boolean keyUp(int keyCode) {
		if(keyCode == Input.Keys.E) {
			showMenu = !showMenu;
			menuColumnIndex = 0;
			menuRowIndex = 0;
			String openClosed = showMenu ? "open" : "closed";
			Gdx.app.log("Menu", "Menu is " + openClosed);

			return true;

		}
		if(showMenu) {
			if(keyCode == Input.Keys.DOWN) {
				int nextRow = menuRowIndex++;
				if(menuTable.buttonList.length > nextRow) {
					menuRowIndex = nextRow;
					TextButton currentButton = menuTable.buttonList[menuRowIndex][menuColumnIndex];
					uiStage.setKeyboardFocus(currentButton);
					Gdx.app.log("Menu", "Down key");

				}
			}
			if(keyCode == Input.Keys.UP) {
				int nextRow = menuRowIndex--;
				if(nextRow >=0) {
					menuRowIndex = nextRow;
					TextButton currentButton = menuTable.buttonList[menuRowIndex][menuColumnIndex];
					uiStage.setKeyboardFocus(currentButton);
					Gdx.app.log("Menu", "Up key");

				}
			}
		}
		return false;
	}
	
	

}
