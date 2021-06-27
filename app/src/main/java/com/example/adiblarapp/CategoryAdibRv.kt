package com.example.adiblarapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.adiblarapp.adapters.RvAdibAdapter
import com.example.adiblarapp.databinding.FragmentCategoryAdibRvBinding
import com.example.adiblarapp.models.Adib
import com.example.adiblarapp.models.AdibType

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryAdibRv.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryAdibRv : Fragment() {
    // TODO: Rename and change types of parameters
    private var categoryAdib: AdibType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryAdib = it.getSerializable(ARG_PARAM1) as AdibType
        }
    }

    lateinit var binding: FragmentCategoryAdibRvBinding
    lateinit var rvAdibAdapter: RvAdibAdapter
    lateinit var list: ArrayList<Adib>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryAdibRvBinding.inflate(inflater, container, false)

        list = ArrayList()
        list.addAll(categoryAdib?.list!!)
        rvAdibAdapter = RvAdibAdapter(list, categoryAdib?.adibType!!, object : RvAdibAdapter.OnItemClickListener{

            override fun onClick(adib: Adib, adibTypeName: String) {
                var bundle = Bundle()
                bundle.putSerializable("adib", adib)
                bundle.putString("adibTypeName", adibTypeName)
                findNavController().navigate(R.id.aboutAdib, bundle)
            }
        })

        binding.rv.adapter = rvAdibAdapter

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryAdibRv.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: AdibType) =
            CategoryAdibRv().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}