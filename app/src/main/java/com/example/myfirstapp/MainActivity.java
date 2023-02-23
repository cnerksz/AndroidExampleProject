package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;

import com.example.myfirstapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String kullanici=intent.getStringExtra("key");
        Bundle bundle=new Bundle();
        bundle.putString("key",kullanici);
        Fragment hesap=new HesapFragment();
        Fragment musteri=new MusteriFragment();
        Fragment alinan=new StokFragment();
        Fragment bakiye=new BakiyeFragment();
        hesap.setArguments(bundle);
        musteri.setArguments(bundle);
        alinan.setArguments(bundle);
        bakiye.setArguments(bundle);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(hesap);
        binding.bottomNavigationView.setOnItemSelectedListener(item->{


switch (item.getItemId()){
    case R.id.hesap:
        replaceFragment(hesap);
        break;
    case R.id.musteri:
        replaceFragment(musteri);
        break;
    case R.id.alinan:
        replaceFragment(alinan);
        break;
    case R.id.bakiye:
        replaceFragment(bakiye);
        break;
}
return true;
    });



    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    }
