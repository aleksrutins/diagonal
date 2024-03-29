package com.farthergate.diagonal

interface Subject {
    class NotFoundException(name: String) : Exception("Subject not found: $name")
}

inline fun <reified Needle : Subject> Array<Subject>.find()
    = this.find { it is Needle } as Needle?

inline fun <reified Needle : Subject> Array<Subject>.require()
    = this.find<Needle>() ?: throw Subject.NotFoundException(Needle::class.java.simpleName)