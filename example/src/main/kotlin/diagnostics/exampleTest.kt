
package com.farthergate.diagonal.example.diagnostics

import com.farthergate.diagonal.describe
import com.farthergate.diagonal.example.Example

val exampleTest = describe<Example> { subject ->
    it("can add") {
        (subject.add(2, 2) == 4).expect("Could not add 2 and 2")
        (subject.add(3, 2) == 5).expect("Could not add 3 and 2")
    }
    it("can multiply") {
        (subject.multiply(2, 2) == 4).expect("Could not multiply 2 and 2")
        (subject.multiply(3, 2) == 6).expect("Could not multiply 3 and 2")
    }
}