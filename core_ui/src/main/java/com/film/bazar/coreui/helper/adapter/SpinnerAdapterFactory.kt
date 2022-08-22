package com.film.bazar.coreui.helper.adapter

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.film.bazar.coreui.appcoreui.spinner.SpinnerAdapter
import com.film.bazar.coreui.appcoreui.spinner.setSpinnerDataWithSelection
import com.film.bazar.coredomain.SpinnerList
import com.film.bazar.coreui.R
import com.film.bazar.domaincore.SpinnerData

object SpinnerAdapterFactory {

    @JvmStatic
    @JvmOverloads
    fun <T> getSpinnerDefaultAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        val adapter = ArrayAdapter<T>(context, R.layout.spinner_item, data)
        adapter.setDropDownViewResource(R.layout.row_spinner_simple)
        return adapter
    }

    @JvmStatic
    @JvmOverloads
    fun <T> getSpinnerLoginAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        val adapter = ArrayAdapter<T>(context, R.layout.login_spinner_item, data)
        adapter.setDropDownViewResource(R.layout.row_spinner_simple)
        return adapter
    }

    @JvmStatic
    @JvmOverloads
    fun <T> getSpinnerMFTransactionsAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        val adapter = ArrayAdapter<T>(context, R.layout.spinner_item_mf_transaction, data)
        adapter.setDropDownViewResource(R.layout.row_spinner_mf)
        return adapter
    }

    @JvmStatic
    @JvmOverloads
    fun <T> getSpinnerMFRMCode(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        val adapter = ArrayAdapter<T>(context, R.layout.spinner_item_rmcode, data)
        adapter.setDropDownViewResource(R.layout.row_spinner_mf)
        return adapter
    }

    @JvmStatic
    @JvmOverloads
    fun <T> getSpinnerExchangeAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        val adapter = ArrayAdapter<T>(context, R.layout.spinner_item_exchange, data)
        adapter.setDropDownViewResource(R.layout.row_exchange_spinner_simple)
        return adapter
    }

    @JvmStatic
    @JvmOverloads
    fun <T> getCuratedMandateDecorativeSpinnerAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        val adapter = ArrayAdapter<T>(context, R.layout.spinner_item_curated_mandate, data)
        adapter.setDropDownViewResource(R.layout.row_exchange_spinner_simple)
        return adapter
    }

    @JvmStatic
    @JvmOverloads
    fun <T> getDecorativeSpinnerAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        val adapter = ArrayAdapter<T>(context, R.layout.spinner_item_markets, data)
        adapter.setDropDownViewResource(R.layout.row_exchange_spinner_simple)
        return adapter
    }

    @JvmStatic
    @JvmOverloads
    fun <T : SpinnerData> getDecorativeSpinnerDataAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        return SpinnerAdapter(
            context,
            data,
            textViewResourceId = R.layout.spinner_item_markets,
            layoutResource = R.layout.row_spinner_simple
        )
    }

    @JvmStatic
    @JvmOverloads
    fun <T> getOtherReportsSpinnerAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        val adapter = ArrayAdapter<T>(context, R.layout.spinner_item_other_reports, data)
        adapter.setDropDownViewResource(R.layout.row_exchange_spinner_simple)
        return adapter
    }

    @JvmStatic
    @JvmOverloads
    fun <T : SpinnerData> getDematHoldingsSpinnerAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        return SpinnerAdapter(
            context,
            data,
            textViewResourceId = R.layout.spinner_item_demat_holdings,
            layoutResource = R.layout.row_exchange_spinner_simple
        )
    }

    @JvmStatic
    @JvmOverloads
    fun <T> getQuoteDecorativeSpinnerAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        val adapter = ArrayAdapter<T>(context, R.layout.quote_spinner_item_markets, data)
        adapter.setDropDownViewResource(R.layout.row_exchange_spinner_simple)
        return adapter
    }

    @JvmStatic
    @JvmOverloads
    fun <T : SpinnerData> getQuoteDecorativeSpinnerDataAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        return SpinnerAdapter(
            context,
            data,
            textViewResourceId = R.layout.quote_spinner_item_markets,
            layoutResource = R.layout.row_exchange_spinner_simple
        )
    }

    @JvmStatic
    @JvmOverloads
    fun <T : SpinnerData> getSpinnerPlaceOrderValidityAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        return SpinnerAdapter(
            context,
            data,
            textViewResourceId = R.layout.spinner_order_validity_item,
            layoutResource = R.layout.row_spinner_simple
        )
    }

    @JvmStatic
    @JvmOverloads
    fun <T> getSpinnerPlaceOrderParticipantAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        val adapter = ArrayAdapter<T>(context, R.layout.spinner_order_validity_item, data)
        adapter.setDropDownViewResource(R.layout.row_spinner_simple)
        return adapter
    }

    @JvmStatic
    @JvmOverloads
    fun <T> getSpinnerWithRoundedCornerAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        val adapter = ArrayAdapter<T>(context, R.layout.spinner_item_rounded_corner, data)
        adapter.setDropDownViewResource(R.layout.row_spinner_simple)
        return adapter
    }

    @JvmStatic
    @JvmOverloads
    fun <T> getBondSpinnerAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        val adapter = ArrayAdapter<T>(context, R.layout.spinner_item_exchange, data)
        adapter.setDropDownViewResource(R.layout.row_spinner_simple)
        return adapter
    }

    @JvmStatic
    @JvmOverloads
    fun <T : SpinnerData> getExchangeSpinnerAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        return SpinnerAdapter(
            context,
            data,
            textViewResourceId = R.layout.spinner_item_exchange,
            layoutResource = R.layout.row_spinner_simple
        )
    }

    @JvmStatic
    @JvmOverloads
    fun <T : SpinnerData> getSpinnerDecorativeAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        return SpinnerAdapter(
            context,
            data,
            textViewResourceId = R.layout.quote_spinner_item_markets,
            layoutResource = R.layout.row_spinner_simple
        )
    }

    @JvmStatic
    @JvmOverloads
    fun <T> getStartupSpinnerDecorativeAdapter(
        context: Context,
        data: List<T> = mutableListOf()
    ): ArrayAdapter<T> {
        val adapter = ArrayAdapter<T>(context, R.layout.quote_spinner_item_markets, data)
        adapter.setDropDownViewResource(R.layout.row_spinner_simple)
        return adapter
    }
}

fun <T> ArrayAdapter<T>.setSpinnerListWithSelection(
    spinner: Spinner,
    data: SpinnerList<T>,
    selectedData: T? = null
) {
    setSpinnerDataWithSelection(
        spinner, data.entries, data.selectedEntry ?: selectedData
    )
}
