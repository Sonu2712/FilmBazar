package com.film.bazar.appusercore.model

data class AppLanguage(
    @JvmField val id : Int,
    @JvmField val langId: String,
    @JvmField val name : String
) {

    fun isHindi() : Boolean{
        return this == HINDI
    }

    fun isEnglish() : Boolean{
        return this == ENGLISH
    }

    fun isGujrati() : Boolean{
        return this == GUJARATI
    }

    override fun toString(): String {
        return langId
    }

    companion object{
        fun getLangId(langId: String) : AppLanguage {
            return when(langId){
                EnglishLanguage -> ENGLISH
                HindiLanguage -> HINDI
                GujaratiLanguage -> GUJARATI
                else -> ENGLISH
            }
        }

        private const val EnglishLanguage = "en"
        private const val HindiLanguage = "hi"
        private const val GujaratiLanguage = "gu"

        @JvmField
        val ENGLISH = AppLanguage(0, EnglishLanguage, "English")
        val HINDI = AppLanguage(1, HindiLanguage, "Hindi")
        val GUJARATI = AppLanguage(2, GujaratiLanguage, "Gujarati")
    }
}