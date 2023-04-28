package com.napas.imagepagination.base

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.napas.imagepagination.R
import com.napas.imagepagination.model.AlertEvent

abstract class BaseFragment : Fragment() {

    protected fun showAlert(alertEvent: AlertEvent) {
        view?.let {
            Snackbar.make(
                it,
                alertEvent.message ?: alertEvent.messageRes?.let(this::getString)
                ?: getString(R.string.error_generic),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}