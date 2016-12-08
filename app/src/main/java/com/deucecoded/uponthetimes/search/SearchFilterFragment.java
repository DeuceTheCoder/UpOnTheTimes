package com.deucecoded.uponthetimes.search;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.deucecoded.uponthetimes.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
public class SearchFilterFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.date_edit_text)
    TextView dateField;
    @BindView(R.id.sort_spinner)
    Spinner sortSpinner;
    @BindView(R.id.cb_arts)
    CheckBox artsBox;
    @BindView(R.id.cb_fashion)
    CheckBox fashionBox;
    @BindView(R.id.cb_sports)
    CheckBox sportsBox;

    private OnFragmentInteractionListener mListener;
    private boolean sortByOldest;
    private Date selectedDate;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

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
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSort = (String) parent.getItemAtPosition(position);
                sortByOldest = selectedSort.equals("Oldest");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_search_filter, container, false);
        ButterKnife.bind(this, view);
        this.sortByOldest = false;
        setUpSpinner();
        dateField.setText(R.string.no_date_selected);

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
        mListener.onApplyFilters(selectedDate, sortByOldest, getCheckedDesks());
        dismiss();
    }

    @OnClick(R.id.date_edit_text)
    public void displayDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setTargetFragment(SearchFilterFragment.this, 0);
        datePickerFragment.show(getFragmentManager(), "datePicker");
    }

    private List<String> getCheckedDesks() {
        List<String> selectedDesks = new ArrayList<>();
        if (artsBox.isChecked()) {
            selectedDesks.add("Arts");
        }
        if (fashionBox.isChecked()) {
            selectedDesks.add("Fashion & Style");
        }
        if (sportsBox.isChecked()) {
            selectedDesks.add("Sports");
        }

        return selectedDesks;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        selectedDate = calendar.getTime();

        dateField.setText(dateFormat.format(selectedDate));
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
        void onApplyFilters(Date earliestDate, boolean sortOrder, List<String> filters);
    }
}
