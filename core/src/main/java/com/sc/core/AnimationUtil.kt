package com.sc.core

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils

object AnimationUtil {

    fun animate(view: View, animId: Int, context: Context) {
        try {
            AnimationUtils.loadAnimation(context, animId).let {

                it.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(animation: Animation) {
                        view.visibility = View.VISIBLE
                    }

                    override fun onAnimationEnd(animation: Animation) {

                    }

                    override fun onAnimationRepeat(animation: Animation) {

                    }

                })

                view.apply {
                    clearFocus()
                    clearAnimation()
                    startAnimation(it)

                }

            }

        } catch (error: Exception) {

        }
    }

}