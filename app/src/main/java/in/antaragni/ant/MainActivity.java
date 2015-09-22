package in.antaragni.ant;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.KeyboardUtil;

import in.antaragni.ant.fragments.ContactFragment;
import in.antaragni.ant.fragments.EventFragment;
import in.antaragni.ant.fragments.FoodFragment;
import in.antaragni.ant.fragments.MapFragment;
import in.antaragni.ant.fragments.ScheduleFragment;

public class MainActivity extends AppCompatActivity {
  protected int SCHEDULE = 1;
  protected int EVENTS = 2;
  protected int MAP = 3;
  protected int FOOD = 4;
  protected int CONTACT = 5;
  //save our header or result
  private Drawer result = null;
  private Fragment f;
  private GCMClientManager pushClientManager;
  String PROJECT_NUMBER = "138444406408";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Handle Toolbar
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //Create the drawer
    result = new DrawerBuilder(this)
      //this layout have to contain child layouts
      .withRootView(R.id.drawer_container)
      .withToolbar(toolbar)
      .withTranslucentStatusBar(false)
      .withActionBarDrawerToggleAnimated(true)
      .addDrawerItems(
        new PrimaryDrawerItem().withName(R.string.drawer_item_schedule).withIcon(FontAwesome.Icon.faw_calendar).withIdentifier(SCHEDULE),
        new PrimaryDrawerItem().withName(R.string.drawer_item_events).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(EVENTS),
        new PrimaryDrawerItem().withName(R.string.drawer_item_maps).withIcon(FontAwesome.Icon.faw_map_marker).withIdentifier(MAP),
        new PrimaryDrawerItem().withName(R.string.drawer_item_food).withIcon(FontAwesome.Icon.faw_cutlery).withIdentifier(FOOD),
        new PrimaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_users).withIdentifier(CONTACT))
      .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
        @Override
        public boolean onItemClick(AdapterView<?> parent, View view, int position,
                                   long id, IDrawerItem drawerItem) {
          //check if the drawerItem is set.
          //there are different reasons for the drawerItem to be null
          //--> click on the header
          //--> click on the footer
          //those items don't contain a drawerItem
          if (drawerItem != null) {
            if (drawerItem.getIdentifier() == SCHEDULE) {
              getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
              f = ScheduleFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            } else if (drawerItem.getIdentifier() == EVENTS) {
              getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
              f = EventFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            } else if (drawerItem.getIdentifier() == MAP) {
              getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
              f = MapFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            } else if (drawerItem.getIdentifier() == FOOD) {
              getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
              f = FoodFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            } else if (drawerItem.getIdentifier() == CONTACT) {
              getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
              f = ContactFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            }
          }
          return false;
        }
      })
      .withOnDrawerListener(new Drawer.OnDrawerListener() {
        @Override
        public void onDrawerOpened(View drawerView) {
          KeyboardUtil.hideKeyboard(MainActivity.this);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
        }
      })
      .withFireOnInitialOnClick(true)
      .withSavedInstance(savedInstanceState)
      .withShowDrawerOnFirstLaunch(true)
      .build();

    //react on the keyboard
    result.keyboardSupportEnabled(this, true);

    gcmregister();
  }

  public void gcmregister()
  {
    pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
    pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
      @Override
      public void onSuccess(String registrationId, boolean isNewRegistration) {
        Toast.makeText(MainActivity.this, registrationId,
          Toast.LENGTH_SHORT).show();
        Log.wtf("gcm token", registrationId);
        // SEND async device registration to your back-end server
        // linking user with device registration id
        // POST https://my-back-end.com/devices/register?user_id=123&device_id=abc
      }

      @Override
      public void onFailure(String ex) {
        super.onFailure(ex);
        // If there is an error registering, don't just keep trying to register.
        // Require the user to click a button again, or perform
        // exponential back-off when retrying.
      }
    });

  }
  public void showSnackBar(String s)
  {
    Snackbar.make(findViewById(R.id.main_screen), s, Snackbar.LENGTH_SHORT).show(); // Don’t forget to show!
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    //add the values which need to be saved from the drawer to the bundle
    outState = result.saveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onBackPressed() {
    //handle the back press :D close the drawer first and if the drawer is closed close the activity
    if (result != null && result.isDrawerOpen()) {
      result.closeDrawer();
      super.onBackPressed();
    }
  }

}
