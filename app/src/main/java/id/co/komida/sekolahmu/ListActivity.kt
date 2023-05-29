package id.co.komida.sekolahmu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener

class ListActivity : AppCompatActivity(), OnItemClickListener {
    //lateinit var arrayAdapter: ArrayAdapter<String>
    lateinit var studentItemAdapter:StudentItemAdapter
    private lateinit var lvStudent: ListView
    private lateinit var svStudent: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        lvStudent=findViewById(R.id.lvStudent)
        //   arrayAdapter = ArrayAdapter(this,android.R.layout.simple_selectable_list_item)
        studentItemAdapter=StudentItemAdapter(this)
        lvStudent.adapter=studentItemAdapter
        lvStudent.onItemClickListener=this

        setupSearchView()
        registerForContextMenu(lvStudent)

    }

    private fun setupSearchView() {
         svStudent = findViewById(R.id.query)

        svStudent.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                 return false
            }

            override fun onQueryTextChange(newText: String?):Boolean{
                loadDataStudent(newText)
                return false
            }
        })
    }
    private fun loadDataStudent(keyword: String?) {
        val studentDataSource = StudentDataSource(this)
        //val keyword = ""
        val studentList = studentDataSource.search(keyword!!)
        studentItemAdapter.clear()
        studentItemAdapter.addAll(studentList)
    }

    override fun onResume() {
        super.onResume()
        val studentDataSource=StudentDataSource(this)
        val studentList = studentDataSource.getAll()
        val studentNameList=studentList.map { "${it.namaDepan} ${it.namaBelakang}" }
        studentItemAdapter.clear()
        studentItemAdapter.addAll(studentList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.form_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuTambah){
            val addData = Intent(this,FormActivity ::class.java)
            startActivity(addData)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        if(v?.id == R.id.lvStudent){
            menu?.setHeaderTitle("Pilih donk")
            menuInflater.inflate(R.menu.context_menu, menu)
        }
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        val menuInfo = item.menuInfo
        val acmi = menuInfo as AdapterContextMenuInfo
        val student = studentItemAdapter.getItem(acmi.position)
        if (item.itemId == R.id.context_menu_delete_action){
            val studentDataSource = StudentDataSource(this)
            studentDataSource.removeStudent(student!!.id!!)
            studentItemAdapter.remove(student)
            studentItemAdapter.notifyDataSetChanged()
            return true
        } else if(item.itemId == R.id.context_menu_edit_action){
            val intent = Intent(this, FormActivity::class.java)
            intent.putExtra("id",student!!.id!!)
            startActivity(intent)

            return true
        }
        return super.onContextItemSelected(item)
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val intent = Intent(this,DetailActivity ::class.java)
        val student = studentItemAdapter.getItem(position)
        intent.putExtra("id",student?.id)
        startActivity(intent)
    }
}

private fun SearchView.setOnQueryTextListener() {
}
