package com.example.adiblarapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adiblarapp.databinding.DialogBinding
import com.example.adiblarapp.databinding.FragmentSozlamalarBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Sozlamalar.newInstance] factory method to
 * create an instance of this fragment.
 */
class Sozlamalar : Fragment() {
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

    lateinit var binding: FragmentSozlamalarBinding
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSozlamalarBinding.inflate(inflater, container, false)

        binding.share.setOnClickListener {
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, "Adiblar App ni Playmarketdan yuklab oling!!!")
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }

        binding.info.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            var dialogBinding = DialogBinding.inflate(layoutInflater)
            dialog.setView(dialogBinding.root)
            dialogBinding.aboutApp.text = "Siz ushbu dastur yordamida o'zingizni qiziqtirgan adiblaringizni hayoti va ijodi bilan yaqindan tanishishingiz mumkin. Bu dasturning qulayliklari shundaki, har bir adib alohida-alohida ro'yxatga olingan va qidiruv tugmasi orqali bir zumda topishingiz mumkin boladi. Shu bilan bir qatorda \"Mumtoz\", \"O'zbek\", \"Jahon adabiyoti\" sahifalariga bo'lib qo'yilgan. Yana bir qancha qulayliklarga ega bo'lib, \"Sozlamalar\" sahifasida qorong'u vaqtlarda qiynalmasdan o'zingiz sevgan adibni ijodi bilan tanishishingiz uchun \"dark theme\" ya'ni sahifani ko'zingizga zarar yetkazmaydigan qilib foydalanishingiz mumkin."
            dialog.show()
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Sozlamalar.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Sozlamalar().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}