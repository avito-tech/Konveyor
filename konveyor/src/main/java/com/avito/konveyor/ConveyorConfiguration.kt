package com.avito.konveyor

import com.avito.konveyor.validation.ValidationPolicy

object konveyorConfiguration {

    internal var policy: ValidationPolicy = DefaultPolicy()

    fun setConfigurationPolicy(policy: ValidationPolicy) {
        this.policy = policy
    }

    private class DefaultPolicy : ValidationPolicy {

        override var validateEagerly: Boolean = false

    }

}