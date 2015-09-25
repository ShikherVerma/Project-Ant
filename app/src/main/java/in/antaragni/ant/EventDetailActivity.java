package in.antaragni.ant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class EventDetailActivity extends AppCompatActivity
{

  public static final String EXTRA_NAME = "Event_name";

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_event_detail);

    Intent intent = getIntent();
    final String EventName = intent.getStringExtra(EXTRA_NAME);

    final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    CollapsingToolbarLayout collapsingToolbar =
      (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    collapsingToolbar.setTitle(EventName);

    loadBackdrop();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    switch (item.getItemId())
    {
      case android.R.id.home:
        finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void loadBackdrop()
  {
    final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
    Glide.with(this).load(getEventDrawable()).centerCrop().into(imageView);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  public static int getEventDrawable() {
        return R.drawable.bheed;
    }
}
