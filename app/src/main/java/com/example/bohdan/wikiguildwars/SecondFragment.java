package com.example.bohdan.wikiguildwars;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by bohdan on 14.05.2018.
 */

public class SecondFragment extends Fragment {

    TextView name;
    TextView type;
    TextView description;
    TextView id;
    ImageView imageView;
    Model model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        name = container.findViewById(R.id.textView58);
        type = container.findViewById(R.id.textView69);
        description = container.findViewById(R.id.textView25);
        imageView = container.findViewById(R.id.imageView2);
        id = container.findViewById(R.id.textView5);

        return container;
    }
}
