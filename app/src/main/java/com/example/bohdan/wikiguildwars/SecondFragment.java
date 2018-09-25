package com.example.bohdan.wikiguildwars;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bohdan.wikiguildwars.databinding.SecondFragmentBinding;
import com.squareup.picasso.Picasso;

/** Created by bohdan on 14.05.2018. */

public class SecondFragment extends Fragment {

    /*TextView name;
    TextView type;
    TextView description;
    TextView id;
    ImageView imageView;
    Button back;*/

    Model model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //container = (ViewGroup) inflater.inflate(R.layout.second_fragment, container,false);
        SecondFragmentBinding secondFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.second_fragment,
                container, false);
        /*back = container.findViewById(R.id.button);
        name = container.findViewById(R.id.textView58);
        type = container.findViewById(R.id.textView69);
        description = container.findViewById(R.id.textView25);
        imageView = container.findViewById(R.id.imageView2);
        id = container.findViewById(R.id.textView5);*/

        Bundle bundle = getArguments();
        if (bundle != null) {
            model = (Model) bundle.getSerializable("model");
        }

        //secondFragmentBinding.textViewName.setText("Name: " + model.getName());
        //secondFragmentBinding.textViewType.setText("Type: " + model.getType());
        //secondFragmentBinding.textView2Description.setText("Description: " + model.getDescription());
        //secondFragmentBinding.textViewId.setText("Id: " + model.getId());
        //Picasso.get().load(model.getIcon()).into(secondFragmentBinding.imageView2);

        secondFragmentBinding.setModel(model);

        // кнопка для повернення на попередній фрагмент
        secondFragmentBinding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity activityHome = (MainActivity) view.getContext();
                CallbackClass callbacks = new CallbackClass();
                callbacks.registerCallBack(activityHome);

                callbacks.buttonBack();
            }
        });

        return secondFragmentBinding.getRoot();
    }
}
