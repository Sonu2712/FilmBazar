package com.film.bazar.coreui.views

import android.content.Context
import android.content.IntentFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.Nullable
import androidx.core.view.isVisible
import com.film.bazar.coreui.R
import com.film.bazar.coreui.databinding.LayoutAutoReadOtpBinding
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.film.bazar.coreui.helper.otp.SMSReceiver
import com.film.bazar.coreui.helper.otp.startSMSRetriever
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Function6
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class AutoReadOTPView(context: Context, @Nullable attrs: AttributeSet) :
    LinearLayout(context, attrs) {
    private val binding: LayoutAutoReadOtpBinding
    val autoReadOTPEvents = PublishSubject.create<AutoReadOTPEvents>()
    val disposable = CompositeDisposable()
    lateinit var smsReceiver: SMSReceiver
    var isReceiverRegister = false

    init {
        val inflater = LayoutInflater.from(context)
        binding = LayoutAutoReadOtpBinding.inflate(inflater, this, true)
        setupView()
    }

    fun setupView() {
        smsReceiver = SMSReceiver()
        val obsED1 = binding.edOTP1.textChanges().map { it.toString() }.share()
        binding.edOTP1.setOnKeyListener(GenericKeyEvent(binding.edOTP1, null))
        obsED1.subscribe {
            if (it.length == 1) {
                binding.edOTP2.requestFocus(binding.edOTP2.text.toString().length)
            }
        }.addTo(disposable)
        val obsED2 = binding.edOTP2.textChanges().map { it.toString() }.share()
        binding.edOTP2.setOnKeyListener(GenericKeyEvent(binding.edOTP2, binding.edOTP1))
        obsED2.subscribe {
            if (it.isEmpty()) {
                binding.edOTP1.requestFocus(binding.edOTP1.text.toString().length)
            } else if (it.length == 1) {
                binding.edOTP3.requestFocus(binding.edOTP3.text.toString().length)
            }
        }.addTo(disposable)
        val obsED3 = binding.edOTP3.textChanges().map { it.toString() }.share()
        binding.edOTP3.setOnKeyListener(GenericKeyEvent(binding.edOTP3, binding.edOTP2))
        obsED3.subscribe {
            if (it.isEmpty()) {
                binding.edOTP2.requestFocus(binding.edOTP2.text.toString().length)
            } else if (it.length == 1) {
                binding.edOTP4.requestFocus(binding.edOTP4.text.toString().length)
            }
        }.addTo(disposable)
        val obsED4 = binding.edOTP4.textChanges().map { it.toString() }.share()
        binding.edOTP4.setOnKeyListener(GenericKeyEvent(binding.edOTP4, binding.edOTP3))
        obsED4.subscribe {
            if (it.isEmpty()) {
                binding.edOTP3.requestFocus(binding.edOTP3.text.toString().length)
            } else if (it.length == 1) {
                binding.edOTP5.requestFocus(binding.edOTP5.text.toString().length)
            }
        }.addTo(disposable)
        val obsED5 = binding.edOTP5.textChanges().map { it.toString() }.share()
        binding.edOTP5.setOnKeyListener(GenericKeyEvent(binding.edOTP5, binding.edOTP4))
        obsED5.subscribe {
            if (it.isEmpty()) {
                binding.edOTP4.requestFocus(binding.edOTP4.text.toString().length)
            } else if (it.length == 1) {
                binding.edOTP6.requestFocus(binding.edOTP6.text.toString().length)
            }
        }.addTo(disposable)
        val obsED6 = binding.edOTP6.textChanges().map { it.toString() }.share()
        binding.edOTP6.setOnKeyListener(GenericKeyEvent(binding.edOTP6, binding.edOTP5))
        obsED6.subscribe {
            if (it.isEmpty()) {
                binding.edOTP5.requestFocus(binding.edOTP5.text.toString().length)
            }
        }.addTo(disposable)

        Observable.combineLatest(obsED1,
            obsED2,
            obsED3,
            obsED4,
            obsED5,
            obsED6,
            Function6 { t1: String, t2: String, t3: String, t4: String, t5: String, t6: String ->
                if (t1.isNotEmpty() && t2.isNotEmpty() && t3.isNotEmpty() && t4.isNotEmpty() && t5.isNotEmpty() && t6.isNotEmpty()) {
                    return@Function6 true
                } else false
            })
            .subscribe { autoReadOTPEvents.onNext(AutoReadOTPEvents.ToggleSubmitButtonEvent(it)) }
            .addTo(disposable)

        binding.tvResendOTP.clicks()
            .subscribe { autoReadOTPEvents.onNext(AutoReadOTPEvents.ResendOTPEvent) }
            .addTo(disposable)
    }

    fun renderResend(resendData: ResendData, isAttemptsRemaining: Boolean = true) {
        val attempts = resendData.resendCounter

        clearOTP()
        activateResend(resendData.retryAfter)

        binding.tvAttempts.apply {
            isVisible = isAttemptsRemaining
            text = context.getString(R.string.attempts_remaining, attempts)
            //startOTPRead()
        }
    }

    private fun activateResend(retryAfter: Int) {
        val counter = retryAfter - 1
        Observable.interval(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { toggleResend(false) }
            .take(retryAfter.toLong())
            .doOnComplete {
                toggleResend(true)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { sec ->
                val remainingSec = counter - sec
                binding.tvTimer.apply {
                    text = context.getString(R.string.retry_after, remainingSec)
                }
                binding.tvTimer.visibility = if (remainingSec > 0) View.VISIBLE else View.GONE
            }.addTo(disposable)

        smsReceiver.read().map { it.toString() }.subscribe {
            binding.edOTP1.setText(it[0].toString())
            binding.edOTP2.setText(it[1].toString())
            binding.edOTP3.setText(it[2].toString())
            binding.edOTP4.setText(it[3].toString())
            binding.edOTP5.setText(it[4].toString())
            binding.edOTP6.setText(it[5].toString())
            binding.edOTP6.setSelection(binding.edOTP6.text.toString().length)
        }.addTo(disposable)
    }

    fun renderSubmit(retryAfter: Int) {
        binding.tvResendOTP.visibility = View.VISIBLE

        binding.edOTP1.requestFocus(binding.edOTP1.text.toString().length)

        activateResend(retryAfter)
        //startOTPRead()
    }

    fun getOTP(): String {
        return "${binding.edOTP1.text}${binding.edOTP2.text}${binding.edOTP3.text}${binding.edOTP4.text}${binding.edOTP5.text}${binding.edOTP6.text}"
    }

    fun clearOTP() {
        binding.edOTP1.setText("")
        binding.edOTP2.setText("")
        binding.edOTP3.setText("")
        binding.edOTP4.setText("")
        binding.edOTP5.setText("")
        binding.edOTP6.setText("")
        binding.edOTP1.requestFocus(binding.edOTP1.text.toString().length)
    }

    private fun startOTPRead() {
        isReceiverRegister = true
        context.registerReceiver(
            smsReceiver, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        )
        startSMSRetriever(context)
    }

    private fun toggleResend(enable: Boolean) {
        binding.tvResendOTP.isEnabled = enable
    }

    fun destroyView() {
        if (isReceiverRegister) context.unregisterReceiver(smsReceiver)
        disposable.clear()
    }
    }


data class ResendData(
    @JvmField val resendCounter: Int,
    @JvmField val retryAfter: Int
)

sealed class AutoReadOTPEvents {
    data class ToggleSubmitButtonEvent(val toggle: Boolean) : AutoReadOTPEvents()
    object ResendOTPEvent : AutoReadOTPEvents()
}
