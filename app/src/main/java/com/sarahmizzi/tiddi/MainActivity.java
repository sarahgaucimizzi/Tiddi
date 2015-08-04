
package com.sarahmizzi.tiddi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.*;
import com.github.clans.fab.FloatingActionMenu;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Integer> colors = new ArrayList<>();
    static boolean isClosed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ColorPicker picker = (ColorPicker) findViewById(R.id.picker);
        SaturationBar saturationBar = (SaturationBar) findViewById(R.id.saturationbar);
        ValueBar valueBar = (ValueBar) findViewById(R.id.valuebar);

        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);
        picker.setShowOldCenterColor(false);

        final com.github.clans.fab.FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu);
        floatingActionMenu.setIconAnimated(false);

        com.github.clans.fab.FloatingActionButton clearQueue = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_item1);
        clearQueue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                colors = new ArrayList<>();
                if(colors.isEmpty()){
                    Toast.makeText(MainActivity.this, "Queue cleared.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        com.github.clans.fab.FloatingActionButton startQueue = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_item2);
        startQueue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(MainActivity.this, FullscreenActivity.class);
                activityChangeIntent.putIntegerArrayListExtra("color_list", colors);
                MainActivity.this.startActivity(activityChangeIntent);
            }
        });

        com.github.clans.fab.FloatingActionButton addToQueue = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_item3);
        addToQueue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                if(colors.add(picker.getColor())){
                    Toast.makeText(MainActivity.this, "Colour added to queue. " + colors.get(0), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
