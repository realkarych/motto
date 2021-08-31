package github.karchx.motto.views.tools.managers

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class Vibrator {

    fun setLowVibration(context: Context) {
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            //deprecated in API 26
            v.vibrate(50)
        }
    }
}