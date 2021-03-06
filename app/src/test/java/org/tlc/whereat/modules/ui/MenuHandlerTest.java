/**
 *
 * Copyright (c) 2015-present, Total Location Test Paragraph.
 * All rights reserved.
 *
 * This file is part of Where@. Where@ is free software:
 * you can redistribute it and/or modify it under the terms of
 * the GNU General Public License (GPL), either version 3
 * of the License, or (at your option) any later version.
 *
 * Where@ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. For more details,
 * see the full license at <http://www.gnu.org/licenses/gpl-3.0.en.html>
 *
 */

package org.tlc.whereat.modules.ui;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenu;
import org.robolectric.fakes.RoboMenuItem;
import org.tlc.whereat.BuildConfig;
import org.tlc.whereat.R;
import org.tlc.whereat.activities.OnOffActivity;
import org.tlc.whereat.activities.MapActivity;
import org.tlc.whereat.activities.SettingsActivity;

import static org.assertj.core.api.Assertions.*;
import static org.tlc.whereat.support.ActivityHelpers.*;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)

public class MenuHandlerTest {

    TestActivity a;
    Menu menu;

    private static class TestActivity extends AppCompatActivity {

        MenuHandler mMenu;

        @Override
        protected void onCreate(Bundle state){
            super.onCreate(state);
            mMenu = new MenuHandler(this);

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            return mMenu.create(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            return mMenu.select(item, super::onOptionsItemSelected);
        }
    }

    @Before
    public void setup(){
        a = createActivity(TestActivity.class);
        menu = new RoboMenu(a);
    }

    @Test
    public void create_should_createMenuWithCorrectContents(){
        a.onCreateOptionsMenu(menu);

        assertThat(menu.getItem(0).getItemId()).isEqualTo(R.id.action_map);
        assertThat(menu.getItem(0).getTitle()).isEqualTo(a.getString(R.string.map_activity_title));

        assertThat(menu.getItem(1).getItemId()).isEqualTo(R.id.action_on_off);
        assertThat(menu.getItem(1).getTitle()).isEqualTo(a.getString(R.string.main_activity_title));

        assertThat(menu.getItem(2).getItemId()).isEqualTo(R.id.action_prefs);
        assertThat(menu.getItem(2).getTitle()).isEqualTo(a.getString(R.string.pref_activity_title));
    }

    @Test
    public void selectingActivityFromMenu_should_startThatActivity() {
        a.onOptionsItemSelected(new RoboMenuItem(R.id.action_on_off));
        assertThat(nextActivity(a)).isEqualTo(OnOffActivity.class.getName());

        a.onOptionsItemSelected(new RoboMenuItem(R.id.action_map));
        assertThat(nextActivity(a)).isEqualTo(MapActivity.class.getName());

        a.onOptionsItemSelected(new RoboMenuItem(R.id.action_prefs));
        assertThat(nextActivity(a)).isEqualTo(SettingsActivity.class.getName());
    }
}