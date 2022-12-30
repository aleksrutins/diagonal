package com.rutins.aleks.diagonal
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class Runner(private val tests: Array<TestConstructor<*>>, val logger: Logger) {
    private fun createMessage(messageName: String, data: String)
        = "/* log:tag:diagonal */ {\"Message\":\"$messageName\",\"Data\":$data}";

    @Serializable
    private data class TestReport(val name: String, val success: Boolean)

    private fun String.toJSON(): String {
        return "\"${this.replace("\"", "\\\"")}\""
    }

    fun log(message: String) {
        logger.log(createMessage("logLine", message.toJSON()))
    }

    private fun clearTestList() {
        logger.log(createMessage("clearTests", "null"))
    }

    private fun enumerateTest(testName: String) {
        logger.log(createMessage("enumerateTest", testName.toJSON()))
    }

    private fun notifyTestStart(testName: String) {
        logger.log(createMessage("testStart", testName.toJSON()))
    }

    private fun statusReport(testName: String, success: Boolean) {
        logger.log(createMessage("statusReport", Json.encodeToString(TestReport(testName, success))))
    }
    fun runAll(subjects: Array<Subject>) {
        clearTestList()
        for (test in tests) {
            enumerateTest(test.first.simpleName)
        }
        for (test in tests) {
            run(test, subjects)
        }
    }
    fun run(test: TestConstructor<*>, subjects: Array<Subject>) {
        notifyTestStart(test.first.simpleName)
        val instance = test.second(subjects, this)
        try {
            instance.run()
        } catch(err: Throwable) {
            log("ERROR:  ${err.message}")
            statusReport(test.first.simpleName, false)
        } finally {
            if (instance.errors.size > 0) {
                log(logger.failedColor("FAILED in sub-clauses:"))
                for(err in instance.errors) {
                    log("  it ${logger.primaryColor(err.first)}")
                    log("    ${err.second.message}")
                }
                statusReport(test.first.simpleName, false)
            } else {
                statusReport(test.first.simpleName, true)
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