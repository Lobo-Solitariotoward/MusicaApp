object Routes {
    const val HOME = "home"
    const val DETAIL = "detail"
    const val DETAIL_WITH_ID = "detail/{albumId}"

    fun detailWithId(id: String): String = "detail/$id"
}
