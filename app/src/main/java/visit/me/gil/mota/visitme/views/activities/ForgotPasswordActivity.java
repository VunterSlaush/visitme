package visit.me.gil.mota.visitme.views.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Observable;

import visit.me.gil.mota.visitme.R;
import visit.me.gil.mota.visitme.databinding.ActivityForgotPasswordBinding;
import visit.me.gil.mota.visitme.databinding.ActivityLoginBinding;
import visit.me.gil.mota.visitme.viewModels.ForgotPasswordViewModel;
import visit.me.gil.mota.visitme.viewModels.LoginViewModel;

public class ForgotPasswordActivity extends BindeableActivity implements ForgotPasswordViewModel.Contract {

    public ForgotPasswordViewModel viewModel;
    ActivityForgotPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
    }

    @Override public void initDataBinding()
    {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password);
        viewModel = new ForgotPasswordViewModel(this, this);
        binding.setViewModel(viewModel);
    }


    @Override public void update(Observable observable, Object o)
    {
        if(observable instanceof ForgotPasswordViewModel)
        {
            ForgotPasswordViewModel viewModel = (ForgotPasswordViewModel) observable;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ChangePasswordActivity.CLOSE)
            finishSuccessful();
    }

    private void finishSuccessful() {
        setResult(ChangePasswordActivity.CLOSE);
        finish();
    }

    @Override
    public void loading(boolean loading) {
        binding.loader.setVisibility(loading ? View.VISIBLE: View.GONE);
    }
}
