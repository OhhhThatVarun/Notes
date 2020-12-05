package com.varun.notes.presentation.extentions

import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.varun.notes.R

fun <T : ViewDataBinding> Fragment.getDataBinding(layout: Int, container: ViewGroup?): T {
    val binding: T = DataBindingUtil.inflate(layoutInflater, layout, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    return binding
}

fun Fragment.navigate(navDirections: NavDirections) {
    findNavController().navigate(navDirections)
}

fun Fragment.showAlert(title: Int, message: Int, onOkClicked: (() -> Unit)?) {
    MaterialAlertDialogBuilder(requireContext())
            .setTitle(title).setCancelable(false).setMessage(message)
            .setPositiveButton(R.string.yes) { _, _ ->
                onOkClicked?.invoke()
            }.setNegativeButton(R.string.no) { _, _ ->
            }.show()
}

fun Fragment.createShortToast(messageRes: Int) {
    Toast.makeText(requireContext(), messageRes, Toast.LENGTH_SHORT).show()
}

fun Fragment.createLongToast(messageRes: Int) {
    Toast.makeText(requireContext(), messageRes, Toast.LENGTH_LONG).show()
}