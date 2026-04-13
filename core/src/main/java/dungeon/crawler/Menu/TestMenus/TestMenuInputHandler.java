package dungeon.crawler.Menu.TestMenus;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import dungeon.crawler.Observers.MenuInputObserver;

public class TestMenuInputHandler extends InputAdapter{
public Stage uiStage;
public BaseLinearMenu currentMenuTable;
public boolean showMenu = false;

public int menuColumns = 0;
public int menuRows = 0;
public int menuColumnIndex = 0;
public int menuRowIndex = 0;
public TextButton currentButton;

private final List<MenuInputObserver> listeners = new ArrayList<>();

    public TestMenuInputHandler(
        Stage uiStage,
        BaseLinearMenu currentMenuTable
    ) {
        this.uiStage = uiStage;
        this.currentMenuTable = currentMenuTable;
    }

    public void addListener(MenuInputObserver listener) {
        if(listener != null) {
            listeners.add(listener);
        }
    }

    public void removeListener(MenuInputObserver listener) {
        if(listener != null) {
            listeners.remove(listener);
        }
    }

    public void notifyOnMenuToggled(boolean showMenu) {
        for (MenuInputObserver listener : listeners) {
            listener.onMenuToggled(showMenu);
        }
    }

    public boolean showMenu(){
        if(currentMenuTable != null && currentMenuTable.isVisible()){
            return true;
        }
        return false;
    }

    private void updateCurrentMenuFromFocus() {
        Actor focused = uiStage.getKeyboardFocus();
        if (focused != null) {
            Actor parent = focused;
            while (parent != null) {
                if (parent instanceof BaseLinearMenu) {
                    this.currentMenuTable = (BaseLinearMenu) parent;
                    return; 
                }
                parent = parent.getParent();
            }
        }
    }

    @Override
    public boolean keyDown(int keyCode) {
        updateCurrentMenuFromFocus();
        if(keyCode == Input.Keys.E && currentMenuTable.isToggleable) {
            currentMenuTable.resetMenuSelection();
            return true;
        }

        if(showMenu()) {
            if(keyCode == Input.Keys.ENTER){
                Actor focused = uiStage.getKeyboardFocus();
                if (focused instanceof Button) {
                    Gdx.app.log("Button", "BTTTN clicked");
                    ((Button) focused).toggle(); // Toggles isChecked and fires the listener
                }
            }
            if(keyCode == Input.Keys.DOWN) {
                currentMenuTable.advanceMenuSelection(1);
            }
            if(keyCode == Input.Keys.UP) {
                currentMenuTable.advanceMenuSelection(-1);
            }
            if(keyCode == Input.Keys.BACKSPACE){
                currentMenuTable.returnToParentMenu();
            }
        }
        return false;
    }
    public void setCurrentMenuTable(BaseLinearMenu currentMenuTable) {
        this.currentMenuTable = currentMenuTable;
    }
}
