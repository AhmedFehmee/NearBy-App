package com.fahmy.nearPlaces.utils.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fahmy.nearPlaces.R
import com.fahmy.nearPlaces.repository.api.network.Status
import com.fahmy.nearPlaces.utils.extensions.gone
import com.fahmy.nearPlaces.utils.extensions.visible

/**
 * A custom implementation of [RecyclerView] to support
 * Empty View & Loading animation.
 */
class CompleteRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    RecyclerView(context, attrs, defStyle) {

    private var mEmptyView: View? = null
    private var mWrongView: View? = null
    private var mProgressView: View? = null

    private var columnWidth: Int = 0

    init {
        gone()
        if (attrs != null) {
            val attrsArray = intArrayOf(android.R.attr.columnWidth)
            val array = context.obtainStyledAttributes(
                attrs, attrsArray
            )
            columnWidth = array.getDimensionPixelSize(0, -1)
            array.recycle()
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        visible()
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(mAdapterObserver)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(mAdapterObserver)
        refreshState()
    }

    private fun refreshState() {
        adapter?.let {
            val noItems = 0 == it.itemCount
            if (noItems) {
                mProgressView?.gone()
                mWrongView?.gone()
                mEmptyView?.visible()
                gone()
            } else {
                mProgressView?.gone()
                mWrongView?.gone()
                mEmptyView?.gone()
                visible()
            }
        }
    }

    fun setEmptyView(emptyView: View) {
        this.mEmptyView = emptyView
        mEmptyView?.gone()
        mWrongView?.gone()
    }

    fun setProgressView(progressView: View) {
        this.mProgressView = progressView
        mProgressView?.gone()
        mWrongView?.gone()
    }

    fun showProgressView() {
        mProgressView?.visible()
        mEmptyView?.gone()
        mWrongView?.gone()
    }

    fun showEmptyStateView() {
        mProgressView?.gone()
        mEmptyView?.visible()
        mWrongView?.gone()
    }

    fun setWrongView(wrongView: View) {
        this.mWrongView = wrongView
        mProgressView?.gone()
        mEmptyView?.gone()
    }

    fun showWrongView() {
        mWrongView?.visible()
        mEmptyView?.gone()
        mProgressView?.gone()
    }

    fun setEmptyMessage(@StringRes mEmptyMessageResId: Int) {
        val emptyText = mEmptyView?.findViewById<TextView>(R.id.empty_title)
        emptyText?.setText(mEmptyMessageResId)
    }

    fun setEmptyIcon(@DrawableRes mEmptyIconResId: Int) {
        val emptyImage = mEmptyView?.findViewById<ImageView>(R.id.empty_image)
        emptyImage?.setImageResource(mEmptyIconResId)
    }

    fun showState(status: Status) {
        when (status) {
            Status.SUCCESS, Status.ERROR -> {
                mProgressView?.gone()
                mWrongView?.gone()
                mEmptyView?.visible()
            }
            Status.LOADING -> {
                mEmptyView?.gone()
                mWrongView?.gone()
                mProgressView?.visible()
            }
        }
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        if (layoutManager is GridLayoutManager) {
            val manager = layoutManager as GridLayoutManager
            if (columnWidth > 0) {
                val spanCount = Math.max(1, measuredWidth / columnWidth)
                manager.spanCount = spanCount
            }
        }
    }

    /**
     * Observes for changes in the adapter and is triggered on change
     */
    private val mAdapterObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() = refreshState()
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) = refreshState()
        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) = refreshState()
    }

}
