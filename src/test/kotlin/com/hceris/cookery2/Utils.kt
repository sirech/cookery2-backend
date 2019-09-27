package com.hceris.cookery2

import arrow.core.Either
import arrow.core.Option
import arrow.core.Some
import strikt.api.Assertion
import strikt.assertions.isA
import java.io.InputStream
import java.nio.charset.Charset

// I need to get the classLoader from this class, otherwise asStream will get an NPE
object Utils

fun String.asStream(): InputStream {
    return Utils.javaClass.classLoader.getResourceAsStream(this)
}

fun InputStream.readTextAndClose(charset: Charset = Charsets.UTF_8): String {
    return this.bufferedReader(charset).use { it.readText() }
}

inline fun <reified T, reified U> Assertion.Builder<Either<U, T>>.isRight() =
        isA<Either.Right<T>>()
                .get { b }

inline fun <reified T> Assertion.Builder<Option<T>>.isSome() =
        isA<Some<T>>()
                .get { t }
