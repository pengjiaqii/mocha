//package com.example.mocha.base
//
//import androidx.lifecycle.LifecycleObserver
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.CancellationException
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.coroutineScope
//import kotlinx.coroutines.launch
//
///**
// * 作者 : Mocha
// * 邮箱 : robotjiaqi@163.com
// * 日期 : 2019/8/15 12:46
// * 功能 :
// */
//open class BaseViewModel : ViewModel(), LifecycleObserver {
//
//    val mException: MutableLiveData<Throwable> = MutableLiveData()
//
//    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
//        launchOnUI {
//            tryCatch(tryBlock, {}, {}, true)
//        }
//    }
//
//
//    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
//
//        viewModelScope.launch { block() }
//
//    }
//
//
//    private suspend fun tryCatch(
//            tryBlock: suspend CoroutineScope.() -> Unit,
//            catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
//            finallyBlock: suspend CoroutineScope.() -> Unit,
//            handleCancellationExceptionManually: Boolean = false) {
//        coroutineScope {
//            try {
//                tryBlock()
//            } catch (e: Throwable) {
//                if (e !is CancellationException || handleCancellationExceptionManually) {
//                    mException.value = e
//                    catchBlock(e)
//                } else {
//                    throw e
//                }
//            } finally {
//                finallyBlock()
//            }
//        }
//    }
//
//}