package com.film.debugview.captureintent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.film.debugview.R;
import com.film.debugview.databinding.FragmentCaptureIntentBinding;
import java.util.Locale;

public class CaptureIntentFragment extends Fragment {
    public static final String ARG_PAGE_ID = "page_id";
    private FragmentCaptureIntentBinding binding;

    protected int getLayout() {
        return R.layout.fragment_capture_intent;
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCaptureIntentBinding.bind(view);

        Bundle bundle = getArguments();

        binding.txtPageId.setText(String.format(Locale.ENGLISH, "%d", bundle.getInt(ARG_PAGE_ID)));
        binding.txtData.setText(bundle.toString());
    }
}