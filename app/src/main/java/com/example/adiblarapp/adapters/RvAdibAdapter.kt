package com.example.adiblarapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adiblarapp.R
import com.example.adiblarapp.databinding.ItemAdibBinding
import com.example.adiblarapp.models.Adib
import com.example.adiblarapp.models.AdibType
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.squareup.picasso.Picasso

class RvAdibAdapter(var list: List<Adib>, var adibTypeName: String, var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<RvAdibAdapter.Vh>() {

    inner class Vh(var itemAdibBinding: ItemAdibBinding) :
        RecyclerView.ViewHolder(itemAdibBinding.root) {

        lateinit var firebaseFirestore: FirebaseFirestore
        lateinit var firstAdibList: ArrayList<Adib>
        lateinit var list: ArrayList<Adib>
        var firstIsSaved: Boolean? = null
        var isSaved: Boolean? = null
        var firstIndex = -1
        var index = -1


        fun onBind(adib: Adib, adibTypeName: String) {
            list = ArrayList()
            firstAdibList = ArrayList()
            firebaseFirestore = FirebaseFirestore.getInstance()
            Picasso.get().load(adib.image).into(itemAdibBinding.image)
            itemAdibBinding.adibName.text = adib.name.toString()
            itemAdibBinding.adibFamilyName.text = adib.familyName.toString()
            itemAdibBinding.adibBirth.text = "(${adib.bornYear} - ${adib.deathYear})"
            itemAdibBinding.card.setOnClickListener {
                onItemClickListener.onClick(adib, adibTypeName)
            }

            onSavedClick(adib)
        }

        private fun onSavedClick(adib: Adib) {
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
                            if (firstAdibList[i].name == adib.name && firstAdibList[i].familyName == adib.familyName && firstAdibList[i].bornYear == adib.bornYear) {
                                firstIsSaved = firstAdibList[i].isSaved!!
                                firstIndex = i
                            }
                        }

                        if (firstIsSaved!!) {
                            itemAdibBinding.saved.setImageResource(R.drawable.saved)
                            itemAdibBinding.save.setCardBackgroundColor(Color.parseColor("#00B238"))

                        } else {
                            itemAdibBinding.saved.setImageResource(R.drawable.saved_black)
                            itemAdibBinding.save.setCardBackgroundColor(Color.parseColor("#F6F6F6"))
                        }
                        itemAdibBinding.save.setOnClickListener {

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
                                            if (list[i].name == adib.name && list[i].familyName == adib.familyName && list[i].bornYear == adib.bornYear) {
                                                isSaved = list[i].isSaved!!
                                                index = i
                                            }
                                        }

                                        if (isSaved!!) {
                                            itemAdibBinding.saved.setImageResource(R.drawable.saved_black)
                                            itemAdibBinding.save.setCardBackgroundColor(Color.parseColor("#F6F6F6"))
                                            adib?.isSaved = false
                                            list[index].isSaved = false
                                            firebaseFirestore.collection("adiblar")
                                                .document(list[index].id!!)
                                                .set(list[index])
                                                .addOnSuccessListener {
                                                }
                                        } else {
                                            itemAdibBinding.saved.setImageResource(R.drawable.saved)
                                            itemAdibBinding.save.setCardBackgroundColor(Color.parseColor("#00B238"))
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

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemAdibBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], adibTypeName)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onClick(adib: Adib, adibTypeName: String)
    }
}