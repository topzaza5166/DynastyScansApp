package com.dynasty.dynastyscansapp.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(text: String?) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
}

fun View.hide() {
    val anim = TranslateAnimation(0f, 0f, 0f, -200f).apply {
        duration = 500
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })
    }
    startAnimation(anim)
}

fun View.show() {
    val anim = TranslateAnimation(0f, 0f, -200f, 0f).apply {
        duration = 500
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {

            }

            override fun onAnimationStart(animation: Animation?) {
                visibility = View.VISIBLE
            }
        })
    }
    startAnimation(anim)
}