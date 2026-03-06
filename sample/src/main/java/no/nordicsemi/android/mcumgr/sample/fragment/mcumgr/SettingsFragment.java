package no.nordicsemi.android.mcumgr.sample.fragment.mcumgr;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import no.nordicsemi.android.mcumgr.sample.databinding.FragmentCardSettingsBinding;
import no.nordicsemi.android.mcumgr.sample.di.Injectable;
import no.nordicsemi.android.mcumgr.sample.viewmodel.mcumgr.McuMgrViewModelFactory;
import no.nordicsemi.android.mcumgr.sample.viewmodel.mcumgr.SettingsViewModel;

public class SettingsFragment extends Fragment implements Injectable {

    @Inject
    McuMgrViewModelFactory viewModelFactory;

    private FragmentCardSettingsBinding binding;
    private SettingsViewModel viewModel;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, viewModelFactory)
                .get(SettingsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        binding = FragmentCardSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getBusyState().observe(getViewLifecycleOwner(), busy -> {
            binding.actionRead.setEnabled(!busy);
            binding.actionWrite.setEnabled(!busy);
            binding.actionDelete.setEnabled(!busy);
        });

        viewModel.getResponse().observe(getViewLifecycleOwner(), response -> {
            binding.settingsContent.setVisibility(response != null ? View.VISIBLE : View.GONE);
            if (response != null) {
                binding.settingsResponse.setText(response);
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            binding.settingsContent.setVisibility(error != null ? View.VISIBLE : View.GONE);
            if (error != null) {
                binding.settingsResponse.setText(error);
            }
        });

        binding.actionRead.setOnClickListener(v -> {
            final String key = binding.settingsKeyValue.getText().toString();
            if (!TextUtils.isEmpty(key)) {
                viewModel.read(key);
            }
        });

        binding.actionWrite.setOnClickListener(v -> {
            final String key = binding.settingsKeyValue.getText().toString();
            final String val = binding.settingsValueField.getText().toString();
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(val)) {
                viewModel.write(key, val);
            }
        });

        binding.actionDelete.setOnClickListener(v -> {
            final String key = binding.settingsKeyValue.getText().toString();
            if (!TextUtils.isEmpty(key)) {
                viewModel.delete(key);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
