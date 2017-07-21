package com.avito.konveyor.util

import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.mockito.Mockito


@Suppress("HasPlatformType") // unspecified return type is required to allow compiler to infer type in caller code
internal fun <T> Is(value: T) = org.hamcrest.core.Is.`is`(value)

internal inline fun <reified T : Any> any(instance: T) = Mockito.any(T::class.java) ?: instance

inline fun <reified T: Any> instanceOf(): Matcher<Any> = CoreMatchers.instanceOf(T::class.java)