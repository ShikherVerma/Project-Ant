package in.antaragni.antaragni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

public class MainActivity extends AppCompatActivity
{

  //save our header or result
  private Drawer result = null;

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
        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home),
        new PrimaryDrawerItem().withName(R.string.drawer_item_schedule).withIcon(FontAwesome.Icon.faw_calendar),
        new PrimaryDrawerItem().withName(R.string.drawer_item_events).withIcon(FontAwesome.Icon.faw_eye),
        new PrimaryDrawerItem().withName(R.string.drawer_item_maps).withIcon(FontAwesome.Icon.faw_map_marker),
        new PrimaryDrawerItem().withName(R.string.drawer_item_food).withIcon(FontAwesome.Icon.faw_cutlery),
        new PrimaryDrawerItem().withName(R.string.drawer_item_register_informals).withIcon(FontAwesome.Icon.faw_gamepad),
        new PrimaryDrawerItem().withName(R.string.drawer_item_results).withIcon(FontAwesome.Icon.faw_bar_chart),
        new PrimaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_users)
      )
      .withSavedInstance(savedInstanceState)
      .build();
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
