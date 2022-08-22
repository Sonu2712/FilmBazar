package com.film.bazar.data.drawermenu

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.film.app.core.prefs.StringPreference
import com.film.annotations.AppMenu
import com.film.annotations.UserLanguage
import com.film.bazar.appuser.repository.UserManager
import com.film.bazar.domain.drawermenu.AppMenus
import com.film.bazar.domain.drawermenu.MenuDataSourceRepository
import com.film.bazar.appusercore.model.AppLanguage
import io.reactivex.rxjava3.core.Observable
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class MenuDataSourceRepositoryImpl @Inject constructor(
    val userManager: UserManager,
    val preferences: SharedPreferences,
    private val gson: Gson,
    @AppMenu val appMenuPref: StringPreference,
    @param:UserLanguage private val languagePreference: StringPreference,
    val context: Context
): MenuDataSourceRepository {
    val userType
    get() = userManager.getUser()?.userType?.label ?: "OU"

    override fun getMenuItems(): Observable<AppMenus> {
        val jsonFileString = getJsonFromAssets(userType)
        val usersAppMenus: LocalAppMenu = gson.fromJson(jsonFileString, LocalAppMenu::class.java)
        usersAppMenus.transform1(AppLanguage.getLangId(languagePreference.get()))
        return Observable.just(usersAppMenus.transform1(AppLanguage.getLangId(languagePreference.get())))
    }

    override fun getAllAppMenu(userType : String): Observable<AppMenus> {
        return getMenuItems()
    }

    override fun saveAppMenusLocal(appMenus: AppMenus){
        preferences.edit().putString(MENUJSON,gson.toJson(appMenus)).apply()
        appMenuPref.set(gson.toJson(appMenus))
    }

    override fun getSavedMenus(): AppMenus? {
        val appMenus = try {
            gson.fromJson(appMenuPref.get(), AppMenus::class.java)
        } catch (e: Exception){
            appMenuPref.delete()
            null
        }
        return appMenus
    }

    override fun getMenus(): AppMenus? {
        val menus = preferences.getString(MENUJSON,"")
        val appMenus = try {
            gson.fromJson(menus, AppMenus::class.java)
        } catch (e: Exception){
            null
        }
        return appMenus
    }

    override fun saveBottomMenu(identifier: List<String>): Observable<String> {
       return Observable.just("")
    }

    private fun getJsonFromAssets(userType: String): String? {
        val fileName = when {
            userType == "T" -> "trading_user_menu.json"
            userType == "N" -> "non_trading_user_menu.json"
            userType == "M" -> "mf_user_menu.json"
            userType == "G" -> "guest_user_menu.json"
            else -> "open_user_menu.json"
        }

        val jsonString: String
        jsonString = try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }
    companion object{
        const val MENUJSON ="menujson"

    }
}