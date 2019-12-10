package gunjika.varshney.gla.bluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    Button scanner, connect;
    Switch s1, s2, s3, s4, s5;
    Spinner devicelist;
    Map<String, String> devices;
    TextView status;



    private Set<BluetoothDevice> pairedDevices;
    public static String EXTRA_ADDRESS = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialization
        devicelist=findViewById(R.id.spinner);
        devicelist.setEnabled(false);
        scanner=findViewById(R.id.scanner);
        s1=findViewById(R.id.switch1);
        s2=findViewById(R.id.switch2);
        s3=findViewById(R.id.switch3);
        s4=findViewById(R.id.switch4);
        s5=findViewById(R.id.switch5);
        status=findViewById(R.id.status);

        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btSocket!=null)
                {
                    if(isChecked){
                        try
                        {
                            btSocket.getOutputStream().write("1".getBytes());
                        }
                        catch (IOException e)
                        {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        try
                        {
                            btSocket.getOutputStream().write("0".getBytes());
                        }
                        catch (IOException e)
                        {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btSocket!=null)
                {
                    if(isChecked){
                        try
                        {
                            btSocket.getOutputStream().write("3".getBytes());
                        }
                        catch (IOException e)
                        {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        try
                        {
                            btSocket.getOutputStream().write("2".getBytes());
                        }
                        catch (IOException e)
                        {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btSocket!=null)
                {
                    if(isChecked){
                        try
                        {
                            btSocket.getOutputStream().write("5".getBytes());
                        }
                        catch (IOException e)
                        {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        try
                        {
                            btSocket.getOutputStream().write("4".getBytes());
                        }
                        catch (IOException e)
                        {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
        s4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (btSocket!=null)
                {
                    if(isChecked){
                        try
                        {
                            btSocket.getOutputStream().write("7".getBytes());
                        }
                        catch (IOException e)
                        {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        try
                        {
                            btSocket.getOutputStream().write("6".getBytes());
                        }
                        catch (IOException e)
                        {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        s5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                s1.setChecked(isChecked);
                s2.setChecked(isChecked);
                s3.setChecked(isChecked);
                s4.setChecked(isChecked);
            }
        });

        connect=findViewById(R.id.connect);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btSocket=null;
                String device = devicelist.getSelectedItem().toString();
                EXTRA_ADDRESS=devices.get(device);
                new ConnectBT().execute();
            }
        });

        s1.setEnabled(false);
        s2.setEnabled(false);
        s3.setEnabled(false);
        s4.setEnabled(false);
        s5.setEnabled(false);
        connect.setEnabled(false);

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothAdapter adapter=getBluetooth();
                if(adapter!=null)
                    pairedDevicesList(adapter);
            }
        });
    }

    public BluetoothAdapter  getBluetooth(){
        BluetoothAdapter myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if(myBluetooth == null){
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
        }
        else if(!myBluetooth.isEnabled())
        {
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon,1);

        }
        else{
            return myBluetooth;
        }
        return  null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode== 0) {
            getBluetooth();
        }
    }

    private void pairedDevicesList(BluetoothAdapter myBluetooth)
    {
        pairedDevices = myBluetooth.getBondedDevices();
        devices=new HashMap<>();
        ArrayList<String> lst=new ArrayList<>();

        if (pairedDevices.size()>0)
        {
            for(BluetoothDevice bt : pairedDevices) {
                devices.put(bt.getName(), bt.getAddress());
                lst.add(bt.getName());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lst);
            devicelist.setAdapter(dataAdapter);
            devicelist.setEnabled(true);
            connect.setEnabled(true);

        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }
    }

    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    ProgressDialog progress;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(MainActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    BluetoothDevice dispositivo =  BluetoothAdapter.getDefaultAdapter().getRemoteDevice(EXTRA_ADDRESS);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                Toast.makeText(MainActivity.this, ("Connection Failed. Is it a SPP Bluetooth? Try again."), Toast.LENGTH_SHORT).show();
                s1.setEnabled(false);
                s2.setEnabled(false);
                s3.setEnabled(false);
                s4.setEnabled(false);
                s5.setEnabled(false);
                status.setText("No device connected!");
                isBtConnected=false;
            }
            else
            {
                Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                s1.setEnabled(true);
                s2.setEnabled(true);
                s3.setEnabled(true);
                s4.setEnabled(true);
                s5.setEnabled(true);
                isBtConnected = true;
                status.setText("Connected with "+btSocket.getRemoteDevice().getName());
            }
            progress.dismiss();
        }
    }
}