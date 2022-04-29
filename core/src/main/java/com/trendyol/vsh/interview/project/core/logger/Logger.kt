package com.trendyol.vsh.interview.project.core.logger

interface Logger {

    fun v(tag: String?, msg: String, tr: Throwable? = null)

    fun d(tag: String?, msg: String, tr: Throwable? = null)

    fun i(tag: String?, msg: String, tr: Throwable?)

    fun w(tag: String?, msg: String, tr: Throwable?)

    fun e(tag: String?, msg: String, tr: Throwable?)
}