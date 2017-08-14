package com.lanthanh.admin.icareapp.core.mvvm

/**
 * @author longv
 * Created on 14-Aug-17.
 */
interface MVVMView<out VM : MVVMViewModel> {
    val viewModel : VM
}