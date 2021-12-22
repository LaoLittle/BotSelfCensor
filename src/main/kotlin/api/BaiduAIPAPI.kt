package org.laolittle.plugin.api

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.laolittle.plugin.Config
import org.laolittle.plugin.Config.client_id
import org.laolittle.plugin.Config.client_secret
import org.laolittle.plugin.Data.access_token
import org.laolittle.plugin.utils.KtorHttpUtil
import org.laolittle.plugin.utils.KtorHttpUtil.post

object BaiduAIPAPI {
    private suspend inline fun String.normalCensor(): JsonObject {
        val body = "access_token=$access_token&text=$this"
        return body.post(KtorHttpUtil.censorUrl, "application/x-www-form-urlencoded")
    }

    private suspend inline fun String.sampleCensor(): JsonObject {
        val body = buildJsonObject {
            put("strategyId", 1)
            put("text", this@sampleCensor)
        }
        return body.toString().post(KtorHttpUtil.censorUrl, "application/json", true)
    }

    suspend fun getToken(): String {
        var url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials"
        url += "&client_id=$client_id"
        url += "&client_secret=$client_secret"
        val token = "{}".post(url, "")["access_token"]
        if (token != null)
            return token.toString()
        else throw NoSuchElementException("获取失败！")
    }

    suspend fun String.censor() =
        when (Config.verifyType) {
            Config.ApiType.Normal -> {
                this.normalCensor()
            }
            Config.ApiType.Sample -> {
                this.sampleCensor()
            }
        }["conclusionType"] ?: throw NoSuchElementException("获取失败！")
}