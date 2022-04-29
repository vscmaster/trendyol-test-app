package com.trendyol.vsh.interview.project.core.logger

import android.util.Log
import javax.inject.Inject

class TrendyolLogger @Inject constructor() : Logger {
    override fun v(tag: String?, msg: String, tr: Throwable?) {
        Log.v(tag, msg, tr)
    }

    override fun d(tag: String?, msg: String, tr: Throwable?) {
        Log.d(tag, msg, tr)
    }

    override fun i(tag: String?, msg: String, tr: Throwable?) {
        Log.i(tag, msg, tr)
    }

    override fun w(tag: String?, msg: String, tr: Throwable?) {
        Log.w(tag, msg, tr)
    }

    override fun e(tag: String?, msg: String, tr: Throwable?) {
        Log.e(tag, msg, tr)
    }
}