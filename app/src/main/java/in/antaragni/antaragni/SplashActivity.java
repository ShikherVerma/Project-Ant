package in.antaragni.antaragni;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.romainpiel.titanic.library.Titanic;
import com.romainpiel.titanic.library.TitanicTextView;

public class SplashActivity extends Activity
{
  private static String TAG = SplashActivity.class.getName();
  private static long SLEEP_TIME = 9;	// Sleep for some time
  private KenBurnsView mImg;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    mImg = (KenBurnsView) findViewById(R.id.img);
    setContentView(R.layout.activity_splash);

    LinearLayout hiddenLayout = (LinearLayout) findViewById(R.id.hidden);
    TransitionDrawable transition = (TransitionDrawable) hiddenLayout.getBackground();
    transition.startTransition(8000);

    // Start timer and launch main activity
    IntentLauncher launcher = new IntentLauncher();
    launcher.start();

    TitanicTextView tv = (TitanicTextView) findViewById(R.id.my_text_view);
    // set fancy typeface
    tv.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));

    // start animation
    new Titanic().start(tv);
    tv.setShadowLayer(20, 0, 0, Color.YELLOW);

  }

  private class IntentLauncher extends Thread {

    @Override
    /**
     * Sleep for some time and than start new activity.
     */
    public void run() {
      try {
        // Sleeping
        Thread.sleep(SLEEP_TIME*1000);
      } catch (Exception e) {
        Log.e(TAG, e.getMessage());
      }

      // Start main activity
      Intent intent = new Intent(SplashActivity.this, MainActivity.class);
      SplashActivity.this.startActivity(intent);
      SplashActivity.this.finish();
    }
  }
}
