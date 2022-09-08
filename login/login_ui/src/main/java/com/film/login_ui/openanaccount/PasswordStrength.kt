package com.film.login_ui.openanaccount

import com.film.login_ui.R

enum class PasswordStrength private constructor(internal var resId: Int, color: Int) {

    WEAK(R.string.password_strength_weak, R.color.film_red_error),
    MEDIUM(R.string.password_strength_medium, R.color.fil_passowrd_medim),
    STRONG(R.string.password_strength_strong, R.color.fil_passowrd_good),
    VERY_STRONG(R.string.password_strength_very_strong, R.color.film_green_success);

    var color: Int = 0
        internal set

    init {
        this.color = color
    }

    fun getText(ctx: android.content.Context): CharSequence {
        return ctx.getText(resId)
    }

    companion object {
        internal var REQUIRED_LENGTH = 8
        internal var MAXIMUM_LENGTH = 15
        internal var REQUIRE_SPECIAL_CHARACTERS = true
        internal var REQUIRE_DIGITS = true
        internal var REQUIRE_LOWER_CASE = true
        internal var REQUIRE_UPPER_CASE = false

        fun calculateStrength(password: String): PasswordStrength {
            var currentScore = 0
            var sawUpper = false
            var sawLower = false
            var sawDigit = false
            var sawSpecial = false
            for (element in password) {
                val c = element
                if (!sawSpecial && !Character.isLetterOrDigit(c)) {
                    currentScore += 1
                    sawSpecial = true
                } else {
                    if (!sawDigit && Character.isDigit(c)) {
                        currentScore += 1
                        sawDigit = true
                    } else {
                        if (!sawUpper || !sawLower) {
                            if (Character.isUpperCase(c))
                                sawUpper = true
                            else
                                sawLower = true
                            if (sawUpper && sawLower)
                                currentScore += 1
                        }
                    }
                }

            }

            if (password.length > REQUIRED_LENGTH) {
                if (REQUIRE_SPECIAL_CHARACTERS && !sawSpecial
                    || REQUIRE_UPPER_CASE && !sawUpper
                    || REQUIRE_LOWER_CASE && !sawLower
                    || REQUIRE_DIGITS && !sawDigit) {
                    currentScore = 1
                } else {
                    currentScore = 2
                    if (password.length > MAXIMUM_LENGTH) {
                        currentScore = 3
                    }
                }
            } else {
                currentScore = 0
            }

            when (currentScore) {
                0 -> return WEAK
                1 -> return MEDIUM
                2 -> return STRONG
                3 -> return VERY_STRONG
            }

            return VERY_STRONG
        }
    }

}