package github.karchx.motto.models.storages

import github.karchx.motto.search_engine.citaty_info_website.data.TVSeries

class TVSeriesStorage {

    fun getSeries(): ArrayList<TVSeries> {
        val series = ArrayList<TVSeries>()

        series.add(TVSeries("reasons_why_13", "13 причин, почему", "/series/13-prichin-pochemu-13-reasons-why"))
        series.add(TVSeries("peaky_blinders", "Острые козырьки", "/series/zatochennye-kepki-ostrye-kozyrki-peaky-blinders"))
        series.add(TVSeries("kuhnya", "Кухня", "/series/kuhnya"))
        series.add(TVSeries("things_10_i_hate", "10 причин моей ненависти", "/series/10-prichin-moei-nenavisti-10-things-i-hate-about-you"))
        series.add(TVSeries("s_112263", "11/22/63", "/series/112263"))
        series.add(TVSeries("monkeys_12", "12 обезьян", "/series/12-obezyan-12-monkeys"))
        series.add(TVSeries("stulev_12", "12 стульев", "/series/12-stulev-1976"))
        series.add(TVSeries("two_and_a_half_men", "2,5 человека", "/series/2-5-cheloveka-dva-s-polovinoi-cheloveka-two-and-a-half-men"))
        series.add(TVSeries("crime_scene_investigation", "C.S.I. Место преступления", "/series/csi-mesto-prestupleniya-csi-crime-scene-investigation"))
        series.add(TVSeries("csi_miami", "C.S.I.: Майами", "/series/csi-maiami-csi-miami"))
        series.add(TVSeries("sherlock", "Шерлок Холмс", "/series/sherlok-sherlock"))
        series.add(TVSeries("mad_men", "Безумцы", "/series/bezumcy-mad-men"))
        series.add(TVSeries("game_of_thrones", "Игра престолов", "/series/igra-prestolov-game-of-thrones"))
        series.add(TVSeries("supernatural", "Сверхъестественное", "/series/sverhestestvennoe-supernatural"))
        series.add(TVSeries("doctor_who", "Доктор Кто", "/series/doktor-kto-doctor-who-2005"))
        series.add(TVSeries("breaking_bad", "Во все тяжкие", "/series/vo-vse-tyazhkie-breaking-bad"))
        series.add(TVSeries("house_md", "Доктор Хаус", "/series/doktor-haus-house-md"))
        series.add(TVSeries("black_mirror", "Чёрное Зеркало", "/series/chernoe-zerkalo-black-mirror"))

        series.shuffle()
        return series
    }
}
