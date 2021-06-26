package github.karchx.motto.models.storages

import github.karchx.motto.search_engine.citaty_info_website.data.Topic

class TopicsStorage {

    fun getTopics(): ArrayList<Topic> {
        val topics = ArrayList<Topic>()

        topics.add(Topic("zhizn", "Жизнь", "/topic/zhizn"))
        topics.add(Topic("lyubov", "Любовь", "/topic/lyubov"))
        topics.add(Topic("chelovek-lyudi", "Человек", "/topic/chelovek-lyudi"))
        topics.add(Topic("druzya-druzhba", "Дружба", "/topic/druzya-druzhba"))
        topics.add(Topic("motiviruyushie-citaty", "Мотивация", "/category/motiviruyushie-citaty"))
        topics.add(Topic("otnosheniya", "Отношения", "/topic/otnosheniya"))
        topics.add(Topic("roditeli", "Родители", "/topic/roditeli"))
        topics.add(Topic("vozrast", "Возраст", "/topic/vozrast"))
        topics.add(Topic("odinochestvo", "Одиночество", "/topic/odinochestvo"))
        topics.add(Topic("smert", "Смерть", "/topic/smert"))
        topics.add(Topic("bog", "Бог", "/topic/bog"))
        topics.add(Topic("deti", "Дети", "/topic/deti"))
        topics.add(Topic("dengi", "Деньги", "/topic/dengi"))
        topics.add(Topic("mysli", "Мысли", "/topic/mysli"))
        topics.add(Topic("nenavist", "Ненависть", "/topic/nenavist"))
        topics.add(Topic("istina", "Истина", "/topic/istina"))
        topics.add(Topic("mudrost", "Мудрость", "/topic/mudrost"))
        topics.add(Topic("razum", "Разум", "/topic/razum"))

        return topics
    }
}
