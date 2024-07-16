package com.example.easygo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.easygo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

       // setContentView(R.layout.activity_main);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if(id==R.id.home){
                 replaceFragment(new HomeFragment());
            }

            else if (id==R.id.explore) {
                replaceFragment(new ExploreFragment());
            }

            else if (id==R.id.easychat) {
                replaceFragment(new EasychatFragment());
            }

            else{
                replaceFragment(new ProfileFragment());
            }

//            switch(item.getItemId()){
//                case  R.id.home:
//                    break;
//                case  R.id.profile:
//                    break;
//                case  R.id.setting:
//                    break;
//            }
            return true;
        });

        // Handle navigation based on intent extras
        Intent intent = getIntent();
        if (intent != null) {
            String targetFragment = intent.getStringExtra("target_fragment");
            if (targetFragment != null) {
                switchToFragment(targetFragment);
            }
        }
    }

    private void switchToFragment(@NonNull String fragmentTag) {
        Fragment fragment = null;

        switch (fragmentTag) {
            case "ProfileFragment":
                fragment = new ProfileFragment();
                break;
            // Add other cases if you have more fragments to handle
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.Frame_Layout, fragment); // Assuming you have a FrameLayout with id 'fragment_container'
            fragmentTransaction.addToBackStack(null); // Add to back stack if you want to allow back navigation
            fragmentTransaction.commit();
        }

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Frame_Layout,fragment);
        fragmentTransaction.commit();
    }
}