package com.gmail.goyter012.labs_realtimesys_android.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.gmail.goyter012.labs_realtimesys_android.R

class LabsUtil(private val context: Context) {

    fun showAlert(isShowDescription: Boolean, res: String?) {
        val resultAlert = AlertDialog.Builder(context)
        if (isShowDescription)
            resultAlert.setMessage(
                String.format(
                    context.getString(R.string.res_alert_message),
                    res
                )
            )
        else
            resultAlert.setMessage(
                String.format(
                    context.getString(R.string.error_alert_message),
                    res
                )
            )
        resultAlert.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        resultAlert.show()
    }
}