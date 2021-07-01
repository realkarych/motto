package github.karchx.motto.views.tools.managers

import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

class FragmentReload {
    companion object {
        fun reload(fragment: Fragment, layout: Int) {
            fragment.findNavController().navigate(
                layout, fragment.arguments, NavOptions.Builder().setPopUpTo(layout, true).build()
            )
        }
    }
}
