package com.ayush.diceapp
class Dice {
    private  var diceName: String
    private var sides: Int
    private var sideUp: Int

    // Default Constructor
    //
    constructor() {
        sides = 6
        diceName = "d6"
        sideUp =  roll()
    }

    // Constructor with one argument ie. Sides
    //
    constructor(sides: Int) {
        this.sides = sides
        diceName = getDiceNameFor(sides)
        sideUp= roll()
    }

    // Constructor with two argument ie. Sides and Name
    //
    constructor(sides: Int, diceName: String) {
        this.sides = sides
        this.diceName = diceName
        sideUp= roll()
    }

    // This method rolls the dice and set a random number to side up
    //
    fun  roll():Int {
        val roundRandom = Math.round(Math.random() * 10).toInt()
        if (roundRandom == 0) {
            return 1
        } else {
            return Math.round((roundRandom * sides / 10).toFloat())
        }


    }

    // Getters or Accessors
    //
    fun getSides(): Int {
        return sides
    }
    fun getSideUp(): Int {
        return sideUp
    }
    fun diceName(): String {
        return diceName
    }

    fun setSideUp(sideUp: Int) {
        this.sideUp = sideUp
    }

    // Setters or Mutators
    //
    fun setSides(sides: Int) {
        this.sides = sides
        diceName = getDiceNameFor(sides)
    }

    fun setName(diceName: String) {
        this.diceName = diceName
    }

    private fun getDiceNameFor(sides: Int): String {
        return "d" + sides.also { diceName = it.toString() }
    }

    // Get highest value
    //
    fun showHighestValue() {
        println("Highest value of dice: " + sides)
    }
}