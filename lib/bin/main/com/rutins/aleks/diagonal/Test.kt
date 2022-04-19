package com.rutins.aleks.diagonal

abstract class Test(val subjects: Array<Subject>) {
    abstract fun run()
    inline fun <reified Needle : Subject> find(): Needle? = subjects.find()
    inline fun <reified Needle : Subject> require(): Needle = subjects.require()
}