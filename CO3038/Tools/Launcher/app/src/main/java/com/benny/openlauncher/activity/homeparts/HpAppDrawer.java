package com.benny.openlauncher.activity.homeparts;

import android.view.View;

import com.benny.openlauncher.activity.HomeActivity;
import com.benny.openlauncher.manager.Setup;
import com.benny.openlauncher.util.AppSettings;
import com.benny.openlauncher.util.Tool;
import com.benny.openlauncher.widget.AppDrawerController;
import com.benny.openlauncher.widget.PagerIndicator;

import net.gsantner.opoc.util.Callback;

public class HpAppDrawer implements Callback.a2<Boolean, Boolean> {
    private HomeActivity _homeActivity;
    private PagerIndicator _appDrawerIndicator;

    public HpAppDrawer(HomeActivity homeActivity, PagerIndicator appDrawerIndicator) {
        _homeActivity = homeActivity;
        _appDrawerIndicator = appDrawerIndicator;
    }

    public void initAppDrawer(AppDrawerController appDrawerController) {
        appDrawerController.setCallBack(this);
        AppSettings appSettings = Setup.appSettings();

        appDrawerController.setBackgroundColor(appSettings.getDrawerBackgroundColor());
        appDrawerController.getBackground().setAlpha(0);
        appDrawerController.reloadDrawerCardTheme();

        switch (appSettings.getDrawerStyle()) {
            case AppDrawerController.DrawerMode.HORIZONTAL_PAGED: {
                if (!appSettings.isDrawerShowIndicator()) {
                    appDrawerController.getChildAt(1).setVisibility(View.GONE);
                }
                break;
            }
            case AppDrawerController.DrawerMode.VERTICAL: {
                // handled in the AppDrawerVertical class
                break;
            }
        }
    }

    @Override
    public void callback(Boolean openingOrClosing, Boolean startOrEnd) {
        if (openingOrClosing) {
            if (startOrEnd) {
                Tool.visibleViews(_appDrawerIndicator);
                Tool.invisibleViews(_homeActivity.getDesktop());
                _homeActivity.updateDesktopIndicator(false);
                _homeActivity.updateDock(false, 0);
                _homeActivity.updateSearchBar(false);
            }
        } else {
            if (startOrEnd) {
                Tool.invisibleViews(_appDrawerIndicator);
                Tool.visibleViews(_homeActivity.getDesktop());
                _homeActivity.updateDesktopIndicator(true);
                if (Setup.appSettings().getDrawerStyle() == AppDrawerController.DrawerMode.HORIZONTAL_PAGED)
                    _homeActivity.updateDock(true, 200);
                else
                    _homeActivity.updateDock(true, 200);
                _homeActivity.updateSearchBar(true);
            } else {
                if (!Setup.appSettings().isDrawerRememberPosition()) {
                    _homeActivity.getAppDrawerController().scrollToStart();
                }
                _homeActivity.getAppDrawerController().getDrawer().setVisibility(View.INVISIBLE);
            }
        }
    }
}
