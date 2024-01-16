package com.example.travel_taipei.util

import android.content.Context
import android.content.res.Configuration
import com.example.travel_taipei.MainApplication
import com.example.travel_taipei.enum.CountryCode
import java.util.Locale

fun initAppLanguage(context: Context) {
    val countryCode: CountryCode

    if (getPrefStringValue(context, LANGUAGE_CODE, "").isNullOrBlank()) {
        countryCode = getDefaultCountryCode(context)

        saveLanguageChange(context, countryCode)
    } else {
        countryCode = getAppCountryCode(context)
    }

    setAppLanguage(context, countryCode.ordinal)
}

fun setAppLanguage(context: Context, index: Int) {
    val countryCode = getCountryCodeByIndex(index)
    val locale = Locale(countryCode.languageCode, countryCode.name)
    val configuration: Configuration? = context.resources.configuration

    Locale.setDefault(locale)

    if (configuration != null) {
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
    }

    val newContext = context.createConfigurationContext(configuration!!)

    MainApplication.appResources = newContext.resources

    saveLanguageChange(context, countryCode)
}

fun saveLanguageChange(context: Context, countryCode: CountryCode) {
    setPrefStringValue(context, LANGUAGE_CODE, countryCode.languageCode)
    setPrefStringValue(context, COUNTRY_CODE, countryCode.name)
}

fun getCountryCodeByIndex(index: Int): CountryCode =
    CountryCode.entries.getOrNull(index) ?: CountryCode.TW

fun getDefaultCountryCode(context: Context): CountryCode {
    val countryCode: CountryCode
    val sysLanguageCode = context.resources.configuration.locales[0].language

    countryCode = if (sysLanguageCode == "zh") {
        val sysCountryCode = context.resources.configuration.locales[0].country

        CountryCode.entries.find { it.languageCode == sysLanguageCode && it.name == sysCountryCode }
            ?: CountryCode.TW
    } else {
        CountryCode.entries.find { it.languageCode == sysLanguageCode } ?: CountryCode.TW
    }

    return countryCode
}

fun getAppCountryCode(context: Context): CountryCode {
    val countryCode: CountryCode
    val appLanguageCode = getPrefStringValue(context, LANGUAGE_CODE, "")
    val appCountryCode = getPrefStringValue(context, COUNTRY_CODE, "")

    countryCode =
        CountryCode.entries.find { it.languageCode == appLanguageCode && it.name == appCountryCode }
            ?: CountryCode.TW

    return countryCode
}

fun getApiLang(context: Context): String = when (getAppCountryCode(context)) {
    CountryCode.TW -> "zh-tw"
    CountryCode.CN -> "zh-cn"
    CountryCode.US -> "en"
    CountryCode.JP -> "ja"
    CountryCode.KR -> "ko"
    CountryCode.ES -> "es"
    CountryCode.ID -> "id"
    CountryCode.TH -> "th"
    CountryCode.VN -> "vi"
}
