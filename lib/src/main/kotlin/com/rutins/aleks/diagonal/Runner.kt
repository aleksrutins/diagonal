package com.rutins.aleks.diagonal

class Runner(private val tests: Array<TestConstructor<*>>, val logger: Logger) {
    fun runAll(subjects: Array<Subject>) {
        logger.logVerbose("Running all tests")
        for (test in tests) {
            run(test, subjects)
        }
    }
    fun run(test: TestConstructor<*>, subjects: Array<Subject>) {
        logger.logVerbose("describe " + logger.primaryColor(test.first.simpleName))
        val instance = test.second(subjects, this)
        try {
            instance.run()
            logger.log(logger.successColor("SUCCESS"))
        } catch(err: Throwable) {
            logger.log(logger.failedColor("FAILED"))
            logger.log("  ${err.message}")
        } finally {
            if (instance.errors.size > 0) {
                logger.log(logger.failedColor("FAILED in sub-clauses:"))
                for(err in instance.errors) {
                    logger.log("  it ${logger.primaryColor(err.first)}")
                    logger.log("    ${err.second.message}")
                }
            }
        }
    }
}

interface Logger {
    fun log(what: String)
    fun useColor() = true
    fun useVerbose() = true
    fun logVerbose(what: String) = if(useVerbose()) log(what) else Unit
    fun primaryColor(what: String) = if(useColor()) "\u001b[34m$what\u001b[0m" else what
    fun successColor(what: String) = if(useColor()) "\u001b[32$what\u001b[0m" else what
    fun failedColor(what: String) = if(useColor()) "\u001b[31m$what\u001b[0m" else what
}