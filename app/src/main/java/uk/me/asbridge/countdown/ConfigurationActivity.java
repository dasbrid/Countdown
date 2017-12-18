package uk.me.asbridge.countdown;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import uk.me.asbridge.countdown.Helpers.LogHelper;
import uk.me.asbridge.countdown.Helpers.SharedPrefs;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

public class ConfigurationActivity extends AppCompatActivity {

    private DatePicker targetDate;
    EditText etMessageBefore;
    EditText etMessageAfter;
    private int mAppWidgetId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        View layout = (View) findViewById(R.id.includedlayout);
        targetDate = (DatePicker) layout.findViewById(R.id.datepicker);
        etMessageBefore = (EditText) layout.findViewById(R.id.etMessageBefore);
        etMessageAfter = (EditText) layout.findViewById(R.id.etMessageAfter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setResult(RESULT_CANCELED);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_countdown, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnSetDateClicked(View v)
    {
        mAppWidgetId = INVALID_APPWIDGET_ID;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(EXTRA_APPWIDGET_ID,
                    INVALID_APPWIDGET_ID);

            // get the date
            int day = targetDate.getDayOfMonth();
            int month = targetDate.getMonth();
            int year =  targetDate.getYear();
            String messageBefore = etMessageBefore.getText().toString();
            String messageAfter = etMessageAfter.getText().toString();
            SharedPrefs.setYear(this, year, mAppWidgetId);
            SharedPrefs.setMonth(this, month, mAppWidgetId);
            SharedPrefs.setDay(this, day, mAppWidgetId);
            SharedPrefs.setMessageBefore(this, messageBefore, mAppWidgetId);
            SharedPrefs.setMessageAfter(this, messageAfter, mAppWidgetId);

            //N.B.: we want to launch this intent to our AppWidgetProvider!
            //Intent firstUpdate = new Intent(context, ConfigurationActivity.class);
            Intent firstUpdate = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, this, CountdownAppWidget.class);
            int[] appWidgetIds = new int[] {mAppWidgetId};
            //firstUpdate.setAction("android.appwidget.action.APPWIDGET_UPDATE");
            firstUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            sendBroadcast(firstUpdate);






            AppWidgetProviderInfo providerInfo = AppWidgetManager.getInstance(
                    getBaseContext()).getAppWidgetInfo(mAppWidgetId);
            String appWidgetLabel = providerInfo.label;

            Intent startService = new Intent(); // new Intent(ConfigurationActivity.this, CountdownAppWidget.UpdateWidgetService.class);
            startService.putExtra(EXTRA_APPWIDGET_ID, mAppWidgetId);
            //startService.setAction("FROM CONFIGURATION ACTIVITY");
            setResult(RESULT_OK, startService);
            //startService(startService);

            finish();
        }
        if (mAppWidgetId == INVALID_APPWIDGET_ID) {
            LogHelper.i("I am invalid", "I am invalid");
            finish();
        }


        /*
        Intent intent = new Intent(this, CountdownAppWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), CountdownAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(intent);
*/

    }
}
