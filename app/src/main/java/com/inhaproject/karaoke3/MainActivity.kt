package com.inhaproject.karaoke3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.inhaproject.karaoke3.databinding.ActivityMainBinding
import com.inhaproject.karaoke3.ui.home.HomeFragment
import com.inhaproject.karaoke3.ui.mypage.MyPageFragment
import com.inhaproject.karaoke3.ui.mysong.BasketFragment
import com.inhaproject.karaoke3.ui.community.CommunityFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun initBottomNavigation() {

            binding.navView.run {
                setOnItemSelectedListener { item ->
                    when(item.itemId) {
                        R.id.navigation_home -> replaceFragment(HomeFragment())
                        R.id.navigation_community -> replaceFragment(CommunityFragment())
                        R.id.navigation_basket -> replaceFragment(BasketFragment())
                        R.id.navigation_mypage -> replaceFragment(MyPageFragment())
                    }
                    true
                 }
                selectedItemId = R.id.navigation_home
            }
        }
    }




