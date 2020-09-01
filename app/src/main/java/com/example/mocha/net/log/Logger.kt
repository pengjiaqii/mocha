package com.safframework.http.interceptor

import android.text.TextUtils
import android.util.Log
import com.example.mocha.net.log.LoggingInterceptor
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Request
import okio.Buffer
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class Logger {

    companion object {

        private const val JSON_INDENT = 3
        private const val MAX_STRING_LENGTH = 4000
        private const val MAX_LONG_SIZE = 120
        private const val N = "\n"
        private const val T = "\t"

        private const val TOP_LEFT_CORNER = '╔'
        private const val BOTTOM_LEFT_CORNER = '╚'
        private const val DOUBLE_DIVIDER = "═════════════════════════════════════════════════"
        private val TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER
        private val BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER
        private val LINE_SEPARATOR = System.getProperty("line.separator")

        private fun String.isLineEmpty() = isEmpty() || N == this || T == this || this.trim { it <= ' ' }.isEmpty()

        private fun getDoubleSeparator(hideVerticalLine: Boolean = false): String = if (hideVerticalLine) LINE_SEPARATOR else LINE_SEPARATOR

        /**
         * 支持超长日志的打印
         */
        private fun printLog(tag: String, logString: String, logLevel: LoggingInterceptor.LogLevel) {

            if (logString.length > MAX_STRING_LENGTH) {

                var i = 0

                while (i < logString.length) {

                    if (i + MAX_STRING_LENGTH < logString.length)
                        log(tag, logString.substring(i, i + MAX_STRING_LENGTH), logLevel)
                    else
                        log(tag, logString.substring(i, logString.length), logLevel)
                    i += MAX_STRING_LENGTH
                }
            } else
                log(tag, logString, logLevel)
        }

        private fun log(tag: String, msg: String, logLevel: LoggingInterceptor.LogLevel = LoggingInterceptor.LogLevel.INFO) {

            when (logLevel) {

                LoggingInterceptor.LogLevel.ERROR -> Log.e(tag, msg)
                LoggingInterceptor.LogLevel.WARN -> Log.w(tag, msg)
                LoggingInterceptor.LogLevel.INFO -> Log.i(tag, msg)
                LoggingInterceptor.LogLevel.DEBUG -> Log.d(tag, msg)
            }
        }

        @JvmStatic
        fun printJsonRequest(builder: LoggingInterceptor.Builder, request: Request) {

            val tag = builder.getTag(true)
            val hideVerticalLine = builder.hideVerticalLineFlag
            val requestLogLevel = builder.requestLogLevel
            val responseLogLevel = builder.responseLogLevel

            val sb = StringBuilder()
            sb.append("  ").append(LINE_SEPARATOR).append(TOP_BORDER).append(LINE_SEPARATOR)
            sb.append(getRequest(request, hideVerticalLine))

            if (request.method() != "GET") { // get请求不需要body

                val requestBody = if (hideVerticalLine) {
                    " " + LINE_SEPARATOR + " Body:" + LINE_SEPARATOR
                } else {
                    " " + LINE_SEPARATOR + "║ Body:" + LINE_SEPARATOR
                }

                val bodyString = bodyToString(request).split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                sb.append(requestBody + logLines(bodyString, hideVerticalLine))

            } else {

                val header = request.headers().toString()

                if (header.isLineEmpty()) {

                    sb.append(LINE_SEPARATOR)
                }
            }

            sb.append(BOTTOM_BORDER)

            log(tag, sb.toString(), requestLogLevel)
        }

        @JvmStatic
        fun printFileRequest(builder: LoggingInterceptor.Builder, request: Request) {

            val tag = builder.getTag(true)
            val requestLogLevel = builder.requestLogLevel

            val sb = StringBuilder()
            sb.append("  ").append(LINE_SEPARATOR).append(TOP_BORDER).append(LINE_SEPARATOR)
            sb.append(getRequest(request))

            val requestBody = " " + LINE_SEPARATOR

            val binaryBodyString = binaryBodyToString(request).split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            sb.append(requestBody + logLines(binaryBodyString))
            sb.append(BOTTOM_BORDER)

            log(tag, sb.toString(), requestLogLevel)
        }

        @JvmStatic
        fun printJsonResponse(builder: LoggingInterceptor.Builder, chainMs: Long, isSuccessful: Boolean,
                              code: Int, headers: String, bodyString: String, segments: HttpUrl) {

            val tag = builder.getTag(false)
            val hideVerticalLine = builder.hideVerticalLineFlag
            val responseLogLevel = builder.responseLogLevel

            val sb = StringBuilder()
            sb.append("  ").append(LINE_SEPARATOR).append(TOP_BORDER).append(LINE_SEPARATOR)
            sb.append(getResponse(headers, chainMs, code, isSuccessful, segments, hideVerticalLine))

            val responseBody = if (hideVerticalLine) {
                " " + LINE_SEPARATOR + " Body:" + LINE_SEPARATOR
            } else {
                "║ " + LINE_SEPARATOR + "║ Body:" + LINE_SEPARATOR
            }

            val bodyString = getJsonString(bodyString).split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            sb.append(responseBody + logLines(bodyString, hideVerticalLine))

            sb.append(BOTTOM_BORDER)

            printLog(tag, sb.toString(), responseLogLevel)
        }

        @JvmStatic
        fun printFileResponse(builder: LoggingInterceptor.Builder, chainMs: Long, isSuccessful: Boolean,
                              code: Int, headers: String, segments: HttpUrl) {

            val tag = builder.getTag(false)
            val responseLogLevel = builder.responseLogLevel

            val sb = StringBuilder()
            sb.append("  ").append(LINE_SEPARATOR).append(TOP_BORDER).append(LINE_SEPARATOR)
            sb.append(getResponse(headers, chainMs, code, isSuccessful, segments))
            sb.append(BOTTOM_BORDER)

            log(tag, sb.toString(), responseLogLevel)
        }

        /**
         * 获取请求体
         */
        private fun getRequest(request: Request, hideVerticalLine: Boolean = false): String {

            val header = request.headers().toString()

            if (hideVerticalLine) {

                return " URL: " + request.url() + getDoubleSeparator(hideVerticalLine) + " Method: @" + request.method() + getDoubleSeparator(hideVerticalLine) +
                        if (header.isLineEmpty()) " " else " Headers:" + LINE_SEPARATOR + dotHeaders(header, hideVerticalLine)
            } else {

                return "║ URL: " + request.url() + getDoubleSeparator() + "║ Method: @" + request.method() + getDoubleSeparator() +
                        if (header.isLineEmpty()) "║ " else "║ Headers:" + LINE_SEPARATOR + dotHeaders(header)
            }
        }

        /**
         * 获取返回数据
         */
        private fun getResponse(header: String, tookMs: Long, code: Int, isSuccessful: Boolean,
                                segments: HttpUrl, hideVerticalLine: Boolean = false): String {

            var segmentString: String?

            if (hideVerticalLine) {

                segmentString = segments.toString()

                return (if (!TextUtils.isEmpty(segmentString)) segmentString + " - " else "") + "is success : " + isSuccessful + " - " + "Received in: " + tookMs + "ms" + getDoubleSeparator(hideVerticalLine) + " Status Code: " +
                        code + getDoubleSeparator(hideVerticalLine) +
                        if (header.isLineEmpty()) " " else " Headers:" + LINE_SEPARATOR + dotHeaders(header, hideVerticalLine)
            } else {

                segmentString = segments.toString()

                return (if (!TextUtils.isEmpty(segmentString)) segmentString + " - " else "") + "is success : " + isSuccessful + " - " + "Received in: " + tookMs + "ms" + getDoubleSeparator() + "║ Status Code: " +
                        code + getDoubleSeparator() +
                        if (header.isLineEmpty()) "║ " else "║ Headers:" + LINE_SEPARATOR + dotHeaders(header)
            }
        }

//        private fun slashSegments(segments: HttpUrl): String {
//            val segmentString = StringBuilder()
//            for (segment in segments) {
//                segmentString.append("/").append(segment)
//            }
//            return segmentString.toString()
//        }

        private fun dotHeaders(header: String, hideVerticalLine: Boolean = false): String {
            val headers = header.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val builder = StringBuilder()

            if (headers != null && headers.isNotEmpty()) {
                for (item in headers) {

                    if (hideVerticalLine) {
                        builder.append(" - ").append(item).append("\n")
                    } else {
                        builder.append("║ - ").append(item).append("\n")
                    }
                }
            } else {

                builder.append(LINE_SEPARATOR)
            }

            return builder.toString()
        }

        private fun logLines(lines: Array<String>, hideVerticalLine: Boolean = false): String {
            val sb = StringBuilder()
            for (line in lines) {
                val lineLength = line.length
                for (i in 0..lineLength / MAX_LONG_SIZE) {
                    val start = i * MAX_LONG_SIZE
                    var end = (i + 1) * MAX_LONG_SIZE
                    end = if (end > line.length) line.length else end

                    if (hideVerticalLine) {
                        sb.append(" " + line.substring(start, end)).append(LINE_SEPARATOR)
                    } else {
                        sb.append("║ " + line.substring(start, end)).append(LINE_SEPARATOR)
                    }

                }
            }

            return sb.toString()
        }

        private fun bodyToString(request: Request): String {
            try {
                val copy = request.newBuilder().build()
                val buffer = Buffer()
                if (copy.body() == null) return ""

                copy.body()?.writeTo(buffer)
                return getJsonString(buffer.readUtf8())
            } catch (e: IOException) {
                return "{\"err\": \"" + e.message + "\"}"
            }
        }

        private fun binaryBodyToString(request: Request): String {

            val copy = request.newBuilder().build()
            val requestBody = copy.body()
            if (requestBody == null) return ""

            var buffer: String?
            val contentType = requestBody.contentType()
            if (contentType != null) {
                buffer = "Content-Type: " + contentType.toString()
            } else {
                buffer = ""
            }

            if (requestBody.contentLength() > 0) {
                buffer += LINE_SEPARATOR + "Content-Length: " + requestBody.contentLength()
            }

            if (contentType != null) {

                val contentTypeString = contentType.toString()
                if (contentTypeString.contains("application/x-www-form-urlencoded")) {
                    buffer += LINE_SEPARATOR
                    if (requestBody is FormBody) {
                        val size = requestBody.size()
                        for (i in 0 until size) {
                            buffer += requestBody.name(i) + "=" + requestBody.value(i) + "&"
                        }

                        buffer = buffer.take(buffer.length - 1)
                    }
                }
            }

            return buffer
        }

        @JvmStatic
        fun getJsonString(msg: String): String {

            var message: String
            try {
                if (msg.startsWith("{")) {
                    val jsonObject = JSONObject(msg)
                    message = jsonObject.toString(JSON_INDENT)
                } else if (msg.startsWith("[")) {
                    val jsonArray = JSONArray(msg)
                    message = jsonArray.toString(JSON_INDENT)
                } else {
                    message = msg
                }
            } catch (e: JSONException) {
                message = msg
            }

            return message
        }
    }

}