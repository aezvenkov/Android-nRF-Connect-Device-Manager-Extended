package no.nordicsemi.android.mcumgr.sample.viewmodel.mcumgr;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;
import javax.inject.Named;

import no.nordicsemi.android.mcumgr.McuMgrCallback;
import no.nordicsemi.android.mcumgr.exception.McuMgrException;
import no.nordicsemi.android.mcumgr.managers.SettingsManager;
import no.nordicsemi.android.mcumgr.response.McuMgrResponse;
import no.nordicsemi.android.mcumgr.response.settings.McuMgrSettingsReadResponse;

public class SettingsViewModel extends McuMgrViewModel {
    private final SettingsManager settingsManager;

    private final MutableLiveData<String> responseLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    @Inject
    SettingsViewModel(final SettingsManager settingsManager,
                      @Named("busy") final MutableLiveData<Boolean> state) {
        super(state);
        this.settingsManager = settingsManager;
    }

    @NonNull
    public LiveData<String> getResponse() {
        return responseLiveData;
    }

    @NonNull
    public LiveData<String> getError() {
        return errorLiveData;
    }

    public void read(final String name) {
        setBusy();
        errorLiveData.setValue(null);
        responseLiveData.setValue(null);
        settingsManager.read(name, new McuMgrCallback<McuMgrSettingsReadResponse>() {
            @Override
            public void onResponse(@NonNull final McuMgrSettingsReadResponse response) {
                if (response.isSuccess()) {
                    if (response.val != null) {
                        responseLiveData.postValue(new String(response.val));
                    } else {
                        responseLiveData.postValue("<null>");
                    }
                } else {
                    errorLiveData.postValue("Return code: " + response.getReturnCodeValue() + " " + (response.getGroupReturnCode() != null ? SettingsManager.ReturnCode.valueOf(response.getGroupReturnCode()) : ""));
                }
                postReady();
            }

            @Override
            public void onError(@NonNull final McuMgrException error) {
                errorLiveData.postValue(error.getMessage());
                postReady();
            }
        });
    }

    public void write(final String name, final String value) {
        setBusy();
        errorLiveData.setValue(null);
        responseLiveData.setValue(null);
        settingsManager.write(name, value.getBytes(), new McuMgrCallback<McuMgrResponse>() {
            @Override
            public void onResponse(@NonNull final McuMgrResponse response) {
                if (response.isSuccess()) {
                    responseLiveData.postValue("Written successfully");
                } else {
                    errorLiveData.postValue("Return code: " + response.getReturnCodeValue());
                }
                postReady();
            }

            @Override
            public void onError(@NonNull final McuMgrException error) {
                errorLiveData.postValue(error.getMessage());
                postReady();
            }
        });
    }

    public void delete(final String name) {
        setBusy();
        errorLiveData.setValue(null);
        responseLiveData.setValue(null);
        settingsManager.delete(name, new McuMgrCallback<McuMgrResponse>() {
            @Override
            public void onResponse(@NonNull final McuMgrResponse response) {
                if (response.isSuccess()) {
                    responseLiveData.postValue("Deleted successfully");
                } else {
                    errorLiveData.postValue("Return code: " + response.getReturnCodeValue());
                }
                postReady();
            }

            @Override
            public void onError(@NonNull final McuMgrException error) {
                errorLiveData.postValue(error.getMessage());
                postReady();
            }
        });
    }
}
