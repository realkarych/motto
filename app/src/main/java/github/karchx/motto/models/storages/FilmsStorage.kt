package github.karchx.motto.models.storages

import github.karchx.motto.search_engine.citaty_info_website.data.Film

class FilmsStorage {

    fun getFilms(): ArrayList<Film> {
        val films = ArrayList<Film>()

        films.add(
            Film(
                "neprikasaemye_intouchables",
                "Неприкасаемые",
                "/movie/1-1-neprikasaemye-intouchables"
            )
        )
        films.add(
            Film(
                "boicovskii_klub_fight_club",
                "Бойцовский клуб",
                "/movie/boicovskii-klub-fight-club"
            )
        )
        films.add(Film("bolshoi_kush_snatch", "Большой куш", "/movie/bolshoi-kush-snatch"))
        films.add(Film("brat_2", "Брат 2", "/movie/brat-2"))
        films.add(
            Film(
                "vechnoe_siyanie_chistogo_razuma",
                "Вечное сияние чистого разума",
                "/movie/vechnoe-siyanie-chistogo-razuma-eternal-sunshine-of-the-spotless-mind"
            )
        )
        films.add(Film("the_gentlemen", "Джентльмены", "/movie/dzhentlmeny-the-gentlemen"))
        films.add(Film("dzhoker_joker", "Джокер", "/movie/dzhoker-joker-2019"))
        films.add(
            Film(
                "dostuchatsya_do_nebes",
                "Достучаться до небес",
                "/movie/dostuchatsya-do-nebes-knockin-on-heavens-door"
            )
        )
        films.add(Film("dedpul_deadpool", "Дэдпул", "/movie/dedpul-deadpool"))
        films.add(Film("molis_lyubi", "Ешь, молись, люби", "/movie/esh-molis-lyubi-eat-pray-love"))
        films.add(Film("interstellar", "Интерстеллар", "/movie/interstellar-interstellar"))
        films.add(Film("krestnyi_otec", "Крёстный отец", "/movie/krestnyi-otec-the-godfather"))
        films.add(
            Film(
                "kriminalnoe_chtivo",
                "Криминальное чтиво",
                "/movie/kriminalnoe-chtivo-pulp-fiction"
            )
        )
        films.add(Film("the_matrix", "Матрица", "/movie/matrica-the-matrix"))
        films.add(Film("mirnyi_voin", "Мирный воин", "/movie/mirnyi-voin-peaceful-warrior"))
        films.add(Film("the_avengers", "Мстители", "/movie/mstiteli-the-avengers"))
        films.add(Film("inception", "Начало", "/movie/nachalo-inception"))
        films.add(
            Film(
                "o_chem_govoryat_muzhchiny",
                "О чём говорят мужчины",
                "/movie/o-chem-govoryat-muzhchiny"
            )
        )
        films.add(
            Film(
                "obshestvo_mertvyh_poetov",
                "Общество мёртвых поэтов",
                "/movie/obshestvo-mertvyh-poetov-dead-poets-society"
            )
        )
        films.add(Film("sluzhebnyi_roman", "Служебный роман", "/movie/sluzhebnyi-roman"))
        films.add(Film("sumerki_twilight", "Сумерки", "/movie/sumerki-twilight"))
        films.add(Film("titanic", "Титаник", "/movie/titanik-titanic"))
        films.add(
            Film(
                "tot_samyi_myunhgauzen",
                "Тот самый Мюнхгаузен",
                "/movie/tot-samyi-myunhgauzen"
            )
        )
        films.add(
            Film(
                "tri_metra_nad_urovnem_neba",
                "Три метра над уровнем неба",
                "/movie/tri-metra-nad-urovnem-neba-tres-metros-sobre-el-cielo"
            )
        )
        films.add(
            Film(
                "inside_im_dancing",
                "...А в душе я танцую",
                "/movie/a-v-dushe-ya-tancuyu-inside-im-dancing"
            )
        )
        films.add(Film("schindlers_list", "Список Шиндлера", "/movie/spisok-shindlera-schindlers-list"))

        films.shuffle()
        return films
    }
}
