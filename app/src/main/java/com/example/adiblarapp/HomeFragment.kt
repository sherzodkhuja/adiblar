package com.example.adiblarapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.adiblarapp.adapters.ViewPagerAdapter
import com.example.adiblarapp.databinding.FragmentHomeBinding
import com.example.adiblarapp.databinding.ItemTabBinding
import com.example.adiblarapp.models.Adib
import com.example.adiblarapp.models.AdibType
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : androidx.fragment.app.Fragment() {
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

    lateinit var binding: FragmentHomeBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var list: ArrayList<AdibType>

    lateinit var ozbekAdiblar: ArrayList<Adib>
    lateinit var mumtozAdiblar: ArrayList<Adib>
    lateinit var jahonAdiblar: ArrayList<Adib>

    lateinit var viewPagerAdapter: ViewPagerAdapter
    private val TAG = "HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        firebaseFirestore = FirebaseFirestore.getInstance()
        list = ArrayList()

        ozbekAdiblar = ArrayList()
        mumtozAdiblar = ArrayList()
        jahonAdiblar = ArrayList()


        if (activity != null && isAdded) {
            viewPagerAdapter = ViewPagerAdapter(childFragmentManager, list)
            binding.viewPager.adapter = viewPagerAdapter
            binding.tabLayout.setupWithViewPager(binding.viewPager)
        }

        firebaseFirestore.collection("adiblar")
            .whereEqualTo("adibType", "O'zbek adabiyoti")
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        var adibOzbek = document.toObject(Adib::class.java)
                        ozbekAdiblar.add(adibOzbek)
                    }

                    var adibType = AdibType("O'zbek adabiyoti", ozbekAdiblar)
                    list.add(adibType)

                    firebaseFirestore.collection("adiblar")
                        .whereEqualTo("adibType", "Mumtoz adabiyoti")
                        .get()
                        .addOnCompleteListener(OnCompleteListener<QuerySnapshot> {
                            if (it.isSuccessful) {
                                for (document in it.result!!) {
                                    var adibMumtoz = document.toObject(Adib::class.java)
                                    mumtozAdiblar.add(adibMumtoz)
                                }

                                var adibType1 = AdibType("Mumtoz adabiyoti", mumtozAdiblar)
                                list.add(adibType1)

                                firebaseFirestore.collection("adiblar")
                                    .whereEqualTo("adibType", "Jahon adabiyoti")
                                    .get()
                                    .addOnCompleteListener(OnCompleteListener<QuerySnapshot> {
                                        if (it.isSuccessful) {
                                            for (document in it.result!!) {
                                                var adibJahon = document.toObject(Adib::class.java)
                                                jahonAdiblar.add(adibJahon)
                                            }

                                            var adibType2 =
                                                AdibType("Jahon adabiyoti", jahonAdiblar)
                                            list.add(adibType2)


                                            if (activity != null && isAdded) {
                                                viewPagerAdapter =
                                                    ViewPagerAdapter(childFragmentManager, list)
                                                binding.viewPager.adapter = viewPagerAdapter
                                                binding.tabLayout.setupWithViewPager(binding.viewPager)
                                            }

                                            setTabs()

                                            binding.tabLayout.addOnTabSelectedListener(object :
                                                TabLayout.OnTabSelectedListener {
                                                override fun onTabReselected(tab: TabLayout.Tab?) {

                                                }

                                                override fun onTabUnselected(tab: TabLayout.Tab?) {
                                                    val tabView = tab!!.customView
                                                    var textView =
                                                        tabView!!.findViewById<TextView>(R.id.type)
                                                    var cardView =
                                                        tabView.findViewById<CardView>(R.id.background)
                                                    textView.setTextColor(Color.parseColor("#303236"))
                                                    cardView.setCardBackgroundColor(
                                                        Color.parseColor(
                                                            "#00FFFFFF"
                                                        )
                                                    )
                                                }

                                                override fun onTabSelected(tab: TabLayout.Tab?) {
                                                    val tabView = tab!!.customView
                                                    var textView2 =
                                                        tabView!!.findViewById<TextView>(R.id.type)
                                                    var cardView2 =
                                                        tabView.findViewById<CardView>(R.id.background)
                                                    textView2.setTextColor(Color.parseColor("#FFFFFF"))
                                                    cardView2.setCardBackgroundColor(
                                                        Color.parseColor(
                                                            "#00B238"
                                                        )
                                                    )
                                                }
                                            })
                                        }
                                    })

                            }
                        })
                }
            })

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }

        return binding.root
    }

    private fun setTabs() {
        val count: Int = binding.tabLayout.tabCount
        for (i in 0 until count) {
            var itemTabBinding = ItemTabBinding.inflate(layoutInflater)

            itemTabBinding.type.text = list[i].adibType.toString()
            if (i == 0) {
                itemTabBinding.background.setCardBackgroundColor(Color.parseColor("#00B238"))
                itemTabBinding.type.setTextColor(Color.parseColor("#FFFFFF"))
            } else {
                itemTabBinding.type.setTextColor(Color.parseColor("#303236"))
                itemTabBinding.background.setCardBackgroundColor(Color.parseColor("#00FFFFFF"))
            }

            binding.tabLayout.getTabAt(i)?.customView = itemTabBinding.root
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}