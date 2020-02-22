package sharp_like_view_event

import android.view.View
import android.widget.CompoundButton

data class CheckedListenerDummy(var compoundView: CompoundButton)
val CompoundButton.OnCheckChange : CheckedListenerDummy
    get() = CheckedListenerDummy(this)
operator fun CheckedListenerDummy.plusAssign(listener: (v: CompoundButton, b: Boolean) -> Unit){ compoundView.setOnCheckedChangeListener(listener) }