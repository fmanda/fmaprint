package com.fma.fmaprint.ui.printer;

import androidx.lifecycle.ViewModelProvider;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fma.fmaprint.R;
import com.fma.fmaprint.adapter.PrinterListAdapter;
import com.fma.fmaprint.controller.ControllerSetting;
import com.fma.fmaprint.databinding.FragmentHomeBinding;
import com.fma.fmaprint.databinding.FragmentPrinterBinding;
import com.fma.fmaprint.model.ModelPrinter;
import com.fma.fmaprint.model.ModelSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PrinterFragment extends Fragment implements PrinterListAdapter.ItemClickListener{

    private PrinterViewModel mViewModel;
    private FragmentPrinterBinding binding;
    List<ModelPrinter> printers;
    RecyclerView recyclerView;
    PrinterListAdapter adapter;
    BluetoothAdapter bluetoothAdapter;
    ModelSetting modelSetting;
//    private SelectPrinterListener mSelectListener;

    public static PrinterFragment newInstance() {
        return new PrinterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentPrinterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        recyclerView =  root.findViewById(R.id.rvDevices);
        printers = this.getPairedDevices();
        if (printers != null){
            adapter = new PrinterListAdapter(getContext(), printers);
            adapter.setClickListener(this);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
            recyclerView.setAdapter(adapter);

        }

//
//        rvInventory = root.findViewById(R.id.rvInventory);
//        inventoryAdapter = new InventoryAdapter(getContext(), mViewModel.inventories);
//        rvInventory.setLayoutManager(new GridLayoutManager(getContext(), 1));
//        rvInventory.setAdapter(inventoryAdapter);

        return root;
//        return inflater.inflate(R.layout.fragment_printer, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PrinterViewModel.class);
        // TODO: Use the ViewModel


    }

    private List<ModelPrinter> getPairedDevices() {
        List<ModelPrinter> printers = new ArrayList<ModelPrinter>();
        String value;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try{
            if(!bluetoothAdapter.isEnabled()){
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                this.startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0){

                for(BluetoothDevice device : pairedDevices){
                    printers.add(new ModelPrinter(device.getName(), device.getAddress()));
                }
            }
            else{
                value = "No Devices found";
                Toast.makeText(getActivity(), "No Devices found", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex){
            value = ex.toString()+ "\n" +" InitPrinter \n";
            Toast.makeText(getActivity(), value, Toast.LENGTH_LONG).show();
        }
        return printers;
    }

    @Override
    public void onItemClick(View view, int position) {
        ModelPrinter printer = printers.get(position);
        ControllerSetting cs = new ControllerSetting(getContext());
        cs.updateSetting("printer", printer.getName());

        Toast.makeText(getActivity(), printer.getName() + " selected ", Toast.LENGTH_LONG).show();

    }
}