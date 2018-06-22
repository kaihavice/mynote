package com.xuyazhou.mynote.vp.dialog;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.utils.FingerprintUtils;
import com.xuyazhou.mynote.common.utils.ShowToast;
import com.xuyazhou.mynote.common.widget.IconTextView;
import com.xuyazhou.mynote.vp.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/2/14
 */
public class FingerprintDialogFragment extends DialogFragment implements FingerprintUiHelper.Callback {


    @BindView(R.id.fingerprint_icon)
    IconTextView fingerprintIcon;
    @BindView(R.id.cancel_button)
    IconTextView cancelButton;
    @BindView(R.id.fingerprint_status)
    TextView fingerprintStatus;
    @BindView(R.id.fingerprint_description)
    TextView fingerprintDescription;

    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintUiHelper fingerprintUiHelper;
    private HomeActivity homeActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);

    }


    public void setCryptoObject(FingerprintManager.CryptoObject cryptoObject) {
        this.cryptoObject = cryptoObject;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        homeActivity = (HomeActivity) getActivity();

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fingerprintUiHelper.startListening(cryptoObject);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fingerprint_dialog_container, container, false);
        ButterKnife.bind(this, view);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        fingerprintUiHelper = new FingerprintUiHelper(homeActivity.getSystemService(FingerprintManager.class)
                , fingerprintIcon, fingerprintStatus, this);

        if (!fingerprintUiHelper.isFingerprintAuthAvailable()) {
            goToBackup();
        }
        return view;
    }



    private void goToBackup() {


        ShowToast.Short(homeActivity, "当前手机不支持指纹识别");
        // Fingerprint is not used anymore. Stop listening for it.
        fingerprintUiHelper.stopListening();
    }

    @Override
    public void onAuthenticated() {
        FingerprintUtils.onPurchased(true /* withFingerprint */, cryptoObject);
        dismiss();
    }

    @Override
    public void onError() {
        fingerprintUiHelper.stopListening();
    }


    @OnClick(R.id.cancel_button)
    public void cancelButtonClick() {
        dismiss();
    }

    public enum Stage {
        FINGERPRINT,
        NEW_FINGERPRINT_ENROLLED,
        PASSWORD
    }
}
