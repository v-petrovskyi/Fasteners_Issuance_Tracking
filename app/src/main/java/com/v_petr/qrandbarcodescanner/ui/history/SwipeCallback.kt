package com.v_petr.qrandbarcodescanner.ui.history

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.v_petr.qrandbarcodescanner.R


class SwipeCallback(
    context: Context,
    private val onSwipedLeftListener: (position: Int) -> Unit,
    private val onSwipedRightListener: (position: Int) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val deleteIcon: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.baseline_delete_24)
    private val editIcon: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.baseline_edit_24)
    private val deleteBackground: ColorDrawable =
        ColorDrawable(ContextCompat.getColor(context, R.color.red))
    private val editBackground: ColorDrawable = ColorDrawable(ContextCompat.getColor(context, R.color.orange))

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.bindingAdapterPosition
        when (direction) {
            ItemTouchHelper.LEFT -> onSwipedLeftListener.invoke(position)
            ItemTouchHelper.RIGHT -> onSwipedRightListener.invoke(position)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val iconMargin = (itemView.height - deleteIcon!!.intrinsicHeight) / 2

        when {
            dX > 0 -> { // Swipe to the right (edit)
                val iconTop = itemView.top + (itemView.height - editIcon!!.intrinsicHeight) / 2
                val iconBottom = iconTop + editIcon.intrinsicHeight

                val iconLeft = itemView.left + iconMargin
                val iconRight = itemView.left + iconMargin + editIcon.intrinsicWidth

                editIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                editBackground.setBounds(
                    itemView.left,
                    itemView.top,
                    itemView.left + dX.toInt(),
                    itemView.bottom
                )
            }

            dX < 0 -> { // Swipe to the left (delete)
                val iconTop = itemView.top + (itemView.height - deleteIcon.intrinsicHeight) / 2
                val iconBottom = iconTop + deleteIcon.intrinsicHeight

                val iconLeft = itemView.right - iconMargin - deleteIcon.intrinsicWidth
                val iconRight = itemView.right - iconMargin

                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                deleteBackground.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
            }

            else -> {
                editBackground.setBounds(0, 0, 0, 0)
                deleteBackground.setBounds(0, 0, 0, 0)
            }
        }

        if (dX > 0) {
            editBackground.draw(c)
            editIcon?.draw(c)
        } else {
            deleteBackground.draw(c)
            deleteIcon.draw(c)
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
