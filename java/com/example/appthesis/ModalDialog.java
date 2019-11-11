package com.example.appthesis;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class ModalDialog extends BottomSheetDialogFragment{
    private BottomSheetListener mListener;
    ImageView edit, contact,logout;
    TextView fullname;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_dialog_layout,container, false);


        edit = v.findViewById(R.id.imageView_edit);
        contact = v.findViewById(R.id.imageView_contact);
        logout = v.findViewById(R.id.imageView_log_out);
        fullname = v.findViewById(R.id.textView40);

        sessionManager = new SessionManager(v.getContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        String mfname = user.get(sessionManager.NAME);
        String mlname = user.get(sessionManager.LNAME);
        fullname.setText(""+mfname+" "+mlname);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Edit");
                dismiss();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Contact");
                dismiss();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonClicked("Logout");
                dismiss();
            }
        });


        return v;
    }



    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

         try {
             mListener = (BottomSheetListener) context;
          } catch (ClassCastException e) {
             throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
          }
    }
}
