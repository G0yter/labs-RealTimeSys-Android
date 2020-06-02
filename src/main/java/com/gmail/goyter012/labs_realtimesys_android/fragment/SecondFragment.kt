package com.gmail.goyter012.labs_realtimesys_android.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.gmail.goyter012.labs_realtimesys_android.R
import com.gmail.goyter012.labs_realtimesys_android.util.LabsUtil
import com.google.android.material.textfield.TextInputEditText


class SecondFragment : Fragment(), View.OnClickListener {


    private val P = 4.0
    private val points =
        arrayOf(intArrayOf(0, 6), intArrayOf(1, 5), intArrayOf(3, 3), intArrayOf(2, 4))
    private var w1: Double = 0.toDouble()
    private var w2: Double = 0.toDouble()


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_second, container, false)
        view.findViewById<Button>(R.id.calc_button_lab2).setOnClickListener(this)


        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.calc_button_lab2 -> {
                try {
                    val iterations =
                        view?.findViewById<TextInputEditText>(R.id.iterations_textField_lab2)
                            ?.text.toString().toInt()
                    val rate = view?.findViewById<TextInputEditText>(R.id.rate_textField_lab2)
                        ?.text.toString().toDouble()
                    val deadLine =
                        view?.findViewById<TextInputEditText>(R.id.deadline_textField_lab2)?.text.toString().toLong() * 1_000_000_000
                    train(rate, iterations, deadLine)

                } catch (e: NumberFormatException) {
                    LabsUtil(context!!).showAlert(false, null)
                }

            }
        }

    }


    private fun train(rate: Double, iterationsNum: Int, deadline: Long) {
        var y: Double
        var dt: Double
        var iterations = 0
        var done = false
        w1 = 0.toDouble()
        w2 = 0.toDouble()
        val start = System.nanoTime()

        var index = 0
        while (iterations++ < iterationsNum && System.nanoTime() - start < deadline) {

            index %= 4

            y = points[index][0] * w1 + points[index][1] * w2

            if (isDone()) {
                done = true
                break
            }

            dt = P - y
            w1 += dt * points[index][0] * rate
            w2 += dt * points[index][1] * rate
            index++
        }

        if (done) {
            val execTimeMcs = (System.nanoTime() - start) / 1000

            LabsUtil(context!!).showAlert(
                true,
                String.format(
                    "Trained successfully!\n" +
                            "w1 = %-6.3f w2 = %-6.3f\n" +
                            "Execution time: %d mcs" +
                            "\nIterations: %d", w1, w2, execTimeMcs, iterations
                )
            )


        } else {
            var reason = "failed!"
            reason += if (iterations >= iterationsNum) "\nMore iterations needed!" else "\nMore time is required!"
            LabsUtil(context!!).showAlert(
                true,
                reason
            )
        }

    }


    private fun isDone(): Boolean {
        return (P < points[0][0] * w1 + points[0][1] * w2
                && P < points[1][0] * w1 + points[1][1] * w2
                && P > points[2][0] * w1 + points[2][1] * w2
                && P > points[3][0] * w1 + points[3][1] * w2)
    }


    companion object {
        fun newInstance(): SecondFragment = SecondFragment()
    }

}