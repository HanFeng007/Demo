package com.example.mvpdemo.ui.frag;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mvpdemo.R;
import com.example.mvpdemo.ui.act.RetroPracActivity;
import com.example.mvpdemo.ui.act.ScrollingActivity;

/**
 * 制作安卓吸顶效果
 */
public class FourFragment extends Fragment implements View.OnClickListener {
    private Button start_aliHome;
    private Button doubleR;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        start_aliHome = view.findViewById(R.id.start_AliHome);
        start_aliHome.setOnClickListener(this);
        doubleR = view.findViewById(R.id.start_doubleR);
        doubleR.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_AliHome:
                startActivity(new Intent(getActivity(), ScrollingActivity.class));
                break;
            case R.id.start_doubleR:
                startActivity(new Intent(getActivity(), RetroPracActivity.class));
                break;
            default:
                break;
        }
    }
}
