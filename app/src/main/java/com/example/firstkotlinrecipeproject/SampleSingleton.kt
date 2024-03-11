package com.example.firstkotlinrecipeproject

/*
class SampleSingleton private constructor() {
    companion object {
        val instance: SampleSingleton by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            SampleSingleton()
        }
    }
}*/

object SampleSingleton {

    private var instance: SampleSingleton? = null


    fun getInstance(): SampleSingleton {
        if (instance == null) {
            instance = SampleSingleton
        }
        return instance!!
    }
}