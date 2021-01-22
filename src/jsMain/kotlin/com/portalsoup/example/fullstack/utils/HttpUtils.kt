package com.portalsoup.example.fullstack.utils

import com.portalsoup.example.fullstack.jsdefs.AxiosRequestConfig

data class AxiosGet(
    override var url: String?,
    override var headers: Any?
): AxiosRequestConfig {
    override var method: String?
        get() = "get"
        set(value) {}
}