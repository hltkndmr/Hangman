package com.example.gamehang.functions

import android.content.Context
import android.content.res.Resources
import com.example.gamehang.screens.selectedLanguage

fun getWordListFromXml(context: Context): List<String> {
    val res: Resources = context.resources
    if (selectedLanguage == "Türkçe"){
        return res.getStringArray(res.getIdentifier("word_listtr", "array", context.packageName)).toList()
    }
    else
        return res.getStringArray(res.getIdentifier("word_listen", "array", context.packageName)).toList()
}


fun getRandomWord(wordList: List<String>): String {
    return wordList.random()
}


