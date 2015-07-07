package in.antaragni.antaragni;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.KeyboardUtil;

import in.antaragni.antaragni.fragments.ContactFragment;
import in.antaragni.antaragni.fragments.EventFragment;
import in.antaragni.antaragni.fragments.FoodFragment;
import in.antaragni.antaragni.fragments.InformalFragment;
import in.antaragni.antaragni.fragments.MapFragment;
import in.antaragni.antaragni.fragments.NotificationFragment;
import in.antaragni.antaragni.fragments.ResultFragment;
import in.antaragni.antaragni.fragments.ScheduleFragment;

public class MainActivity extends AppCompatActivity
{

  protected int HOME = 1;
  protected int SCHEDULE = 2;
  protected int EVENTS = 3;
  protected int MAP = 4;
  protected int FOOD = 5;
  protected int INFORMAL = 6;
  protected int RESULT = 7;
  protected int CONTACT = 8;
  //save our header or result
  private Drawer result = null;
  private Fragment f;
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
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
        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(HOME),
        new PrimaryDrawerItem().withName(R.string.drawer_item_schedule).withIcon(FontAwesome.Icon.faw_calendar).withIdentifier(SCHEDULE),
        new PrimaryDrawerItem().withName(R.string.drawer_item_events).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(EVENTS),
        new PrimaryDrawerItem().withName(R.string.drawer_item_maps).withIcon(FontAwesome.Icon.faw_map_marker).withIdentifier(MAP),
        new PrimaryDrawerItem().withName(R.string.drawer_item_food).withIcon(FontAwesome.Icon.faw_cutlery).withIdentifier(FOOD),
        new PrimaryDrawerItem().withName(R.string.drawer_item_register_informals).withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(INFORMAL),
        new PrimaryDrawerItem().withName(R.string.drawer_item_results).withIcon(FontAwesome.Icon.faw_bar_chart).withIdentifier(RESULT),
        new PrimaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_users).withIdentifier(CONTACT))
      .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener()
      {
        @Override
        public boolean onItemClick(AdapterView<?> parent, View view, int position,
                                   long id, IDrawerItem drawerItem)
        {
          //check if the drawerItem is set.
          //there are different reasons for the drawerItem to be null
          //--> click on the header
          //--> click on the footer
          //those items don't contain a drawerItem
          if (drawerItem != null)
          {
            if (drawerItem.getIdentifier() == HOME)
            {
              try
              {
                getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
              }
              catch (NullPointerException e)
              {
                //TODO : write a lot of Log statements.
              }
              //ignore the NotificationFragment and it's layout it's just to showcase the handle with an keyboard
              f = NotificationFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
              showSnackBar("Home");
            }
            else if (drawerItem.getIdentifier() == SCHEDULE)
            {
              f = ScheduleFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
              showSnackBar("Schedule");
            }
            else if (drawerItem.getIdentifier() == EVENTS)
            {
              f = EventFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
              showSnackBar("Events");
            }
            else if (drawerItem.getIdentifier() == MAP)
            {
              f = MapFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
              showSnackBar("Map");
            }
            else if (drawerItem.getIdentifier() == FOOD)
            {
              f = FoodFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
              showSnackBar("Food Courts");
            }
            else if (drawerItem.getIdentifier() == INFORMAL)
            {
              f = InformalFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
              showSnackBar("Informals");
            }
            else if (drawerItem.getIdentifier() == RESULT)
            {
              f = ResultFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
              showSnackBar("Results");
            }
            else if (drawerItem.getIdentifier() == CONTACT)
            {
              f = ContactFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
              showSnackBar("Contact");
            }
          }
          return false;
        }
      })
      .withOnDrawerListener(new Drawer.OnDrawerListener()
      {
        @Override
        public void onDrawerOpened(View drawerView)
        {
          KeyboardUtil.hideKeyboard(MainActivity.this);
        }

        @Override
        public void onDrawerClosed(View drawerView)
        {
        }

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset)
        {
        }
      })
      .withFireOnInitialOnClick(true)
      .withSavedInstance(savedInstanceState)
      .withShowDrawerOnFirstLaunch(true)
      .build();

    //react on the keyboard
    result.keyboardSupportEnabled(this, true);

  }

  public void showSnackBar(String s)
  {
    Snackbar.make(findViewById(R.id.main_screen), s, Snackbar.LENGTH_SHORT).show(); // Don’t forget to show!
  }

  @Override
  protected void onSaveInstanceState(Bundle outState)
  {
    //add the values which need to be saved from the drawer to the bundle
    outState = result.saveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onBackPressed()
  {
    //handle the back press :D close the drawer first and if the drawer is closed close the activity
    if (result != null && result.isDrawerOpen())
    {
      result.closeDrawer();
    }
    else
    {
      super.onBackPressed();
    }
  }

}
