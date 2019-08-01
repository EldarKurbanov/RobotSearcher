package ru.vanilla.robotsearcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<String> robotsIPList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_main, menu);
        return true;
    }

    void refreshList() {
        //robotsIPList.add("kek");

        class Sender {
            private String host;
            private int port;

            public Sender(String host, int port) {
                this.host = host;
                this.port = port;
            }

            private void sendMessage(String message) {
                try {
                    byte[] data = message.getBytes();
                    InetAddress addr = InetAddress.getByName(host);
                    DatagramPacket pack = new DatagramPacket(data, data.length, addr, port);
                    DatagramSocket ds = new DatagramSocket();
                    ds.send(pack);
                    ds.close();
                } catch(IOException e){
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //TODO find broadcast address
        Sender sender = new Sender("192.168.100.255", 1031);
        sender.sendMessage("Robot, where are you?");

        try {
            DatagramSocket ds = new DatagramSocket(1032);
            DatagramPacket pack = new DatagramPacket(new byte[1024], 1024);
            ds.receive(pack);
            robotsIPList.add(pack.getAddress().getHostName());
        } catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(),
                    Toast.LENGTH_LONG).show();
        }


            RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refreshButton) {
            refreshList();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        robotsIPList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        RobotsRecyclerAdapter robotsRecyclerAdapter = new RobotsRecyclerAdapter(robotsIPList);
        recyclerView.setAdapter(robotsRecyclerAdapter);
    }
}