package com.rutins.aleks.diagonal

interface Subject {
    class NotFoundException(name: String) : Exception("Subject not found: $name")
}

inline fun <reified Needle : Subject> Array<Subject>.find()
    = this.find { Needle::class.java.isAssignableFrom(it.javaClass) } as Needle?

inline fun <reified Needle : Subject> Array<Subject>.require()
    = this.find<Needle>() ?: throw Subject.NotFoundException(Needle::class.java.simpleName)