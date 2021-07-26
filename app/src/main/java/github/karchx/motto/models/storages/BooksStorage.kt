package github.karchx.motto.models.storages

import github.karchx.motto.search_engine.citaty_info_website.items.Book

class BooksStorage(private val shuffle: Boolean) {

    fun getBooks(): ArrayList<Book> {
        val books = ArrayList<Book>()

        books.add(Book("voina_i_mir", "Война и мир", "Л.Толстой", "/book/lev-nikolaevich-tolstoi/voina-i-mir"))
        books.add(Book("oruell_1984", "1984", "Д.Оруэлл", "/book/dzhordzh-oruell/1984"))
        books.add(Book("bozhestvennaya_komediya", "Божественная комедия", "Д.Алигьери", "/book/dante-aligeri/bozhestvennaya-komediya"))
        books.add(Book("master_i_margarita", "Мастер и Маргарита", "М.Булгаков", "/book/mihail-afanasevich-bulgakov/master-i-margarita"))
        books.add(Book("prestuplenie_i_nakazanie", "Преступление и наказание", "Ф.Достоевский", "/book/fedor-mihailovich-dostoevskii/prestuplenie-i-nakazanie"))
        books.add(Book("sobache_serdce", "Собачье сердце", "М.Булгаков", "/book/mihail-afanasevich-bulgakov/sobache-serdce"))
        books.add(Book("evgenii_onegin", "Евгений Онегин", "А.Пушкин", "/book/aleksandr-sergeevich-pushkin/evgenii-onegin"))
        books.add(Book("idiot", "Идиот", "Ф.Достоевский", "/book/fedor-mihailovich-dostoevskii/idiot"))
        books.add(Book("mertvye_dushi", "Мёртвые души", "Н.Гоголь", "/book/nikolai-vasilevich-gogol/mertvye-dushi"))
        books.add(Book("otcy_i_deti", "Отцы и дети", "И.Тургенев", "/book/ivan-sergeevich-turgenev/otcy-i-deti"))
        books.add(Book("gore_ot_uma", "Горе от ума", "А.Грибоедов", "/book/aleksandr-sergeevich-griboedov/gore-ot-uma"))
        books.add(Book("anna_karenina", "Анна Каренина", "Л.Толстой", "/book/lev-nikolaevich-tolstoi/anna-karenina"))
        books.add(Book("geroi_nashego_vremeni", "Герой нашего времени", "М.Лермонтов", "/book/mihail-yurevich-lermontov/geroi-nashego-vremeni"))
        books.add(Book("kapitanskaya_dochka", "Капитанская дочка", "А.Пушкин", "/book/aleksandr-sergeevich-pushkin/kapitanskaya-dochka"))
        books.add(Book("tihii_don", "Тихий Дон", "М.Шолохов", "/book/mihail-aleksandrovich-sholohov/tihii-don"))
        books.add(Book("oblomov", "Обломов", "И.Гончаров", "/book/ivan-aleksandrovich-goncharov/oblomov"))
        books.add(Book("taras_bulba", "Тарас Бульба", "Н.Гоголь", "/book/nikolai-vasilevich-gogol/taras-bulba"))
        books.add(Book("piknik_na_obochine", "Пикник на обочине", "Стругацкие", "/book/arkadii-strugackii-boris-strugackii/piknik-na-obochine"))
        books.add(Book("esli_ya_ostanus", "Если я останусь", "Г.Форман", "/book/geil-forman/esli-ya-ostanus"))
        books.add(Book("zhenshina_u_kotoroi_est_plan", "Женщина, у которой есть план", "М.Маск", "/book/mei-mask/zhenshina-u-kotoroi-est-plan"))
        books.add(Book("chelovek_amfibiya", "Человек-амфибия", "А.Беляев", "/book/aleksandr-belyaev/chelovek-amfibiya"))
        books.add(Book("ni_ci", "Ни Сы", "Д.Синсеро", "/book/dzhen-sinsero/ni-sy-bud-uveren-v-svoih-silah-i-ne-pozvolyai-somneniyam-meshat-tebe-dvigatsya-vpered"))
        books.add(Book("gosudar", "Государь", "Н.Макиавелли", "/book/nikkolo-makiavelli/gosudar"))
        books.add(Book("aforizmi_mudrosti", "Афоризмы житейской мудрости", "А.Шопенгауэр", "/book/artur-shopengauer/aforizmy-zhiteiskoi-mudrosti"))
        books.add(Book("dao_cze_czin", "Дао дэ Цзин", "Лао-Цзы", "/book/lao-czy/dao-de-czin-kniga-o-puti-i-sile"))
        books.add(Book("naedine_s_soboi", "Наедине с собой", "М.Аврелий", "/book/mark-avrelii/naedine-s-soboi"))

        if (shuffle) {
            books.shuffle()
            return books
        }
        return books
    }
}