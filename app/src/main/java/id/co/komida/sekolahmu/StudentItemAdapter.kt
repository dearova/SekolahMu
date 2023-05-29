package id.co.komida.sekolahmu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class StudentItemAdapter(context: Context) : ArrayAdapter<Student>(context, R.layout.list_item_view){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        //if view null
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item_view, parent, false)
        }

        //view always available
        val tvName: TextView = view!!.findViewById(R.id.tvNamaLengkap)
        val tvGrade: TextView = view.findViewById(R.id.tvSekolah)
        val tvGender: TextView = view.findViewById(R.id.tvSex)
        val tvNoHp: TextView = view.findViewById(R.id.tvNohp)

        //get student data
        val student = getItem(position)
        tvName.text = "${student?.namaDepan} ${student?.namaBelakang}"
        tvGrade.text = student?.jenjang
        tvNoHp.text = student?.noHp
        tvGender.text = student?.gender

        return view
    }
}