package com.film.bazar.appuser.repository

import com.film.bazar.appuser.model.LoginInfo
import com.film.bazar.appusercore.model.User

interface UserCache {
  fun dataStore(): UserDataStore
  fun saveUser(user: User)
  fun getUser(): User?
  fun clearUser()
  fun saveCredentials(loginInfo: LoginInfo)
  fun getCredentials(): LoginInfo?
}
