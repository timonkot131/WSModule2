package sharp_like_view_event

import android.view.MotionEvent
import android.view.View

data class ClickListenerDummy(var view: View)
val View.OnClick : ClickListenerDummy
    get() = ClickListenerDummy(this)
operator fun ClickListenerDummy.plusAssign(listener: (v: View) -> Unit){view.setOnClickListener(listener) }

data class TouchListenerDummy(var view: View)
val View.OnTouch : TouchListenerDummy
    get() = TouchListenerDummy(this)
operator fun TouchListenerDummy.plusAssign(listener: (v: View, motionevent: MotionEvent) -> Boolean){view.setOnTouchListener(listener)}

data class LayoutChangeDummy(var view: View)
val View.OnLayoutChange: LayoutChangeDummy
    get() = LayoutChangeDummy(this)
operator fun LayoutChangeDummy.plusAssign(listener: (v: View, a1: Int, a2: Int, a3: Int, a4: Int, a5: Int, a6: Int,a7: Int,a8: Int)->Unit){view.addOnLayoutChangeListener(listener)}