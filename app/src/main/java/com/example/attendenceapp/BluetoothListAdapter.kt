package c.tlgbltcn.bluetoothhelper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attendenceapp.R
import com.example.attendenceapp.databinding.ItemBluetoothDeviceBinding
import java.util.*

/**
 * Created by tolga bolatcan on 26.01.2019
 */
class BluetoothListAdapter(private var item: ArrayList<BluetoothDeviceModel>) :
    RecyclerView.Adapter<BluetoothListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemBluetoothDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(item[position].name ?: "", item[position].macNumber ?: "")
    }

    class ViewHolder(val view: ItemBluetoothDeviceBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(name: String, macNumber: String) {
            view.deviceName.text = name
            view.macAddress.text = macNumber
            if (name == "S20200020285") {
                view.imageView.setBackgroundResource(R.drawable.hemanth)

            }else if(name == "S20200020244"){
                view.imageView.setBackgroundResource(R.drawable.alekhya)
            }else if(name == "S20200020279"){
                view.imageView.setBackgroundResource(R.drawable.shivamani)
            }
            else if(name == "S20200020243"){
                view.imageView.setBackgroundResource(R.drawable.akshat)
            }else if(name == "S20200020286"){
                view.imageView.setBackgroundResource(R.drawable.abhinav)
            }
            else if(name == "S20200020251"){
                view.imageView.setBackgroundResource(R.drawable.cr)
            }
        }
    }
}
