package com.dignalott.models


data class Movies(
    var page: Page
)
{
    companion object{
        val emptyMovies:Movies =Movies(
            page = Page(
                `content-items` = ContentItems(
                    content = ArrayList<Content>()
                ),
                `page-num` = "",
                `page-size` = "",
                title = "",
                `total-content-items` = ""
            )
        )
    }
}

data class Page(
    var `content-items`: ContentItems,
    var `page-num`: String,
    var `page-size`: String,
    var title: String,
    var `total-content-items`: String
)

data class ContentItems(
    var content: List<Content>
)

data class Content(
    var name: String,
    var `poster-image`: String
)


