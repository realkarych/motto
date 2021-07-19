package github.karchx.motto.models.storages

import github.karchx.motto.search_engine.citaty_info_website.data.TVChannel

class TVChannelsStorage {

    fun getChannels(): ArrayList<TVChannel> {
        val channels = ArrayList<TVChannel>()

        channels.add(TVChannel("stand_up", "Stand Up", "/tv/stand-up"))
        channels.add(TVChannel("versus_battle", "Versus Battle", "/tv/versus-battle"))
        channels.add(TVChannel("vdud", "вДудь", "/tv/vdud"))
        channels.add(TVChannel("improvizaciya", "Импровизация", "/tv/improvizaciya"))
        channels.add(TVChannel("comedy_club", "Comedy Club", "/tv/comedy-club-kamedi-klab"))
        channels.add(TVChannel("kvn", "КВН", "/tv/kvn"))
        channels.add(TVChannel("frenki_shou", "Фрэнки-шоу", "/tv/frenki-shou"))
        channels.add(TVChannel("chto_bylo_dalshe", "Что было дальше?", "/tv/chto-bylo-dalshe"))
        channels.add(TVChannel("max100500", "Макс +100500", "/tv/100500"))
        channels.add(TVChannel("komika_22", "22 комика", "/tv/22-komika"))
        channels.add(TVChannel("minut_60", "60 минут", "/tv/60-minut"))
        channels.add(TVChannel("comedy_woman", "Comedy Woman", "/tv/comedy-woman"))
        channels.add(TVChannel("uralskie_pelmeni", "Уральские пельмени", "/tv/uralskie-pelmeni"))
        channels.add(TVChannel("vechernii_urgant", "Вечерний Ургант", "/tv/vechernii-urgant"))

        channels.shuffle()
        return channels
    }
}
