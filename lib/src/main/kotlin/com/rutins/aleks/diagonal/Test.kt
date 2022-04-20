package com.rutins.aleks.diagonal

class ExpectationException(message: String) : Exception("Expectation not met: $message")
typealias TestConstructor<T> = Pair<Class<T>, (Array<Subject>, Runner) -> Test>

abstract class Test(val subjects: Array<Subject>, val runner: Runner) {
    abstract fun run()
    var errors = mutableListOf<Pair<String, Throwable>>()
    inline fun <reified Needle : Subject> find(): Needle? = subjects.find()
    inline fun <reified Needle : Subject> require(): Needle = subjects.require()
    fun Any?.expect(message: String) = if(this != null) Unit else throw ExpectationException(message)
    fun Boolean.expect(message: String) = if(this) Unit else throw ExpectationException(message)

    fun it(doesWhat: String, fn: () -> Unit) {
        with(runner.logger) {
            logVerbose("  it ${primaryColor(doesWhat)}")
        }
        try {
            fn()
            with(runner.logger) {
                log("  ${successColor("SUCCESS")}")
            }
        } catch (err: Throwable) {
            with(runner.logger) {
                log("  ${failedColor("FAILED")}")
                log("    ${failedColor("Error:")} ${err.message}")
            }
            errors.add(doesWhat to err)
        }
    }
}

inline fun <reified T: Subject> describe(crossinline test: Test.(T) -> Unit): TestConstructor<T>{
    return T::class.java to { subjects, runner ->
        object : Test(subjects, runner) {
            override fun run() = this.test(require())
        }
    }
}