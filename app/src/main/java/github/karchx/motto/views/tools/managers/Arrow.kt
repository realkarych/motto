package github.karchx.motto.views.tools.managers

import github.karchx.motto.views.MainActivity

class Arrow {
    companion object {
        fun displayBackArrow(activity: MainActivity) {
            (activity as MainActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as MainActivity?)?.supportActionBar?.setDisplayShowHomeEnabled(true)
        }

        fun hideBackArrow(activity: MainActivity) {
            (activity as MainActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            (activity as MainActivity?)?.supportActionBar?.setDisplayShowHomeEnabled(false)
        }
    }
}
