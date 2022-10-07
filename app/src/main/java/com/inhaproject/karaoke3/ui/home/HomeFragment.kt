package com.inhaproject.karaoke3.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.FragmentHomeBinding

    class HomeFragment : Fragment() {

        lateinit var binding: FragmentHomeBinding

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentHomeBinding.inflate(inflater, container, false)

            return binding.root
        }
    }
