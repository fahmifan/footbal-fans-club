package io.github.miun173.footballfans.detail

interface DetailContract {
    interface View {
        fun setHeader()
        fun setLogo(homeBadge: String?, awayBadge: String?)
        fun setGoals()
        fun setLineups()
    }

    interface Presenter {
        fun getTeam(homeName: String?, awayName: String?)
    }
}