package com.deucecoded.uponthetimes.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.deucecoded.uponthetimes.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link DialogFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFilterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFilterFragment extends DialogFragment {
    @BindView(R.id.date_edit_text)
    EditText dateField;
    @BindView(R.id.sort_spinner)
    Spinner sortSpinner;
    @BindView(R.id.cb_arts)
    CheckBox artsBox;
    @BindView(R.id.cb_fashion)
    CheckBox fashionBox;
    @BindView(R.id.cb_sports)
    CheckBox sportsBox;

    private OnFragmentInteractionListener mListener;

    public SearchFilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFilterFragment newInstance() {
        SearchFilterFragment fragment = new SearchFilterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUpSpinner() {
        ArrayList<String> sortOptions = new ArrayList<>();
        sortOptions.add("Oldest");
        sortOptions.add("Newest");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, sortOptions);
        sortSpinner.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_search_filter, container, false);
        ButterKnife.bind(this, view);
        setUpSpinner();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @OnClick(R.id.btn_apply)
    public void applyAndDismiss() {
        mListener.onApplyFilters();
        dismiss();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onApplyFilters();
    }
}
