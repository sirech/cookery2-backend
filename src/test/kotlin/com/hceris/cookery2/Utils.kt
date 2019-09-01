package com.hceris.cookery2

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
