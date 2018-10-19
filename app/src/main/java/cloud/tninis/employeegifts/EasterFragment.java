package cloud.tninis.employeegifts;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EasterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EasterFragment extends Fragment {
    EditText dateFrom;
    EditText dateTo;
    EditText earnings;
    Button calc;
    Spinner typeOfEmp;
    Utilities appUtil;

    public EasterFragment() {

    }


    public static EasterFragment newInstance() {
        EasterFragment fragment = new EasterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        getActivity().setTitle(getResources().getString(R.string.easterFragmentTitle));

        View v = inflater.inflate(R.layout.fragment_easter, container, false);
        typeOfEmp=(Spinner)v.findViewById(R.id.typeOfEmployee) ;
        earnings=(EditText)v.findViewById(R.id.earnings);
        appUtil=new Utilities();


        dateFrom=(EditText)v.findViewById(R.id.easterDateFrom);
        dateFrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showSetDate(dateFrom);
            }
        });

        dateTo=(EditText)v.findViewById(R.id.easterDateTo);
        dateTo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showSetDate(dateTo);

            }
        });

        calc=(Button)v.findViewById(R.id.easterCalc);
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean checkDateResult=false;
                Boolean checkResult= checkIfEmptyInputs();
                if(checkResult)
                    checkDateResult= checkDates();
                if(checkDateResult)
                {
                    Double[] results;
                    int days=appUtil.findDays(dateFrom.getText().toString(),dateTo.getText().toString(),BonusPeriod.Easter);
                    double amount = Double.parseDouble(earnings.getText().toString());


                    try {
                      results=appUtil.Calculate(getActivity(),days,amount,TypeOfEmployee.valueOf(typeOfEmp.getSelectedItemPosition()),BonusPeriod.Easter);
                      showResults1(results,days);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return v;
    }
    private void showResults1(Double[] results,int days)
    {
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Ημερολογιακές Ημέρες  \n"+days+"\n\n"
                            +"Δώρο Πάσχα \n"+String.format("%.2f", results[0])+"\n\n"
                            +"Προσαύξηση επιδόματος άδειας \n"+String.format("%.2f", results[1])+"\n\n"
                            +"ΣΥΝΟΛΟ ΔΩΡΟΥ ΠΑΣΧΑ  \n"+String.format("%.2f", results[2]));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setTitle("Αποτέλεσμα");
        AlertDialog alert11 = builder1.create();

        alert11.show();


    }

    private void showResults(Double[] results,int days)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Ημερολογιακές Ημέρες : "+days+"\n"
                            +"Δώρο Πάσχα : "+String.format("%.2f", results[0])+"\n"
                            +"Προσαύξηση επιδόματος άδειας :"+String.format("%.2f", results[1])+"\n"
                            +"ΣΥΝΟΛΟ ΔΩΡΟΥ ΠΑΣΧΑ : "+String.format("%.2f", results[2]));
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setTitle("Αποτέλεσμα");
        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    private void showSetDate(final EditText dateText)
    {
        Calendar mcurrentDate=Calendar.getInstance();
        int year=mcurrentDate.get(Calendar.YEAR);
        int month=mcurrentDate.get(Calendar.MONTH);
        int day=mcurrentDate.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog mDatePicker =new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday)
            {
                dateText.setText(new StringBuilder().append(selectedday).append("/").append(selectedmonth+1).append("/").append(selectedyear));
            }
        },year, month, day);

        long maxMillis,minMillis ;
        String minDate="01/01/"+year+" 00:00:00";
        String maxDate="30/04/"+year+" 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {

            Date date= sdf.parse(minDate);
            minMillis = date.getTime();
            date=sdf.parse(maxDate);
            maxMillis= date.getTime();
            mDatePicker.getDatePicker().setMinDate(minMillis);
            mDatePicker.getDatePicker().setMaxDate(maxMillis);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            mDatePicker.show();
        }
    }

    private Boolean checkIfEmptyInputs()
    {


       if(typeOfEmp.getSelectedItemPosition()==0)
       {
           Toast.makeText(getActivity(), getString(R.string.selectTypeOfEmp), Toast.LENGTH_SHORT).show();
           return false;
       }
       if(earnings.getText().toString().equals(""))
       {
            Toast.makeText(getActivity(), getString(R.string.emptyEarnings), Toast.LENGTH_SHORT).show();
            return false;
       }

        if(dateFrom.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), getString(R.string.emptyDateFrom), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dateTo.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), getString(R.string.emptyDateTo), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private Boolean checkDates() {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date dateF = sdf.parse(dateFrom.getText().toString()+" 00:00:00");
            Date dateT = sdf.parse(dateTo.getText().toString()+" 00:00:00");
            if(dateF.after(dateT))
            {
                Toast.makeText(getActivity(), getString(R.string.datesNotValid), Toast.LENGTH_SHORT).show();
                return false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }


}
