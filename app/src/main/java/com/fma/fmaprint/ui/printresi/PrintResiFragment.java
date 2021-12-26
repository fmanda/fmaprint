package com.fma.fmaprint.ui.printresi;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fma.fmaprint.R;
import com.fma.fmaprint.databinding.FragmentPrintResiBinding;
import com.fma.fmaprint.helper.PrinterResiHelper;

public class PrintResiFragment extends Fragment {
    FragmentPrintResiBinding binding;
    TextView txtKodeBooking;
    TextView txtAlamat;
    TextView txtProduct;

    private PrintResiViewModel mViewModel;

    public static PrintResiFragment newInstance() {
        return new PrintResiFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentPrintResiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        txtKodeBooking = root.findViewById(R.id.txtKodeBooking);
        txtAlamat = root.findViewById(R.id.txtAlamat);
        txtProduct = root.findViewById(R.id.txtProduct);

//        txtKodeBooking.setText("3473927842342");
//        txtAlamat.setText("asdfasf aushfiasdf  \n khjaskfhjajksdhfjka \n askdfhakshdfkajhsdf");
//        txtProduct.setText("");

        Button btnPasteKode = root.findViewById(R.id.btnPasteKode);
        btnPasteKode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasteData(txtKodeBooking);
            }
        });

        Button btnPasteAlamat = root.findViewById(R.id.btnPasteAlamat);
        btnPasteAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasteData(txtAlamat);
            }
        });

        Button btnClear = root.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtKodeBooking.setText("");
                txtAlamat.setText("");
            }
        });


        Button btnPrint = root.findViewById(R.id.btnPrint);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrintData();
            }
        });

        return root;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PrintResiViewModel.class);
        // TODO: Use the ViewModel
    }

    private void PasteData(TextView txtview){
        ClipboardManager clipboard = (ClipboardManager)  getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        // If it does contain data, decide if you can handle the data.
        if (!(clipboard.hasPrimaryClip())) {
            //
        } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
            //
        } else {
            try {
                ClipData.Item item;
                item = clipboard.getPrimaryClip().getItemAt(0);
                txtview.setText(item.getText().toString());
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
                return;
            }

        }
    }

    private void PrintData(){
        PrinterResiHelper printer = new PrinterResiHelper(getActivity());
        printer.PrintResi(txtKodeBooking.getText().toString(), txtAlamat.getText().toString(), txtProduct.getText().toString());

    }

}