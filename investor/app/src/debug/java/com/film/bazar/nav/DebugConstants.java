package com.film.bazar.nav;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE) @StringDef({
    DebugConstants.NAVIGATE_TO_PLAYGROUND_01, DebugConstants.NAVIGATE_TO_PLAYGROUND_02
}) public @interface DebugConstants {
    String NAVIGATE_TO_PLAYGROUND_01 = "playground1";
    String NAVIGATE_TO_PLAYGROUND_02 = "playground2";
}
