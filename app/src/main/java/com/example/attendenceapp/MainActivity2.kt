package com.example.attendenceapp

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import c.tlgbltcn.bluetoothhelper.BluetoothDeviceModel
import c.tlgbltcn.bluetoothhelper.BluetoothListAdapter
import c.tlgbltcn.library.BluetoothHelper
import c.tlgbltcn.library.BluetoothHelperListener
import com.example.attendenceapp.Fragments.ChooseNameFragment
import com.example.attendenceapp.databinding.ActivityMain2Binding
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity2 : AppCompatActivity(), BluetoothHelperListener {
    private  var num=0
    var SubCode:String=""
    var Count=0
    private val PERMISSION_REQUEST_CODE = 200
    var db = FirebaseFirestore.getInstance()
    private lateinit var bluetoothHelper: BluetoothHelper
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var binding: ActivityMain2Binding
    private var itemList = ArrayList<BluetoothDeviceModel>()
    private var itemList2 = ArrayList<BluetoothDeviceModel>()
    private var list=ArrayList<String>()
    private val map = LinkedHashMap<String,String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main2)
        val view = binding.root
         // Hard Coding the details of the students
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        binding.Date.addTextChangedListener(watcher)
         list.add("80:AD:16:AC:0C:0D")
        list.add("04:BA:8D:95:FE:D1")
        list.add("20:CD:6E:35:1A:78")
        list.add("50:49:BO:18:6C:96")
        list.add("90:78:B2:CD:ED:EE")
        list.add("10:EC:81:CB:41:D1")
        list.add("60:3A:AF:68:2E:F6")
        list.add("B8:90:47:7D:F2:3B")
        list.add("34:1C:F0:87:58:44")
        list.add("4C:02:20:1D:E4:3B")
        list.add("D0:49:7C:D4:12:A0")
        list.add("5C:17:CF:7F:6B:5C")
        list.add("18:54:CF:EE:3C:43")
        list.add("D4:8A:39:7A:50:4F")
        list.add("20:74:54:0E:62:B2")
        list.add("7C:D9:5C:AA:0F:0D")
        list.add("E0:1F:88:29:BF:09")
        list.add("58:85:E9:9F:9A:14")
        list.add("4C:4F:EE:0D:FC:8F")
        list.add("D0:97:FE:70:4C:84")
        list.add("80:79:5D:D7:72:66")
        list.add("20:CD:6E:8F:AB:AA")
        list.add("B8:C9:B5:A6:59:E4")
        list.add("5C:17:CF:3D:BE:8E")
        list.add("B4:31:61:FC:15:D8")
        list.add("7C:B0:73:2F:70:FA")
        list.add("E8:5A:8B:84:0E:F4")
        list.add("B0:8C:75:BE:FB:1A")
        list.add("30:FC:EB:7F:79:3D")
        list.add("94:8A:C6:3A:B3:C4")
        list.add("C8:3D:DC:CC:55:75")
        list.add("E8:7F:95:66:42:1B")
        list.add("7C:B0:73:FE:5E:78")
        list.add("04:BD:BF:D2:72:EA")
        list.add("44:46:87:0D:A5:76")
        list.add("D4:D7:CF:F9:79:A2")
        list.add("88:A3:03:FA:39:E4")
        list.add("E0:1F:88:29:66:78")
        list.add("20:64:CB:35:9F:48")
        list.add("5C:17:CF:80:49:1A")
        list.add("48:9D:D1:70:61:77")
        list.add("5C:17:CF:84:3C:0B")
        list.add("FC:D4:C6:16:61:D9")
        list.add("50:49:B0:13:D4:CE")
        list.add("9C:28:F7:1C:FD:1D")
        list.add("4C:63:71:46:BA:69")








        map["80:AD:16:AC:0C:0D"]="S20200020243" // Akshat
        map["04:BA:8D:95:FE:D1"]="S20200020285"   // Hemanth
        map["20:CD:6E:35:1A:78"]="S20200020244"   // Alekhya
        map["50:49:BO:18:6C:96"]="S20200020256"  //  Sreeja
        map["90:78:B2:CD:ED:EE"]="S20200020279"  // Shivamani
        map["B8:90:47:7D:F2:3B"]="S20200020253"  // Jahanvi
        map["34:1C:F0:87:58:44"]="S20200020261"  // Sneha
        map["4C:02:20:1D:E4:3B"]="S20200020296"  // Rahul Raj
        map["60:3A:AF:68:2E:F6"]="S20200020286" // Abhinav
        map["D0:49:7C:D4:12:A0"]="S20200020265" // Ramya
        map["5C:17:CF:7F:6B:5C"]="S20200020273" // kana
        map["18:54:CF:EE:3C:43"]="S20200020290" // SHREYAS
        map["D4:8A:39:7A:50:4F"]="S20200020277" // SRAVANTH
        map["20:74:54:0E:62:B2"]="S20200020274" // JEEVANA
        map["7C:D9:5C:AA:0F:0D"]="S20200020262" // BALU
        map["E0:1F:88:29:BF:09"]="S20200020305" // KALYAN
        map["58:85:E9:9F:9A:14"]="S20200020294" // DHARANI
        map["4C:4F:EE:0D:FC:8F"]="S20200020310" // ISHA
        map["D0:97:FE:70:4C:84"]="S20200020245" // AMAN
        map["80:79:5D:D7:72:66"]="S20200020311" // TANISHQ
        map["20:CD:6E:8F:AB:AA"]="S20200020293" // ANIRUDH
        map["B8:C9:B5:A6:59:E4"]="S20200020267" // ANIRUDH
        map["5C:17:CF:3D:BE:8E"]="S20200020275" // KARTHIK
        map["B4:31:61:FC:15:D8"]="S20200020280" // MAYANK
        map["7C:B0:73:2F:70:FA"]="S20200020240" // ABHISHEK
        map["E8:5A:8B:84:0E:F4"]="S20200020283" // MRIDUL
        map["B0:8C:75:BE:FB:1A"]="S20200020259" // MRIDUL
        map["30:FC:EB:7F:79:3D"]="S20200020281" // WASIM
        map["94:8A:C6:3A:B3:C4"]="S20200020260" //  SAI SURYA
        map["C8:3D:DC:CC:55:75"]="S20200020304" //  Meghana
        map["E8:7F:95:66:42:1B"]="S20200020284" //  Surya naidu
        map["7C:B0:73:FE:5E:78"]="S20200020289" //  NIKHIL
        map["04:BD:BF:D2:72:EA"]="S20200020249" //  ASHWANI
        map["44:46:87:0D:A5:76"]="S20200020255" //  BHANU
        map["D4:D7:CF:F9:79:A2"]="S20200020295" //  JAGAN RAM JASWANTH
        map["88:A3:03:FA:39:E4"]="S20200020292" //  HARIKA
        map["E0:1F:88:29:66:78"]="S20200020315" //  PRIYA
        map["20:64:CB:35:9F:48"]="S20200020271" //  VISHWATEJa
        map["5C:17:CF:80:49:1A"]="S20200020301" //  VAZEER
        map["48:9D:D1:70:61:77"]="S20200020252" //  VIKAS
        map["5C:17:CF:84:3C:0B"]="S20200020251" //  CR
        map["FC:D4:C6:16:61:D9"]="S20200020314" //  ISIKA
        map["50:49:B0:13:D4:CE"]="S20200020264" //  HIMAJA
        map["9C:28:F7:1C:FD:1D"]="S20200020270" //   KAVYA SREE
        map["4C:63:71:46:BA:69"]="S20200020282" //   MAYANK







        bluetoothHelper = BluetoothHelper(this@MainActivity2, this@MainActivity2)
            .setPermissionRequired(true)
            .create()

        if (bluetoothHelper.isBluetoothEnabled()){
            binding.enableDisable.text = "Bluetooth State Off"
        }else{
            binding.enableDisable.text = "Bluetooth State On"
        }



//        if (bluetoothHelper.isBluetoothScanning()){
//            binding.startStop.text = "Stop discovery"
//
//            num=0
//        }
//        else {
//            binding.startStop.text = "Start discovery"
//        }
        binding.startStop?.setOnCheckedChangeListener { _, isChecked ->
           if(isChecked){
               itemList2.clear()
               bluetoothHelper.startDiscovery()
               binding.progressBar.visibility=View.VISIBLE
               Handler(Looper.getMainLooper()).postDelayed({
                   binding.progressBar.visibility=View.GONE
                   bluetoothHelper.stopDiscovery()
                   num=0
                   Count=0
               }, 60000)
               viewAdapter = BluetoothListAdapter(itemList2)
               binding.recyclerView.apply {
                   layoutManager = LinearLayoutManager(this@MainActivity2)
                   adapter = viewAdapter
               }

           }else{

               bluetoothHelper.stopDiscovery()
               itemList.clear()
               num=0
           }
        }
//        binding.startStop.setOnClickListener {
//
//            if (bluetoothHelper.isBluetoothScanning()) {
//                bluetoothHelper.stopDiscovery()
//                itemList.clear()
//                num=0
//
//            } else {
//                bluetoothHelper.startDiscovery()
//                binding.progressBar.visibility=View.VISIBLE
//                Handler(Looper.getMainLooper()).postDelayed({
//                    binding.progressBar.visibility=View.GONE
//                    bluetoothHelper.stopDiscovery()
//                    num=0
//                }, 10000)
//                viewAdapter = BluetoothListAdapter(itemList2)
//                binding.recyclerView.apply {
//                    layoutManager = LinearLayoutManager(this@MainActivity2)
//                    adapter = viewAdapter
//                }
//
//            }
//        }
        val languages = resources.getStringArray(R.array.Roll_Numbers)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.Spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    var user: MutableMap<String?, Any?>? = HashMap()
                    val sdf = SimpleDateFormat("dd-MM-yyyy")
                    val currentDate = sdf.format(Date())
                    if (user != null) {
                        user.put(currentDate.toString(),"Present")
                    }
                    if (user != null && languages[position]!="Roll Numbers") {
                        Count++
                        binding.count.text=Count.toString()
                        db.collection("Dr Hrishikesh Venkataraman").document("FCOMMUG2").collection(languages[position])
                            .document(currentDate.toString()).set(user)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

binding.back.setOnClickListener {
    val intent = Intent(this@MainActivity2, ChooseNameFragment::class.java)
    val options =
        ActivityOptions.makeCustomAnimation(this, R.anim.slide_in_left, R.anim.slide_out_right)
    this.startActivity(intent, options.toBundle())
    this.finish()
}


        setContentView(view)
    }
    private val watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            binding.apply {
               count.text=Count.toString()
                }
            }
        }

    override fun onStartDiscovery() {
        //binding.startStop.text = "Stop discovery"
    }

    override fun onFinishDiscovery() {
      //  binding.startStop.text = "Start discovery"
        itemList.clear()
    }

    override fun onEnabledBluetooth() {
      // binding.enableDisable.text = "Bluetooth State Off"
    }

    override fun onDisabledBluetooh() {
       // binding.enableDisable.text = "Bluetooth State On"

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
            lifecycleScope.launch(Dispatchers.IO) {
                if(list.contains(device?.address.toString())){
                    map[device?.address.toString()]?.let {
                        Count++
                        binding.count.text=Count.toString()
                        db.collection("Dr Hrishikesh Venkataraman").document("FCOMMUG2").collection(it)
                            .document(currentDate.toString()).set(user)
                        itemList2.add(BluetoothDeviceModel(it, device?.address))
                    }
                }
            }

        }
        num++
        //itemList2.add(BluetoothDeviceModel(device?.name+"Device", device?.address))
            viewAdapter.notifyDataSetChanged()
        binding.recyclerView.getRecycledViewPool().clear();


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