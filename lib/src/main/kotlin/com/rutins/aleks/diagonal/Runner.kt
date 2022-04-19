package com.rutins.aleks.diagonal

fun runAll(tests: Array<(Array<Subject>) -> Test>, subjects: Array<Subject>) {
    for (test in tests) {
        val instance = test(subjects)
        instance.run()
    }
}