package com.example.syy;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar-ды орнату
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tazasy");

        // Әдепкі фрагментті жүктеу
        loadFragment(new HomeFragment());
    }

    // Менюны жүктеу (оң жақ жоғарғы бұрыштағы 3 нүкте)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    // Меню элементі басылғанда әрекет жасау
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                loadFragment(new HomeFragment());
                return true;
            case R.id.nav_account:
                loadFragment(new AccountFragment());
                return true;
            case R.id.nav_settings:
                loadFragment(new SettingsFragment());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
