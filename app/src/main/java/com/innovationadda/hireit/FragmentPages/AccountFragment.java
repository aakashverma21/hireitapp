package com.innovationadda.hireit.FragmentPages;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.innovationadda.hireit.DatabaseHandler;
import com.innovationadda.hireit.LoginActivity;
import com.innovationadda.hireit.R;
import com.innovationadda.hireit.UserViewActivity;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private Button register;
    private TextView login;
    private TextView expiryDate;
    private TextView dob;
    DatabaseHandler myDb;
    SharedPreferences sp;
    String Session="";

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initComponents(view);
        listenHandler();
        return view;
    }

    private void listenHandler() {

    }

    private void initComponents(View view) {
        sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        Session = sp.getString("email", null);
        myDb = new DatabaseHandler(getActivity());

        Cursor res = myDb.getAllData();

        if (res.getCount() == 0) {
            Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
        }
        else {
            String struserid="", strfname="",strmname="",strlname="", stremail="",strdlic="",strexplic="",strdob="",strphone="",strstreet="",strcity="",strpostal="",    strpass = "",strcpass="";
            while (res.moveToNext()) {
                struserid = res.getString(1);
                strfname = res.getString(2);
                strmname = res.getString(3);
                strlname = res.getString(4);
                stremail = res.getString(5);
                strdlic = res.getString(6);
                strexplic = res.getString(7);
                strdob = res.getString(8);
                strphone = res.getString(9);
                strstreet = res.getString(10);
                strcity = res.getString(11);
                strpostal = res.getString(12);
            }

            if (Session.equals(stremail)){
                TextView userid = ((TextView)view.findViewById(R.id.useridText));
                userid.setText("Userid : "+struserid);
                EditText firstName = ((EditText)view.findViewById(R.id.firstName));
                firstName.setText(strfname);
                EditText middleName = ((EditText)view.findViewById(R.id.middleName));
                middleName.setText(strmname);
                EditText lastName = ((EditText)view.findViewById(R.id.lastName));
                lastName.setText(strlname);
                EditText email = ((EditText)view.findViewById(R.id.email));
                email.setText(stremail);

                EditText driverLicense = ((EditText)view.findViewById(R.id.license));
                driverLicense.setText(strdlic);
                //Register Button
                register = view.findViewById(R.id.register);
                //Expiry Button
                TextView expiryDate = ((TextView)view.findViewById(R.id.expiryDate));
                expiryDate.setText(strexplic);
                //Date of Birth Button
                TextView dob = ((TextView)view.findViewById(R.id.dob));
                dob.setText(strdob);

                EditText phoneNumber = ((EditText)view.findViewById(R.id.phoneNumber));
                phoneNumber.setText(strphone);

                EditText street = ((EditText)view.findViewById(R.id.street));
                street.setText(strstreet);
                EditText city = ((EditText)view.findViewById(R.id.city));
                city.setText(strcity);
                EditText postalCode = ((EditText)view.findViewById(R.id.postalCode));
                postalCode.setText(strpostal);

            }else {

                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();

            }
        }






    }

}
