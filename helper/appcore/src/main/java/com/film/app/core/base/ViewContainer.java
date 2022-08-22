package com.film.app.core.base;

import android.app.Activity;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

/**
 * An indirection which allows controlling the root container used for each activity.
 */
public interface ViewContainer {
    /**
     * An {@link ViewContainer} which returns the normal activity content view.
     */
    ViewContainer DEFAULT = activity -> activity.findViewById(android.R.id.content);

    /**
     * The root {@link ViewGroup} into which the activity should place its contents.
     */
    ViewGroup forActivity(@NonNull Activity activity);
}
