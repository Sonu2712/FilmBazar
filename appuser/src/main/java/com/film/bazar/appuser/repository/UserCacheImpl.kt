package com.film.bazar.appuser.repository

import com.google.gson.Gson
import com.film.bazar.appuser.helper.LoginPrefStore
import com.film.bazar.appuser.helper.UserPrefStore
import com.film.bazar.appuser.model.LoginInfo
import com.film.bazar.appusercore.model.User
import com.film.bazar.appusercore.model.UserType

class UserCacheImpl internal constructor(
    private val dataStore: UserDataStoreImpl,
    private val gson: Gson
) : UserCache {
  private val prefStore: LoginPrefStore = dataStore.loginPrefStore()
  private val userStore: UserPrefStore = dataStore.userPrefStore

  override fun dataStore(): UserDataStore {
    return dataStore
  }

  override fun saveUser(user: User) {
    prefStore.userIdentity.set(user.userCode)
    prefStore.clientCode.set(user.clientCode)
      userStore.userInfo.set(gson.toJson(user))
  }

  override fun getUser(): User? {
    return when {
        userStore.userInfo.isSet -> gson.fromJson(userStore.userInfo.get(), User::class.java)
      else -> null
    }
  }

  override fun clearUser() {
    prefStore.clear()
    userStore.clear()
    dataStore.clear()
  }

  override fun saveCredentials(loginInfo: LoginInfo) {
      userStore.credential.set(gson.toJson(loginInfo))
  }

  override fun getCredentials(): LoginInfo? {
    return when {
        userStore.credential.isSet -> gson.fromJson(
            userStore.credential.get(),
            LoginInfo::class.java
        )
      else -> null
    }
  }

  val User.clientCode: String
    get() {
      return when (userType) {
          UserType.OPEN_USER -> "MGUEST"
          else -> userCode
      }
    }
}
