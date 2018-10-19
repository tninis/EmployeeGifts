package cloud.tninis.employeegifts;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInstaller;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Calendar;

public class ContactFragment extends Fragment {
    Button sendEmail;
    Snackbar snackbar;
    EditText contName,contEmail,contMsg;
    ProgressDialog pd ;

    public ContactFragment() {

    }

    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        getActivity().setTitle(getResources().getString(R.string.contactFragmentTitle));
        View v=inflater.inflate(R.layout.fragment_contact, container, false);

        sendEmail=(Button)v.findViewById(R.id.sendEmailBtn);
        contName=(EditText) v.findViewById(R.id.contactName);
        contEmail=(EditText) v.findViewById(R.id.contactEmail);
        contMsg=(EditText) v.findViewById(R.id.contactMessage);
        pd= new ProgressDialog(getActivity());

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(validateInputs()) {
                    if (isOnline()) {
                        if (snackbar != null)
                            snackbar.dismiss();

                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try {

                                    showProgress("show");

                                    Calendar mcurrentDate=Calendar.getInstance();
                                    StringBuilder date=  new StringBuilder()
                                            .append(mcurrentDate.get(Calendar.DAY_OF_MONTH))
                                            .append(".")
                                            .append(mcurrentDate.get(Calendar.MONTH)+1)
                                            .append(".")
                                            .append(mcurrentDate.get(Calendar.YEAR));

                                    String body=    "Name : "+contName.getText().toString()+"\n"+
                                                    "E-mail : "+contEmail.getText().toString()+"\n"+
                                                    "--------------------------------Message------------------------------\n"+
                                                    contMsg.getText().toString()+"\n"+
                                                    "---------------------------------------------------------------------\n"


                                            ;
                                    GMailSender sender = new GMailSender("Your Google Email",
                                            "Email Password");
                                    sender.sendMail("Employee Bonus - New Contact on "+date, body, contEmail.getText().toString(), "Your Google Email");
                                    showToast("Το E-mail σας Στάλθηκε");
                                    clearInputs();
                                    showProgress("dismiss");
                                } catch (Exception e) {
                                    Log.e("SendMail", e.getMessage(), e);
                                    showToast("Προέκυψε κάποιο σφάλμα παρακαλώ δοκιμάστε ξανά!");

                                }
                            }

                        }).start();
                    } else {
                        snackbar = Snackbar.make(getActivity().findViewById(R.id.frmLayout), "Δέν υπάρχει σύνδεση στο Internet", Snackbar.LENGTH_INDEFINITE);
                        snackbar.show();
                    }
                }

            }
        });


        return v;
    }

    public void showToast(final String msg){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public boolean isOnline() {
        ConnectivityManager cm =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public boolean validateInputs(){


        if(contName.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), getString(R.string.emptyContactName), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(contEmail.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), getString(R.string.emptyContactEmail), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(contMsg.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), getString(R.string.emptyContactMessage), Toast.LENGTH_SHORT).show();
            return false;
        }



        return true;
    }

    public void clearInputs(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                contName.setText("");
                contEmail.setText("");
                contMsg.setText("");
            }
        });


    }
    public void showProgress(final String action){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(action=="show") {
                    pd.setMessage("Αποστολή E-mail...");
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();
                }
                if(action=="dismiss")
                {
                    pd.dismiss();
                }
            }
        });


    }


}
