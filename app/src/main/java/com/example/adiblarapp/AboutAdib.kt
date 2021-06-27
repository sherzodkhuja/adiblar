package com.example.adiblarapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.adiblarapp.adapters.ViewPagerAdapter
import com.example.adiblarapp.databinding.FragmentAboutAdibBinding
import com.example.adiblarapp.models.Adib
import com.example.adiblarapp.models.AdibType
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "adib"
private const val ARG_PARAM3 = "adibTypeName"

/**
 * A simple [Fragment] subclass.
 * Use the [AboutAdib.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutAdib : Fragment() {
    // TODO: Rename and change types of parameters
    private var adib: Adib? = null
    private var adibTypeName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            adib = it.getSerializable(ARG_PARAM1) as Adib
            adibTypeName = it.getString(ARG_PARAM3)
        }
    }

    lateinit var binding: FragmentAboutAdibBinding
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var firstAdibList: ArrayList<Adib>
    lateinit var list: ArrayList<Adib>
    var firstIsSaved: Boolean? = null
    var isSaved: Boolean? = null
    var firstIndex = -1
    var index = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutAdibBinding.inflate(inflater, container, false)

        list = ArrayList()
        firstAdibList = ArrayList()
        firebaseFirestore = FirebaseFirestore.getInstance()
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.adibName.text = adib?.name + " " + adib?.familyName
        binding.adibBirth.text = "(${adib?.bornYear}-${adib?.deathYear})"
        binding.about.text = adib?.about

        firebaseFirestore.collection("adiblar")
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        var adib = document.toObject(Adib::class.java)
                        var id0 = document.id
                        adib.id = id0
                        firstAdibList.add(adib)
                    }

                    for (i in firstAdibList.indices) {
                        if (firstAdibList[i].name == adib?.name && firstAdibList[i].familyName == adib?.familyName && firstAdibList[i].bornYear == adib?.bornYear) {
                            firstIsSaved = firstAdibList[i].isSaved!!
                            firstIndex = i
                        }
                    }

                    if (firstIsSaved!!) {
                        binding.saved.setImageResource(R.drawable.saved)
                        binding.save.setCardBackgroundColor(Color.parseColor("#00B238"))

                    } else {
                        binding.saved.setImageResource(R.drawable.saved_black)
                        binding.save.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                    }
                    binding.save.setOnClickListener {

                        firebaseFirestore.collection("adiblar")
                            .get()
                            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                                if (task.isSuccessful) {
                                    for (document in task.result!!) {
                                        var adib = document.toObject(Adib::class.java)
                                        var id1 = document.id
                                        adib.id = id1
                                        list.add(adib)
                                    }

                                    for (i in list.indices) {
                                        if (list[i].name == adib?.name && list[i].familyName == adib?.familyName && list[i].bornYear == adib?.bornYear) {
                                            isSaved = list[i].isSaved!!
                                            index = i
                                        }
                                    }

                                    if (isSaved!!) {
                                        binding.saved.setImageResource(R.drawable.saved_black)
                                        binding.save.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                                        adib?.isSaved = false
                                        list[index].isSaved = false
                                        firebaseFirestore.collection("adiblar")
                                            .document(list[index].id!!)
                                            .set(list[index])
                                            .addOnSuccessListener {
                                            }
                                    } else {
                                        binding.saved.setImageResource(R.drawable.saved)
                                        binding.save.setCardBackgroundColor(Color.parseColor("#00B238"))
                                        // adib?.isSaved = true
                                        list[index].isSaved = true
                                        firebaseFirestore.collection("adiblar")
                                            .document(list[index].id!!)
                                            .set(list[index])
                                            .addOnSuccessListener {
                                            }
                                    }
                                }
                            })


                    }

                }
            })

        Picasso.get().load(adib?.image).into(binding.image)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AboutAdib.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Adib) =
            AboutAdib().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}