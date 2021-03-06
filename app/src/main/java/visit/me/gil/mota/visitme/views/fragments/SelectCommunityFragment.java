package visit.me.gil.mota.visitme.views.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import visit.me.gil.mota.visitme.R;
import visit.me.gil.mota.visitme.databinding.FragmentSelectCommunityBinding;
import visit.me.gil.mota.visitme.models.Community;
import visit.me.gil.mota.visitme.utils.Pnotify;
import visit.me.gil.mota.visitme.viewModels.SelectCommunityViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectCommunityFragment extends Fragment implements SelectCommunityViewModel.Contract {

    private FragmentSelectCommunityBinding binding;
    private SelectCommunityViewModel viewModel;
    private Contract contract;
    private ArrayAdapter<Community> adapter;


    public SelectCommunityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_community, container, false);
        viewModel = new SelectCommunityViewModel(this);
        binding.setViewModel(viewModel);
        adapter = new ArrayAdapter<Community>(getActivity(), android.R.layout.simple_spinner_item, viewModel.getCommunities());
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(onItemSelected);
        return binding.getRoot();
    }

    @Override
    public void onSelectCommunity() {
        contract.onSelectCommunity(viewModel.getCommunity());
    }

    @Override
    public void setError(String err) {
        Pnotify.makeText(getActivity(), err, Toast.LENGTH_SHORT, Pnotify.ERROR).show();
    }

    @Override
    public Community getSelected() {
        return (Community) binding.spinner.getSelectedItem();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            contract = (Contract) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MyInterface ");
        }
    }

    private AdapterView.OnItemSelectedListener onItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.i("CHANGEEE","!!!@");
            Glide.with(getActivity()).load(viewModel.getCommunities().get(binding.spinner.getSelectedItemPosition()).getImage())
                    .placeholder(R.drawable.house).error(R.drawable.house).into(binding.communityLogo);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public interface Contract {
        void onSelectCommunity(Community community);
    }
}
