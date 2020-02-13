package com.samsung.open_crypto_wallet_app.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.samsung.open_crypto_wallet_app.R;
import com.samsung.open_crypto_wallet_app.SharedPreferenceManager;
import com.samsung.open_crypto_wallet_app.Util;
import com.samsung.open_crypto_wallet_app.databinding.FragmentSendEtherBinding;
import com.samsung.open_crypto_wallet_app.databinding.TokenBinding;
import com.samsung.open_crypto_wallet_app.view_model.AccountViewModel;
import com.samsung.open_crypto_wallet_app.view_model.TransactionViewModel;

import org.web3j.utils.Convert;

import java.math.BigInteger;

public class TransactionHistoryFragment extends Fragment {

    private EditText mReceiverAccountField;
    private EditText mEtherAmountToSendEditText;
    private TextView mCurrentAmount;
    private RadioGroup mTransactionSpeedRadioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TokenBinding binding = DataBindingUtil.inflate(inflater, R.layout.token, container, false);
        Log.d("test", binding == null ? "binding is null" : "binding is not null");
        binding.setAccountInfo(AccountViewModel.getDefaultAccount());
        binding.setTransactionModel(TransactionViewModel.getCurrentTransaction());
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button sendTransactionButton = view.findViewById(R.id.sbutton);
        mCurrentAmount = view.findViewById(R.id.currentBalanceTextView);
        mReceiverAccountField = view.findViewById(R.id.receiverAddressEditText);
        mTransactionSpeedRadioGroup = view.findViewById(R.id.transactionSpeedRadioGroup);
        setSendTransactionButtonOnClickListener(sendTransactionButton);
        super.onViewCreated(view, savedInstanceState);
    }


    private void setSendTransactionButtonOnClickListener(Button sbutton) {
        sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TransactionViewModel.getCurrentTransaction().isSigned()) {
                    TransactionViewModel.sendTransaction(getActivity());
                } else {
                    AlertUtil.showTransactionNotSignedDialog();
                }
            }
        });
    }

    private String getTransactionSpeed(int selectedRadioButtonId) {
        if (selectedRadioButtonId == R.id.transactionSpeedSlow) {
            return "slow";
        }
        if (selectedRadioButtonId == R.id.transactionSpeedAverage) {
            return "average";
        }
        if (selectedRadioButtonId == R.id.transactionSpeedFast) {
            return "fast";
        }
        return "average";
    }

    //When QRCode Activity closes, it returns the result here
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d(Util.LOG_TAG, "Cancelled");
            } else {
                updateReceiverEditText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void updateReceiverEditText(String scanCode) {
        scanCode = scanCode.replace("ethereum:", "");
        mReceiverAccountField.setText(scanCode);
    }
}
