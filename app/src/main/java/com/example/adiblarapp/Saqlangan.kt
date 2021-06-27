package com.example.adiblarapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.example.adiblarapp.adapters.RvAdibAdapter
import com.example.adiblarapp.databinding.FragmentSaqlanganBinding
import com.example.adiblarapp.models.Adib
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Saqlangan.newInstance] factory method to
 * create an instance of this fragment.
 */
class Saqlangan : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentSaqlanganBinding
    lateinit var rvAdibAdapter: RvAdibAdapter
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var list: ArrayList<Adib>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaqlanganBinding.inflate(inflater, container, false)
        list = ArrayList()
        firebaseFirestore = FirebaseFirestore.getInstance()

        firebaseFirestore.collection("adiblar")
            .whereEqualTo("saved", true)
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot>{
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        var adib = document.toObject(Adib::class.java)
                        var id0 = document.id
                        adib.id = id0
                        list.add(adib)
                    }

                    rvAdibAdapter = RvAdibAdapter(list, "uzb", object : RvAdibAdapter.OnItemClickListener{
                        override fun onClick(adib: Adib, adibTypeName: String) {
                            var bundle = Bundle()
                            bundle.putSerializable("adib", adib)
                            bundle.putString("adibTypeName", adibTypeName)
                            findNavController().navigate(R.id.aboutAdib, bundle)
                        }

                    })

                    binding.rv.adapter = rvAdibAdapter


                    binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            binding.search.clearFocus()
                            var newList: ArrayList<Adib> = ArrayList()
                            for (i in list.indices) {
                                if (list[i].name?.toLowerCase()?.startsWith(
                                        query.toString().toLowerCase()
                                    )!! || list[i].name?.toLowerCase()
                                        ?.startsWith(query.toString().toLowerCase())!!
                                ) {
                                    newList.add(list[i])
                                }
                            }

                            rvAdibAdapter = RvAdibAdapter(
                                newList,
                                "uzb",
                                object : RvAdibAdapter.OnItemClickListener {

                                    override fun onClick(adib: Adib, adibType: String) {
                                        var bundle = Bundle()
                                        bundle.putSerializable("adib", adib)
                                        findNavController().navigate(R.id.aboutAdib, bundle)
                                    }
                                })

                            binding.rv.adapter = rvAdibAdapter
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            var newList: ArrayList<Adib> = ArrayList()
                            for (i in list.indices) {
                                if (list[i].name?.toLowerCase()?.startsWith(
                                        newText.toString().toLowerCase()
                                    )!! || list[i].familyName?.toLowerCase()
                                        ?.startsWith(newText.toString().toLowerCase())!!
                                ) {
                                    newList.add(list[i])
                                }
                            }

                            rvAdibAdapter = RvAdibAdapter(
                                newList,
                                "uzb",
                                object : RvAdibAdapter.OnItemClickListener {

                                    override fun onClick(adib: Adib, adibType: String) {
                                        var bundle = Bundle()
                                        bundle.putSerializable("adib", adib)
                                        findNavController().navigate(R.id.aboutAdib, bundle)
                                    }
                                })
                            binding.rv.adapter = rvAdibAdapter
                            return false
                        }
                    })


                }
            })
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Saqlangan.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Saqlangan().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}