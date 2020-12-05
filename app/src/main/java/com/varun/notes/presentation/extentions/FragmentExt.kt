package com.varun.notes.presentation.extentions

import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun <T : ViewDataBinding> Fragment.getDataBinding(layout: Int, container: ViewGroup?): T {
    val binding: T = DataBindingUtil.inflate(layoutInflater, layout, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    return binding
}

fun Fragment.navigate(navDirections: NavDirections) {
    findNavController().navigate(navDirections)
}

fun Fragment.createShortToast(messageRes: Int) {
    Toast.makeText(requireContext(), messageRes, Toast.LENGTH_SHORT).show()
}

fun Fragment.createLongToast(messageRes: Int) {
    Toast.makeText(requireContext(), messageRes, Toast.LENGTH_LONG).show()
}