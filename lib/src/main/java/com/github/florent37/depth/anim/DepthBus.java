package com.github.florent37.depth.anim;

import android.content.Context;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by florentchampigny on 03/03/2017.
 */

public class DepthBus {

    private static Map<WeakReference<Context>, Depth> depthForContext = new HashMap<>();

    public static Depth getDepth(View view) {
        return getDepth(view.getContext());
    }

    public static Depth getDepth(Context context) {
        for (WeakReference<Context> contextWeakReference : depthForContext.keySet()) {
            final Context savedContext = contextWeakReference.get();
            if (savedContext != null) {
                if (context == savedContext) {
                    return depthForContext.get(contextWeakReference);
                }
            }
        }
        //not found
        final Depth depth = new Depth();
        depthForContext.put(new WeakReference<Context>(context), depth);
        return depth;
    }

}
