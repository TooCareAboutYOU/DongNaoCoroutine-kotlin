package com.dn.coroutine.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.dn.coroutine.databinding.FragmentRetrofitBinding
import com.dn.coroutine.retrofit.RetrofitViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RetrofitFragment : Fragment() {

    private val mBinding by lazy { FragmentRetrofitBinding.inflate(layoutInflater) }

    private val mViewModel by viewModels<RetrofitViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            mBinding.acEtKeyWord.textWatchFlow2()
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { input ->
                    if (input.isNotEmpty()) {
                        mViewModel.getData(input)
                    }
                }
        }

        mViewModel.mLiveData.observe(viewLifecycleOwner) {
            mBinding.acTvText.text = it.toString()
        }
    }

    /**
     * 方式一：
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun TextView.textWatchFlow(): Flow<String> = callbackFlow {
        val textWatchListener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                trySend(p0.toString()).isSuccess
            }
        }
        addTextChangedListener(textWatchListener)
        //注：移除监听
        awaitClose { removeTextChangedListener(textWatchListener) }
    }

    /**
     * 方式二：
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun TextView.textWatchFlow2(): Flow<String> = channelFlow {

        val textWatchListener = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                launch { send(p0.toString()) }
            }
        }
        addTextChangedListener(textWatchListener)
        //注：移除监听
        awaitClose { removeTextChangedListener(textWatchListener) }
    }
}