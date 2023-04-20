package com.ayush.diceapp

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class MainActivity : AppCompatActivity() {
    var spChooseSide: Spinner? = null
    var arrChooseDiceSide = arrayOf<Int?>(4, 6, 8, 10, 12, 20)
    var edCustomDiceSide: TextInputEditText? = null
    var btAdd: MaterialButton? = null
    var btRollOnce: MaterialButton? = null
    var btRollTwice: MaterialButton? = null
    var tvRollOneText: TextView? = null
    var tvRollTwoText: TextView? = null
    var arrListViewArray: ArrayList<String>? = null
    var lvSavedList: ListView? = null
    var lvMain: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindID()
    }

    private fun bindID() {
        spChooseSide = findViewById(R.id.spChooseSide)
        edCustomDiceSide = findViewById(R.id.edCustomDiceSide)
        btAdd = findViewById(R.id.btAdd)
        btRollOnce = findViewById(R.id.btRollOnce)
        btRollTwice = findViewById(R.id.btRollTwice)
        tvRollOneText = findViewById(R.id.tvRollOneText)
        tvRollTwoText = findViewById(R.id.tvRollTwoText)
        lvSavedList = findViewById(R.id.lvSavedList)
        lvMain = findViewById(R.id.lvMain)
        //initate Shared Pref
        SharedPrefManager.init(this@MainActivity)
        setSpinnerData(arrChooseDiceSide)
        arrListViewArray = ArrayList()
        btAdd!!.setOnClickListener(View.OnClickListener {
            //adding new array element to spinner
            val strNewCustomSide = edCustomDiceSide!!.text.toString().trim { it <= ' ' }
            if (!strNewCustomSide.equals("", ignoreCase = true)) {
                val arrNewSide = arrayOfNulls<Int>(arrChooseDiceSide.size + 1)
                for (i in arrChooseDiceSide.indices) {
                    //Copy element from old array to new array
                    arrNewSide[i] = arrChooseDiceSide[i]
                }
                arrNewSide[arrNewSide.size - 1] = strNewCustomSide.toInt()
                //sorting array
                Arrays.sort(arrNewSide)
                //copy new array to old array
                arrChooseDiceSide = Arrays.copyOf(arrNewSide, arrNewSide.size)
                setSpinnerData(arrChooseDiceSide)
                Toast.makeText(
                    this@MainActivity,
                    "Your custom Dice $strNewCustomSide is added to dropdown", Toast.LENGTH_SHORT
                ).show()

                edCustomDiceSide!!.setText("")
            }
        })
        btRollOnce!!.setOnClickListener(View.OnClickListener {
            tvRollTwoText!!.setText("")
            val stID = spChooseSide!!.getSelectedItem().toString()
            val d = Dice(stID.toInt())
            tvRollTwoText!!.setVisibility(View.GONE)
            diceResultBeforeAndAfter(d, 1)
        })
        btRollTwice!!.setOnClickListener(View.OnClickListener {
            val stID = spChooseSide!!.getSelectedItem().toString()
            val d = Dice(stID.toInt())
            tvRollTwoText!!.setVisibility(View.VISIBLE)
            diceResultBeforeAndAfter(d, 2)
        })
    }

    private fun setSpinnerData(arrChooseSide: Array<Int?>) {
        val spinnerArrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, arrChooseSide)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line) // The drop down view
        spChooseSide!!.adapter = spinnerArrayAdapter
    }

    private fun diceResultBeforeAndAfter(dice: Dice, i: Int) {
        dice.roll();
        //Roll first dice
        var sideUpAfterRoll: Int = dice.getSideUp()
        if (sideUpAfterRoll == 0)
        {
            sideUpAfterRoll = sideUpAfterRoll+1;
        }
        tvRollOneText!!.text = sideUpAfterRoll.toString()
        //if user clicked on two time roll
        if (i == 2) {

            var getSide = dice.getSides();
            val roundRandom = Math.round(Math.random() * 10).toInt()

          var newSide =   Math.round((roundRandom * getSide / 10).toFloat())
           if(newSide == 0)
           {
               newSide = 1;
           }
            tvRollTwoText!!.text = newSide.toString() ;
        }
        //save data to shared pref
        saveDataToSharePref()
    }

    private fun saveDataToSharePref() {
        val stOne = "First Dice " + tvRollOneText!!.text.toString()
        val stTwo = tvRollTwoText!!.text.toString().trim { it <= ' ' }
        arrListViewArray!!.add(stOne)
        if (!stTwo.equals("", ignoreCase = true)) {
            val stNew = "Second Dice $stTwo"
            arrListViewArray!!.add(stNew)
        }
        //Using Gson i am saving data
        val gson = Gson()
        //Saving string to Gson
        val json = gson.toJson(arrListViewArray)
        //pass object into sharePref
        SharedPrefManager.putString("newSaveObject", json)
        dataFromSharedPref
    }//getting array from gson and saved

    //set array to listview
    //getting data from gson
    private val dataFromSharedPref: Unit
        private get() {
            //getting data from gson
            val gson = Gson()
            val json: String? = SharedPrefManager.getString("newSaveObject", "")
            if (json!!.isEmpty()) {
                Toast.makeText(this@MainActivity, "There is something error", Toast.LENGTH_LONG)
                    .show()
            } else {
                val type = object : TypeToken<List<String?>?>() {}.type
                //getting array from gson and saved
                val arrNewSavedList = gson.fromJson<List<String>>(json, type)
                //set array to listview
                val mHistory =
                    ArrayAdapter(this, android.R.layout.simple_list_item_1, arrNewSavedList)
                lvSavedList!!.adapter = mHistory
            }
        }


}