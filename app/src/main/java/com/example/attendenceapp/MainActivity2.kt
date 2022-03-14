package com.example.attendenceapp

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.tlgbltcn.bluetoothhelper.BluetoothDeviceModel
import c.tlgbltcn.bluetoothhelper.BluetoothListAdapter
import c.tlgbltcn.library.BluetoothHelper
import c.tlgbltcn.library.BluetoothHelperListener
import com.example.attendenceapp.databinding.ActivityMain2Binding
import com.example.attendenceapp.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity2 : AppCompatActivity(), BluetoothHelperListener {
    var SubCode:String=""
    var db = FirebaseFirestore.getInstance()
    private lateinit var bluetoothHelper: BluetoothHelper
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var binding: ActivityMain2Binding
    private var itemList = ArrayList<BluetoothDeviceModel>()
    private var list=ArrayList<String>()
    private val map = LinkedHashMap<String,String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main2)
        val view = binding.root
         // Hard Coding the details of the students

         list.add("80:AD:16:AC:0C:0D")
        list.add("04:BA:8D:95:FE:D1")
        list.add("A4:CC:B9:99:7B:86")
          map["80:AD:16:AC:0C:0D"]="S20200020243"
        map["04:BA:8D:95:FE:D1"]="S20200020285"
        map["A4:CC:B9:99:7B:86"]="S20200020244"



        bluetoothHelper = BluetoothHelper(this@MainActivity2, this@MainActivity2)
            .setPermissionRequired(true)
            .create()

        if (bluetoothHelper.isBluetoothEnabled()) binding.enableDisable.text = "Bluetooth State Off"
        else binding.enableDisable.text = "Bluetooth State On"

        if (bluetoothHelper.isBluetoothScanning()) binding.startStop.text = "Stop discovery"
        else binding.startStop.text = "Start discovery"
        binding.startStop.setOnClickListener {
            if (bluetoothHelper.isBluetoothScanning()) {
                bluetoothHelper.stopDiscovery()

            } else {
                bluetoothHelper.startDiscovery()
            }
        }

        viewAdapter = BluetoothListAdapter(itemList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity2)
            adapter = viewAdapter
        }
        setContentView(view)
    }

    override fun onStartDiscovery() {
        binding.startStop.text = "Stop discovery"
    }

    override fun onFinishDiscovery() {
        binding.startStop.text = "Start discovery"
        itemList.clear()
    }

    override fun onEnabledBluetooth() {
        binding.enableDisable.text = "Bluetooth State Off"
    }

    override fun onDisabledBluetooh() {
        binding.enableDisable.text = "Bluetooth State On"

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun getBluetoothDeviceList(device: BluetoothDevice?) {
        Log.d("main",device?.address.toString())
        var user: MutableMap<String?, Any?>? = HashMap()
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        if (user != null) {
            user.put(currentDate.toString(),"Present")
        }
        if (user != null) {

            if(list.contains(device?.address.toString())){
                map[device?.address.toString()]?.let {
                    db.collection("Dr Hrishikesh Venkataraman").document("FCOMMUG2").collection(it)
                        .document(currentDate.toString()).set(user)
                }
            }
        }
        itemList.add(BluetoothDeviceModel(device?.name, device?.address))
        viewAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        bluetoothHelper.registerBluetoothStateChanged()
    }


    override fun onStop() {
        super.onStop()
        bluetoothHelper.unregisterBluetoothStateChanged()
    }
}