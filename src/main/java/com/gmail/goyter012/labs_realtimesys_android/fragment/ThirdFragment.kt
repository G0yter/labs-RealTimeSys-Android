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
import java.util.*

class ThirdFragment : Fragment(), View.OnClickListener {

    private lateinit var random: Random


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_third, container, false)
        view?.findViewById<Button>(R.id.calc_button_lab3)?.setOnClickListener(this)
        return view
    }


    private fun makePairs(
        survProb: DoubleArray,
        population: Array<IntArray>
    ): Array<IntArray>? {
        val pairs = Array(population.size) { IntArray(2) }
        val parentsNum = survProb.size / 2
        val parents = IntArray(parentsNum)
        var maxIndex: Int
        var max: Double
        for (i in 0 until parentsNum) {
            max = survProb[0]
            maxIndex = 0
            for (j in 1 until survProb.size) {
                if (survProb[j] > max) {
                    max = survProb[j]
                    maxIndex = j
                }
            }
            survProb[maxIndex] = (-1).toDouble()
            parents[i] = maxIndex
        }
        var i = 0
        while (i < pairs.size) {
            pairs[i][0] = parents[random.nextInt(parents.size)]
            pairs[i][1] = parents[random.nextInt(parents.size)]
            if (pairs[i][0] != pairs[i][1]) {
                i++
            }
        }
        return pairs
    }

    private fun crossover(pair1: IntArray, pair2: IntArray): IntArray? {
        val bound = 1 + random.nextInt(3)
        val child = IntArray(pair1.size)
        for (i in child.indices) {
            if (i < bound) {
                child[i] = pair1[i]
            } else {
                child[i] = pair2[i]
            }
        }
        return child
    }

    private fun newPopulation(
        parentPairs: Array<IntArray>,
        population: Array<IntArray>
    ): Array<IntArray>? {
        val newPopulation =
            Array(population.size) { IntArray(4) }
        for (i in population.indices) {
            newPopulation[i] = crossover(
                population[parentPairs[i][0]],
                population[parentPairs[i][1]]
            )!!
        }
        return newPopulation
    }


    private fun firstPopulation(y: Int): Array<IntArray>? {
        val firstPopulation =
            Array(2 + random.nextInt(y - 1)) { IntArray(4) }
        for (i in firstPopulation.indices) {
            for (j in firstPopulation[0].indices) {
                firstPopulation[i][j] = random.nextInt(y / 2)
            }
        }
        return firstPopulation
    }


    private fun fitness(
        population: Array<IntArray>,
        abcd: IntArray,
        y: Int
    ): IntArray? {
        val dts = IntArray(population.size)
        for (i in population.indices) {
            for (j in population[0].indices) {
                dts[i] += population[i][j] * abcd[j]
            }
            dts[i] = Math.abs(dts[i] - y)
        }
        return dts
    }

    private fun solution(array: IntArray): Int {
        var index = -1
        for (i in array.indices) {
            if (array[i] == 0) {
                index = i
                break
            }
        }
        return index
    }


    private fun survivalProbability(deltas: IntArray): DoubleArray? {
        var cummProb = 0.0
        val surv = DoubleArray(deltas.size)
        for (i in deltas.indices) {
            cummProb += 1.toDouble() / deltas[i]
        }
        for (i in deltas.indices) {
            surv[i] = 1.toDouble() / deltas[i] / cummProb
        }
        return surv
    }

    private fun populate(
        deltas: IntArray,
        previousPopulation: Array<IntArray>
    ): Array<IntArray>? {
        val survProb: DoubleArray = survivalProbability(deltas)!!
        val parentPairs: Array<IntArray> = makePairs(survProb, previousPopulation)!!
        return newPopulation(parentPairs, previousPopulation)
    }


    private fun mean(array: DoubleArray): Double {
        var mean = 0.0
        for (i in array.indices) {
            mean += array[i]
        }
        mean /= array.size.toDouble()
        return mean
    }


    private fun mutate(
        population: Array<IntArray>,
        y: Int,
        mutationParam: Double
    ) {
        for (i in population.indices) {
            for (j in population[0].indices) {
                val coin = random.nextDouble()
                if (coin <= mutationParam) population[i][j] = random.nextInt(y + 1)
            }
        }
    }

    private fun findRoots(
        a: Int,
        b: Int,
        c: Int,
        d: Int,
        y: Int,
        mutationParam: Double
    ): IntArray? {
        var population: Array<IntArray>? = firstPopulation(y)
        val abcd = intArrayOf(a, b, c, d)
        var index: Int
        var dts: IntArray?
        while (true) {
            dts = fitness(population!!, abcd, y)
            if (solution(dts!!).also { index = it } != -1) {
                break
            } else {
                val avgSurvivalOld = mean(survivalProbability(dts)!!)
                val newPopulation: Array<IntArray>? = populate(dts, population)
                val avgSurvivalNew =
                    mean(survivalProbability(fitness(newPopulation!!, abcd, y)!!)!!)
                if (avgSurvivalOld < avgSurvivalNew) {
                    population = newPopulation
                } else {
                    mutate(population, y, mutationParam)
                }
            }
        }
        return population!![index]
    }


    companion object {
        fun newInstance(): ThirdFragment = ThirdFragment()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.calc_button_lab3 -> {
                random = Random()
                try {
                    val a =
                        view?.findViewById<TextInputEditText>(R.id.a_textField_lab3)?.text.toString()
                            .toInt()
                    val b =
                        view?.findViewById<TextInputEditText>(R.id.b_textField_lab3)?.text.toString()
                            .toInt()
                    val c =
                        view?.findViewById<TextInputEditText>(R.id.c_textField_lab3)?.text.toString()
                            .toInt()
                    val d =
                        view?.findViewById<TextInputEditText>(R.id.d_textField_lab3)?.text.toString()
                            .toInt()
                    val y =
                        view?.findViewById<TextInputEditText>(R.id.y_textField_lab3)?.text.toString()
                            .toInt()

                    val mutation =
                        view?.findViewById<TextInputEditText>(R.id.mutation_textField_lab3)?.text.toString()
                            .toDouble()
                    val start = System.nanoTime()
                    val x1234 = findRoots(a, b, c, d, y, mutation)
                    val execTimeMls = (System.nanoTime() - start) / 1_000_000;

                    LabsUtil(context!!).showAlert(
                        true, String.format(
                            "x1 = %d\nx2 = %d\nx3 = %d\nx4 = %d\nExecution time: %d ms",
                            x1234?.get(0), x1234?.get(1), x1234?.get(2), x1234?.get(3), execTimeMls
                        )
                    )

                } catch (e: NumberFormatException) {
                    LabsUtil(context!!).showAlert(false, null)
                }
            }
        }
    }


}