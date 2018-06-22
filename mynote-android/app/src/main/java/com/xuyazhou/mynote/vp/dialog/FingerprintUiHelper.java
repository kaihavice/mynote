package com.xuyazhou.mynote.vp.dialog;

import android.annotation.TargetApi;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.TextView;

import com.xuyazhou.mynote.R;
import com.xuyazhou.mynote.common.widget.IconTextView;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017/2/14
 */
@TargetApi(Build.VERSION_CODES.M)
public class FingerprintUiHelper extends FingerprintManager.AuthenticationCallback{

    private static final long ERROR_TIMEOUT_MILLIS = 1600;
    private static final long SUCCESS_DELAY_MILLIS = 1300;

    private final FingerprintManager fingerprintManager;
    private final IconTextView iconTextView;
    private final TextView errorTextView;
    private final Callback callback;
    private CancellationSignal cancellationSignal;

    private boolean selfCancelled;


    public FingerprintUiHelper(FingerprintManager fingerprintManager, IconTextView icon,
                               TextView errorTextView, Callback callback) {
        this.fingerprintManager = fingerprintManager;
        this.iconTextView = icon;
        this.errorTextView = errorTextView;
        this.callback = callback;
    }


    public boolean isFingerprintAuthAvailable() {
        // The line below prevents the false positive inspection from Android Studio
        // noinspection ResourceType
        return fingerprintManager.isHardwareDetected()
                && fingerprintManager.hasEnrolledFingerprints();
    }

    public void startListening(FingerprintManager.CryptoObject cryptoObject) {
        if (!isFingerprintAuthAvailable()) {
            return;
        }
        cancellationSignal = new CancellationSignal();
        selfCancelled = false;
        // The line below prevents the false positive inspection from Android Studio
        // noinspection ResourceType
        fingerprintManager
                .authenticate(cryptoObject, cancellationSignal, 0 /* flags */, this, null);
        iconTextView.setText(R.string.ic_svg_fingerprint);
        iconTextView.setBackground(iconTextView.getContext().getResources().getDrawable(R.drawable.circle_bg));
    }

    public void stopListening() {
        if (cancellationSignal != null) {
            selfCancelled = true;
            cancellationSignal.cancel();
            cancellationSignal = null;
        }
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        if (!selfCancelled) {
            showError(errString);
            iconTextView.postDelayed(callback::onError, ERROR_TIMEOUT_MILLIS);
        }
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        showError(helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        showError(iconTextView.getResources().getString(
                R.string.try_again));
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        errorTextView.removeCallbacks(mResetErrorTextRunnable);
        iconTextView.setText(R.string.ic_svg_done);
       iconTextView.setTextColor(iconTextView.getContext().getResources().getColor(R.color.white));
       iconTextView.setBackground(iconTextView.getContext().getResources().getDrawable(R.drawable.circle_bg));


        errorTextView.setTextColor(
                errorTextView.getResources().getColor(R.color.textColorAccentPrimary_light, null));
        errorTextView.setText(
                errorTextView.getResources().getString(R.string.fingerprint_success));
        iconTextView.postDelayed(callback::onAuthenticated, SUCCESS_DELAY_MILLIS);
    }

    private void showError(CharSequence error) {
        iconTextView.setText(R.string.ic_svg_fingerprint);
        iconTextView.setBackground(iconTextView.getContext().getResources().getDrawable(R.drawable.circle_background_primary_error));
        errorTextView.setText(error);
        errorTextView.setTextColor(
                errorTextView.getResources().getColor(R.color.widget_pink, null));
        errorTextView.removeCallbacks(mResetErrorTextRunnable);
        errorTextView.postDelayed(mResetErrorTextRunnable, ERROR_TIMEOUT_MILLIS);
    }

    private Runnable mResetErrorTextRunnable = new Runnable() {
        @Override
        public void run() {
            errorTextView.setTextColor(
                    errorTextView.getResources().getColor(R.color.widget_gray, null));
            errorTextView.setText(
                    errorTextView.getResources().getString(R.string.fingerprint_hint));
            iconTextView.setText(R.string.ic_svg_fingerprint);
            iconTextView.setBackground(iconTextView.getContext().getResources().getDrawable(R.drawable.circle_bg));
        }
    };


    public interface Callback {

        void onAuthenticated();

        void onError();
    }

}
