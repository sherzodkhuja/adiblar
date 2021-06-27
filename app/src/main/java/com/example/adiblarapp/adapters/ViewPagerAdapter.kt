package com.example.adiblarapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.adiblarapp.CategoryAdibRv
import com.example.adiblarapp.models.AdibType

class ViewPagerAdapter(
    fm: FragmentManager,
    categoryList: List<AdibType>
) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val categoryList: List<AdibType>
    override fun getItem(position: Int): Fragment {
        return CategoryAdibRv.newInstance(categoryList[position])
    }

    override fun getCount(): Int {
        return categoryList.size
    }

    init {
        this.categoryList = categoryList
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return categoryList[position].adibType
    }
}