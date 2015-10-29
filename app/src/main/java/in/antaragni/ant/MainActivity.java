package in.antaragni.ant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import in.antaragni.ant.fragments.AboutFragment;
import in.antaragni.ant.fragments.ContactFragment;
import in.antaragni.ant.fragments.EventFragment;
import in.antaragni.ant.fragments.HomeFragment;
import in.antaragni.ant.fragments.MapFragment;
import in.antaragni.ant.fragments.ScheduleFragment;

public class MainActivity extends AppCompatActivity
{
  protected static int HOME = 1;
  protected static int SCHEDULE = 2;
  protected static int EVENTS = 3;
  protected static int MAP = 4;
  protected static int CONTACT = 5;
  protected static int ABOUT = 6;

  //save our header or result
  private Drawer result = null;
  private Fragment f;
  private Toolbar mtoolbar;
  private GCMClientManager pushClientManager;
  String PROJECT_NUMBER = "138444406408";
  public static String EXTRA_ACTION = "action";
  public Snackbar mSnackBar;
  public AlertDialog alertDialog;
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Handle Toolbar
    mtoolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(mtoolbar);

    //Create the drawer
    result = new DrawerBuilder(this)
      //this layout have to contain child layouts
      .withRootView(R.id.drawer_container)
      .withToolbar(mtoolbar)
      .withTranslucentStatusBar(false)
      .withActionBarDrawerToggleAnimated(true)
      .addDrawerItems(
        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(HOME),
        new PrimaryDrawerItem().withName(R.string.drawer_item_schedule).withIcon(FontAwesome.Icon.faw_calendar).withIdentifier(SCHEDULE),
        new PrimaryDrawerItem().withName(R.string.drawer_item_events).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(EVENTS),
        new PrimaryDrawerItem().withName(R.string.drawer_item_maps).withIcon(FontAwesome.Icon.faw_map_marker).withIdentifier(MAP),
        new PrimaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_users).withIdentifier(CONTACT),
        new PrimaryDrawerItem().withName(R.string.drawer_item_about).withIcon(FontAwesome.Icon.faw_book).withIdentifier(ABOUT))
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
              getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
              f = HomeFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            } else if (drawerItem.getIdentifier() == SCHEDULE)
            {
              showloading();
              getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
              f = ScheduleFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            } else if (drawerItem.getIdentifier() == EVENTS)
            {
              getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
              f = EventFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            } else if (drawerItem.getIdentifier() == MAP)
            {
              getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
              f = MapFragment.newInstance(null);
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            } else if (drawerItem.getIdentifier() == CONTACT)
            {
              getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
              f = ContactFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
            } else if (drawerItem.getIdentifier() == ABOUT)
            {
              getSupportActionBar().setTitle(((Nameable) drawerItem).getNameRes());
              f = AboutFragment.newInstance(getResources().getString(((Nameable) drawerItem).getNameRes()));
              getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
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

    gcmregister();

    Intent intent = getIntent();
    String VenueName = intent.getStringExtra(EXTRA_ACTION);
    if (VenueName != null)
      startMap(VenueName, true);
  }

  public void startMap(String v, boolean backarrow)
  {
    if (backarrow)//if backarrow is true then show back arrow
    {
      result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    startMap(v);
    if (backarrow)//if back arrow then disable drawer
      mtoolbar.setNavigationOnClickListener(new View.OnClickListener()
      {
        @Override
        public void onClick(View view)
        {
          finish();
        }
      });
  }

  public void startMap(String v)
  {
    result.setSelection(3);
    getSupportActionBar().setTitle("Map");
    f = MapFragment.newInstance(v);
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
  }

  public void showSnackBar(CharSequence text, int length)
  {
    Snackbar.make(findViewById(R.id.main_screen), text, length).setAction("Action", null).show();
  }

  public void gcmregister()
  {
    pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
    pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler()
    {

      @Override
      public void onSuccess(String registrationId, boolean isNewRegistration)
      {

        // SEND async device registration to your back-end server
        // linking user with device registration id
        // POST https://my-back-end.com/devices/register?user_id=123&device_id=abc
      }

      @Override
      public void onFailure(String ex)
      {
        super.onFailure(ex);
        // If there is an error registering, don't just keep trying to register.
        // Require the user to click a button again, or perform
        // exponential back-off when retrying.
      }
    });
  }

  public void showSnackBarIndefinite(String s)
  {
    mSnackBar = Snackbar.make(findViewById(R.id.main_screen), s, Snackbar.LENGTH_INDEFINITE);
    mSnackBar.show();// Don’t forget to show!
  }

  public void dismissSnackBar()
  {
    mSnackBar.dismiss();
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
    } else
    {
      super.onBackPressed();
    }
  }

  public void showloading()
  {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

    // set dialog message
    alertDialogBuilder
      .setMessage("Loading ...")
      .setCancelable(false);

    // create alert dialog
    alertDialog = alertDialogBuilder.create();

    // show it
    alertDialog.show();
  }

  public void dismissloading()
  {
    alertDialog.dismiss();
  }
}
