package com.gmail.goyter012.labs_realtimesys_android.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.gmail.goyter012.labs_realtimesys_android.R
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.pow
import kotlin.math.sqrt


class FirstFragment : Fragment(), View.OnClickListener {


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.fragment_first,
            container,
            false
        )

        view.findViewById<Button>(R.id.calc_button_lab1).setOnClickListener(this)

        return view
    }

    private fun showAlert(isSuccess: Boolean, res: String?) {
        val resultAlert = AlertDialog.Builder(this@FirstFragment.context!!)
        if (isSuccess)
            resultAlert.setMessage(
                String.format(
                    context!!.getString(R.string.res_alert_message),
                    res
                )
            )
        else
            resultAlert.setMessage(
                String.format(
                    context!!.getString(R.string.error_alert_message),
                    res
                )
            )
        resultAlert.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        resultAlert.show()
    }


    private fun doFerma(num: Int, deadline: Long): IntArray {
        val result = IntArray(2)
        var k = 0
        var x: Int
        var y: Double
        var converged = true
        val start = System.nanoTime()

        do {
            x = (sqrt(num.toDouble()) + k).toInt()
            y = sqrt(x.toDouble().pow(2.0) - num)
            k++
            if (System.nanoTime() - start > deadline) {
                converged = false
                break
            }
        } while (y % 1 != 0.0)

        if (converged) {
            result[0] = (x + y).toInt()
            result[1] = (x - y).toInt()
            return result
        } else {
            showAlert(false, null)
            return intArrayOf(-1, -1)
        }
    }

    private fun isPrimeNumb(n: Int): Boolean {
        var i = 2
        while (i <= sqrt(n.toDouble())) {
            if (n % i == 0)
                return false
            i++
        }
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.calc_button_lab1 -> {

                try {
                    val num = view?.findViewById<TextInputEditText>(R.id.number_textField_lab1)
                        ?.text.toString().toInt()
                    val deadline =
                        view?.findViewById<TextInputEditText>(R.id.deadline_textField_lab1)?.text.toString().toLong() * 1_000_000_000

                    if (isPrimeNumb(num)) showAlert(
                        true,
                        String.format(
                            context?.getString(R.string.res_alert_message)!!,
                            "The number is prime!"
                        )
                    )
                    else if (num % 2 == 0) showAlert(
                        true,
                        String.format(
                            context?.getString(R.string.res_alert_message)!!,
                            "The number is even!"
                        )
                    )
                    else {
                        val results = doFerma(num, deadline)
                        if (results[0] != -1) {
                            showAlert(
                                true,
                                String.format(
                                    context?.getString(R.string.res_alert_message)!!,
                                    String.format(
                                        "A = %d, B = %d\nA * B = %d",
                                        results[0],
                                        results[1],
                                        num
                                    )
                                )
                            )
                        }
                    }


                } catch (e: NumberFormatException) {
                    showAlert(false, null)
                }

            }

        }
    }


    companion object {
        fun newInstance(): FirstFragment = FirstFragment()
    }
}