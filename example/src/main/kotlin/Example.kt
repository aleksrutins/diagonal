package com.farthergate.diagonal.example

import com.farthergate.diagonal.Subject

class Example : Subject {
    fun add(x: Int, y: Int): Int {
        return x + y
    }

    fun multiply(x: Int, y: Int): Int {
        return x * y
    }
}
