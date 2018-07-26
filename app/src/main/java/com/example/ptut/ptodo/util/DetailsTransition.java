package com.example.ptut.ptodo.util;

import android.support.transition.ChangeBounds;
import android.support.transition.ChangeImageTransform;
import android.support.transition.ChangeTransform;
import android.support.transition.TransitionSet;

/**
 * Created by Ptut on 3/6/2018.
 */
public class DetailsTransition extends TransitionSet {
    public DetailsTransition() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeTransform()).
                addTransition(new ChangeImageTransform());
    }
}