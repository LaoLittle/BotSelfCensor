package org.laolittle.plugin.utils

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.laolittle.plugin.Data

object KtorHttpUtil {
    private val client = HttpClient(OkHttp)

    suspend fun String.post(url: String, type: String, useSample: Boolean = false): JsonObject {
        val responseData = client.post<String> {
            url(url)
            header("Content-Type", type)
            if (useSample)
                header("Cookie", Data.cookie)
            body = this@post
        }
        return Json.parseToJsonElement(responseData).jsonObject
    }


}