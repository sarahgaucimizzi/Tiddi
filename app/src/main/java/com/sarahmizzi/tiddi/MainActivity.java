
package com.sarahmizzi.tiddi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionMenu;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.Slider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Create colors array list
    static ArrayList<Integer> colors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise ColorPicker and bars
        final ColorPicker picker = (ColorPicker) findViewById(R.id.picker);
        SaturationBar saturationBar = (SaturationBar) findViewById(R.id.saturationbar);
        ValueBar valueBar = (ValueBar) findViewById(R.id.valuebar);

        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);
        picker.setShowOldCenterColor(false);

        // Initialise Seek bar
        final Slider seekBar = (Slider) findViewById(R.id.seekbar);

        // Remove FAB animation
        final com.github.clans.fab.FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.menu);
        floatingActionMenu.setIconAnimated(false);

        // Set onCheckedChanged for checkbox
        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    seekBar.setEnabled(false);
                }
                else{
                    seekBar.setEnabled(true);
                }
            }
        });

        // Set onClick for menu_item1
        com.github.clans.fab.FloatingActionButton clearQueue = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_item1);
        clearQueue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Clear the Queue, start over
                colors = new ArrayList<>();
                if (colors.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Queue cleared.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set onClick for menu_item2
        com.github.clans.fab.FloatingActionButton startQueue = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_item2);
        startQueue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Pass array list and diameter to next Activity to create fullscreen circle view
                int diameter = -1;
                boolean isFullscreen = false;

                if(checkBox.isChecked()){
                    diameter = -1;
                    isFullscreen = true;
                }
                else{
                    diameter = seekBar.getValue();
                    isFullscreen = false;
                }

                if(diameter == 0){
                    Toast.makeText(MainActivity.this, "Circle size cannot be 0.", Toast.LENGTH_LONG).show();
                }
                else if (colors.isEmpty()){
                    Toast.makeText(MainActivity.this, "Add at least one colour to the queue.", Toast.LENGTH_LONG).show();
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putIntegerArrayList("color_list", colors);
                    bundle.putInt("dimensions", diameter);
                    bundle.putBoolean("isFullscreen", isFullscreen);
                    Intent intent = new Intent(MainActivity.this, FullscreenActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        // Set onClick for menu_item3
        com.github.clans.fab.FloatingActionButton addToQueue = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_item3);
        addToQueue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Add user selected color to array list
                if (colors.add(picker.getColor())) {
                    Toast.makeText(MainActivity.this, "Colour added to queue.", Toast.LENGTH_SHORT).show();
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
            new MaterialDialog.Builder(this)
                    .title(R.string.title)
                    .content(R.string.content)
                    .positiveText(R.string.done)
                    .show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
