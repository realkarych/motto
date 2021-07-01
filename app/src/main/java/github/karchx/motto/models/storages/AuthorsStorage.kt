package github.karchx.motto.models.storages

import github.karchx.motto.search_engine.citaty_info_website.data.Author

class AuthorsStorage {

    fun getAuthors(): ArrayList<Author> {
        val authors = ArrayList<Author>()
        authors.add(Author("pushkin", "Александр", "Пушкин", "/book/aleksandr-sergeevich-pushkin"))
        authors.add(Author("gyugo", "Виктор", "Гюго", "/book/viktor-gyugo"))
        authors.add(Author("uaild", "Оскар", "Уайльд", "/book/oskar-uaild"))
        authors.add(Author("bredberi", "Рэй", "Брэдбери", "/book/rei-bredberi"))
        authors.add(
            Author(
                "arkadii_i_boris_strugackie",
                "Аркадий и Борис",
                "Стругацкие",
                "/book/arkadii-i-boris-strugackie"
            )
        )
        authors.add(Author("tolstoi", "Лев", "Толстой", "/book/lev-nikolaevich-tolstoi"))
        authors.add(Author("tven", "Марк", "Твен", "/book/mark-tven"))
        authors.add(Author("freid", "Зигмунд", "Фрейд", "/book/zigmund-freid"))
        authors.add(Author("lermontov", "Михаил", "Лермонтов", "/book/mihail-yurevich-lermontov"))
        authors.add(Author("dali", "Сальвадор", "Дали", "/book/salvador-dali"))
        authors.add(Author("van_gog", "Винсент", "Ван Гог", "/book/vinsent-van-gog"))
        authors.add(Author("shanel", "Коко", "Шанель", "/book/koko-shanel"))
        authors.add(Author("ranevskaya", "Фаина", "Раневская", "/book/faina-ranevskaya"))
        authors.add(Author("czy", "Лао", "Цзы", "/book/lao-czy"))
        authors.add(Author("fazil_iskander", "Фазиль", "Искандер", "/book/fazil-iskander"))
        authors.add(Author("ford", "Генри", "Форд", "/book/genri-ford"))
        authors.add(Author("kerroll", "Льюис", "Кэрролл", "/book/lyuis-kerroll"))
        authors.add(Author("palanik", "Чак", "Паланик", "/book/chak-palanik"))

        authors.shuffle()
        return authors
    }
}
